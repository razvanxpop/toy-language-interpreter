package programgenerator;

import datastructure.IDictionary;
import datastructure.MyDictionary;
import exceptions.StatementException;
import model.exp.*;
import model.stmt.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramStateGenerator {
  public static List<Statement> getPrograms() {
    ArrayList<Statement> programs = new ArrayList<>(
        Arrays.asList(
            ProgramStateGenerator.getProgramState1(),
            ProgramStateGenerator.getProgramState2(),
            ProgramStateGenerator.getProgramState3(),
            ProgramStateGenerator.getProgramState4(),
            ProgramStateGenerator.getProgramState5(),
            ProgramStateGenerator.getProgramState6(),
            ProgramStateGenerator.getProgramState7(),
            ProgramStateGenerator.getProgramState8(),
            ProgramStateGenerator.getProgramState9(),
            ProgramStateGenerator.getProgramState10(),
            ProgramStateGenerator.getProgramState11()
        ));
    for (int i = 0; i < programs.size(); i++) {
      try {
        IDictionary<String, Type> typeEnvironment = new MyDictionary<>();
        programs.get(i).typeCheck(typeEnvironment);
      } catch (StatementException e) {
        System.out.println(e.getMessage());
        programs.remove(i);
        i--;
      }
    }

    return programs;
  }

  private static Statement getProgramState1(){
    Statement ex1 = new CompoundStatement(new VarDeclStmt("v", new IntType()),
        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(71))),
            new PrintStatement(new VarExpression("v"))));

    return ex1;
  }

  private static Statement getProgramState2(){
    Statement ex2 = new CompoundStatement(new VarDeclStmt("a", new IntType()),
        new CompoundStatement(new VarDeclStmt("b", new IntType()),
            new CompoundStatement(new AssignmentStatement("a", new ArithmeticalExpression(1, new ValueExpression(new IntValue(2)),
                new ArithmeticalExpression(3, new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                new CompoundStatement(new AssignmentStatement("b", new ArithmeticalExpression(1, new VarExpression("a"), new ValueExpression(
                    new IntValue(1)))), new PrintStatement(new VarExpression("b"))))));
    return ex2;
  }

  private static Statement getProgramState3(){
    Statement ex3 = new CompoundStatement(new VarDeclStmt("a", new BoolType()),
        new CompoundStatement(new VarDeclStmt("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                new CompoundStatement(new IfStatement(new VarExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                    new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VarExpression("v"))))));
    return ex3;
  }

  private static Statement getProgramState4(){
    Statement ex4 = new CompoundStatement(new VarDeclStmt("varf", new StringType()),
        new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("src/main/java/test.in"))),
            new CompoundStatement(new OpenReadFileStatement(new VarExpression("varf")),
                new CompoundStatement(new VarDeclStmt("varc", new IntType()),
                    new CompoundStatement(new ReadFileStatement(new VarExpression("varf"), "varc"),
                        new CompoundStatement(new PrintStatement(new VarExpression("varc")),
                            new CompoundStatement(new ReadFileStatement(new VarExpression("varf"), "varc"),
                                new CompoundStatement(new PrintStatement(new VarExpression("varc")), new CloseReadFileStatement(new VarExpression("varf"))))))))));
    return ex4;
  }

  private static Statement getProgramState5(){
    Statement ex5 = new CompoundStatement(new VarDeclStmt("v", new ReferenceType(new IntType())),
        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
            new CompoundStatement(new VarDeclStmt("a", new ReferenceType(new ReferenceType(new IntType()))),
                new CompoundStatement(new HeapAllocationStatement("a", new VarExpression("v")),
                    new CompoundStatement(new PrintStatement(new VarExpression("v")),
                        new PrintStatement(new VarExpression("a")))))));
    return ex5;
  }

  private static Statement getProgramState6(){
    Statement ex6 = new CompoundStatement(new VarDeclStmt("v", new ReferenceType(new IntType())),
        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
            new CompoundStatement(new VarDeclStmt("a", new ReferenceType(new ReferenceType(new IntType()))),
                new CompoundStatement(new HeapAllocationStatement("a", new VarExpression("v")),
                    new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VarExpression("v"))),
                        new PrintStatement(new ArithmeticalExpression(1, new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a"))), new ValueExpression(new IntValue(5)))))))));
    return ex6;
  }

  private static Statement getProgramState7(){
    Statement ex7 = new CompoundStatement(new VarDeclStmt("v", new ReferenceType(new IntType())),
        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
            new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VarExpression("v"))),
                new CompoundStatement(new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                    new CompoundStatement(new PrintStatement(new ArithmeticalExpression(1,
                        new HeapReadingExpression(new VarExpression("v")), new ValueExpression(new IntValue(5)))), new NopStatement())))));
    return ex7;
  }

  private static Statement getProgramState8(){
    Statement ex8 = new CompoundStatement(new VarDeclStmt("v", new ReferenceType(new IntType())),
        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
            new CompoundStatement(new VarDeclStmt("a", new ReferenceType(new ReferenceType(new IntType()))),
                new CompoundStatement(new HeapAllocationStatement("a", new VarExpression("v")),
                    new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                        new CompoundStatement(new VarDeclStmt("c", new ReferenceType(new IntType())), new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a"))))))))));
    return ex8;
  }

  private static Statement getProgramState9(){
    Statement ex9 = new CompoundStatement(new VarDeclStmt("v", new ReferenceType(new IntType())),
        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
            new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(10))),
                new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VarExpression("v"))),
                    new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(5))),
                        new CompoundStatement(new VarDeclStmt("a", new ReferenceType(new IntType())),
                            new CompoundStatement(new VarDeclStmt("b", new ReferenceType(new IntType())),
                                new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(0))),
                                    new CompoundStatement(new HeapAllocationStatement("b", new ValueExpression(new IntValue(1))),
                                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(3))))))))))));
    return ex9;
  }

  private static Statement getProgramState10(){
    Statement ex10 = new CompoundStatement(new VarDeclStmt("v", new IntType()),
        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
            new CompoundStatement(new WhileStatement(new RationalExpression(4, new VarExpression("v"), new ValueExpression(new IntValue(0))),
                new CompoundStatement(new PrintStatement(new VarExpression("v")), new AssignmentStatement("v", new ArithmeticalExpression(2, new VarExpression("v"), new ValueExpression(new IntValue(1)))))),
                new PrintStatement(new VarExpression("v")))));
    return ex10;
  }

  private static Statement getProgramState11(){
    Statement ex11 = new CompoundStatement(new VarDeclStmt("v", new IntType()),
        new CompoundStatement(new VarDeclStmt("a", new ReferenceType(new IntType())),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                    new CompoundStatement(new ForkStatement(
                        new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                new CompoundStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new HeapReadingExpression(new VarExpression("a"))))))),
                        new CompoundStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new HeapReadingExpression(new VarExpression("a"))))
                    )))));
    return ex11;
  }
}
