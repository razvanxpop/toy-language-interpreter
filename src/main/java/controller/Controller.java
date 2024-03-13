package controller;

import exceptions.ControllerException;
import exceptions.RepositoryException;
import model.prgstate.ProgramState;
import model.value.ReferenceValue;
import model.value.Value;
import repository.IRepository;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
  private final IRepository repository;
  private final ExecutorService executor = Executors.newFixedThreadPool(2);

  public Controller(IRepository repository) {
    this.repository = repository;
  }

  public IRepository getRepository() {
    return repository;
  }

  public void oneStepForAllPrograms(List<ProgramState> programStates) throws ControllerException {
    List<Callable<ProgramState>> callList = programStates.stream()
        .map((ProgramState programState) -> (Callable<ProgramState>) programState::oneStep)
        .toList();
    try {
      List<ProgramState> newProgramStates = this.executor.invokeAll(callList).stream()
          .map(future -> {
            try {
              return future.get();
            } catch (InterruptedException | ExecutionException e) {
              throw new RuntimeException(e);
            }
          })
          .filter(Objects::nonNull)
          .toList();
      programStates.addAll(newProgramStates);
    } catch (InterruptedException e) {
      throw new ControllerException(e.getMessage());
    }

    if (false) {
      programStates.forEach(programState -> {
        try {
          this.repository.logProgramStateExecution(programState);
        } catch (RepositoryException e) {
          throw new RuntimeException(e);
        }
      });
    }
    this.repository.setAllProgramStates(programStates);
  }

  public void allSteps() throws ControllerException {
    //executor = Executors.newFixedThreadPool(2);
    List<ProgramState> programStates = removeCompletedProgram(repository.getAllProgramStates());
//    programStates.forEach(program -> {
//      try {
//        repository.logProgramStateExecution(program);
//      } catch (RepositoryException exception) {
//        throw new RuntimeException(exception.getMessage());
//      }
//    });

    while (!programStates.isEmpty()) {
      try {
        repository.getHeap().setContent(garbageCollector(getAddrFromSymbolTable(repository.getSymbolTable().values()),
            repository.getHeap().getContent()));
      } catch (RepositoryException exception) {
        throw new ControllerException(exception.getMessage());
      }
      oneStepForAllPrograms(programStates);
      programStates = removeCompletedProgram(repository.getAllProgramStates());
    }
    //executor.shutdownNow();
    //repository.setAllProgramStates(programStates);
  }

  public Map<Integer, Value> garbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap) {
    return heap.entrySet().stream()
        .filter(e -> {
          for (Value value : heap.values())
            if (value instanceof ReferenceValue referenceValue)
              if (referenceValue.getAddress() == e.getKey())
                return true;

          return symbolTableAddresses.contains(e.getKey());
        })
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  public List<Integer> getAddrFromSymbolTable(Collection<Value> symbolTableValues) {
    return symbolTableValues.stream()
        .filter(value -> value instanceof ReferenceValue)
        .map(value -> ((ReferenceValue) value).getAddress())
        .collect(Collectors.toList());
  }

  public List<ProgramState> removeCompletedProgram(List<ProgramState> inProgramList) {
    return inProgramList.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
  }
}
