/*
 * @(#)Encoder.java                        2.1 2003/10/07
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */

package Triangle.CodeGenerator;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import TAM.Instruction;
import TAM.Machine;
import Triangle.ErrorReporter;
import Triangle.StdEnvironment;
import Triangle.AbstractSyntaxTrees.AST;
import Triangle.AbstractSyntaxTrees.AnyTypeDenoter;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.BinaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.BoolTypeDenoter;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
import Triangle.AbstractSyntaxTrees.CharTypeDenoter;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.Declaration;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyCommand;
import Triangle.AbstractSyntaxTrees.EmptyExpression;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.ErrorTypeDenoter;
import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncDeclaration;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.CompoundIfCommand;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.IntTypeDenoter;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.SequentialCommand;
import Triangle.AbstractSyntaxTrees.SequentialDeclaration;
import Triangle.AbstractSyntaxTrees.SimpleTypeDenoter;
import Triangle.AbstractSyntaxTrees.SimpleVname;
import Triangle.AbstractSyntaxTrees.SingleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleArrayAggregate;
import Triangle.AbstractSyntaxTrees.SingleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.SingleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleRecordAggregate;
import Triangle.AbstractSyntaxTrees.SubscriptVname;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.UnaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Visitor;
import Triangle.AbstractSyntaxTrees.Vname;
import Triangle.AbstractSyntaxTrees.VnameExpression;
// @author        Joseph
// @descripcion   Importacion de nuevos ASTs
// @funcionalidad Parseo de nuevos ASTs
// @codigo        J.13
import Triangle.AbstractSyntaxTrees.VarTDDeclaration;
import Triangle.AbstractSyntaxTrees.VarExpDeclaration;
import Triangle.AbstractSyntaxTrees.WhileLoopCommand;
import Triangle.AbstractSyntaxTrees.UntilLoopCommand;
import Triangle.AbstractSyntaxTrees.DoLoopUntilCommand;
import Triangle.AbstractSyntaxTrees.DoLoopWhileCommand;
import Triangle.AbstractSyntaxTrees.SingleElsifCommand;
import Triangle.AbstractSyntaxTrees.SequentialElsifCommand;
import Triangle.AbstractSyntaxTrees.ForLoopDoCommand;
import Triangle.AbstractSyntaxTrees.ForLoopWhileCommand;
import Triangle.AbstractSyntaxTrees.ForLoopUntilCommand;
import Triangle.AbstractSyntaxTrees.CaseLiteral;
import Triangle.AbstractSyntaxTrees.SimpleCaseRange;
import Triangle.AbstractSyntaxTrees.CompoundCaseRange;
import Triangle.AbstractSyntaxTrees.CaseLiterals;
import Triangle.AbstractSyntaxTrees.SequentialCaseRange;
import Triangle.AbstractSyntaxTrees.ElseCase;
import Triangle.AbstractSyntaxTrees.SingleCase;
import Triangle.AbstractSyntaxTrees.SequentialCase;
import Triangle.AbstractSyntaxTrees.SimpleCases;
import Triangle.AbstractSyntaxTrees.CompoundCases;
import Triangle.AbstractSyntaxTrees.ChooseCommand;
import Triangle.AbstractSyntaxTrees.Procedure;
import Triangle.AbstractSyntaxTrees.Function;
import Triangle.AbstractSyntaxTrees.SequentialProcFuncs;
import Triangle.AbstractSyntaxTrees.PrivDeclaration;
import Triangle.AbstractSyntaxTrees.RecDeclaration;
import Triangle.AbstractSyntaxTrees.ForFromCommand;
import Triangle.AbstractSyntaxTrees.SimpleVarName;
import Triangle.AbstractSyntaxTrees.DotVarName;
import Triangle.AbstractSyntaxTrees.SubscriptVarName;
import Triangle.AbstractSyntaxTrees.PackageIdentifier;
import Triangle.AbstractSyntaxTrees.PackageVname;
import Triangle.AbstractSyntaxTrees.SimpleLongIdentifier;
import Triangle.AbstractSyntaxTrees.PackageLongIdentifier;
import Triangle.AbstractSyntaxTrees.SinglePackageDeclaration;
import Triangle.AbstractSyntaxTrees.SequentialPackageDeclaration;
import Triangle.AbstractSyntaxTrees.SimpleProgram;
import Triangle.AbstractSyntaxTrees.CompoundProgram;
import Triangle.AbstractSyntaxTrees.VarName;
/* J.13
import Triangle.AbstractSyntaxTrees.WhileCommand;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
*/
// END CAMBIO Joseph
public final class Encoder implements Visitor {


  // Commands
  public Object visitAssignCommand(AssignCommand ast, Object o) {
    Frame frame = (Frame) o;
    Integer valSize = (Integer) ast.E.visit(this, frame);
    encodeStore(ast.V, new Frame (frame, valSize.intValue()),
		valSize.intValue());
    return null;
  }
  
  // @author        Andres
  // @descripcion   Metodo encoder para visitar CompoundIfCommand
  // @funcionalidad Cambio en las alternativas de single-command
  // @codigo        A.22
  public Object visitCompoundIfCommand(CompoundIfCommand ast, Object o) {
      return null;
  }
  
  // @author        Andres
  // @descripcion   Metodo encoder para visitar SingleElsifCommand
  // @funcionalidad AST SingleElsifCommand
  // @codigo        A.16
  public Object visitSingleElsifCommand(SingleElsifCommand ast, Object o) {
      return null;
  }
  // END cambio Andres
  
  // @author        Andres
  // @descripcion   Metodo encoder para visitar SequentialElsifCommand
  // @funcionalidad AST SequentialElsifCommand
  // @codigo        A.17
  public Object visitSequentialElsifCommand(SequentialElsifCommand ast, Object o) {
      return null;
  }
  // END cambio Andres
  
  

  public Object visitCallCommand(CallCommand ast, Object o) {
    Frame frame = (Frame) o;
    Integer argsSize = (Integer) ast.APS.visit(this, frame);
    ast.LI.visit(this, new Frame(frame.level, argsSize));
    return null;
  }

  
  public Object visitEmptyCommand(EmptyCommand ast, Object o) {
    return null;
  }

  public Object visitIfCommand(IfCommand ast, Object o) {
    Frame frame = (Frame) o;
    int jumpifAddr, jumpAddr;

    Integer valSize = (Integer) ast.E.visit(this, frame);
    jumpifAddr = nextInstrAddr;
    emit(Machine.JUMPIFop, Machine.falseRep, Machine.CBr, 0);
    ast.C1.visit(this, frame);
    jumpAddr = nextInstrAddr;
    emit(Machine.JUMPop, 0, Machine.CBr, 0);
    patch(jumpifAddr, nextInstrAddr);
    ast.C2.visit(this, frame);
    patch(jumpAddr, nextInstrAddr);
    return null;
  }

  public Object visitLetCommand(LetCommand ast, Object o) {
    Frame frame = (Frame) o;
    int extraSize = ((Integer) ast.D.visit(this, frame)).intValue();
    ast.C.visit(this, new Frame(frame, extraSize));
    if (extraSize > 0)
      emit(Machine.POPop, 0, 0, extraSize);
    return null;
  }

  public Object visitSequentialCommand(SequentialCommand ast, Object o) {
    ast.C1.visit(this, o);
    ast.C2.visit(this, o);
    return null;
  }


  public Object visitWhileLoopCommand(WhileLoopCommand ast, Object o) {
    Frame frame = (Frame) o;
    int jumpAddr, loopAddr;

    jumpAddr = nextInstrAddr;
    emit(Machine.JUMPop, 0, Machine.CBr, 0);
    loopAddr = nextInstrAddr;
    ast.C.visit(this, frame);
    patch(jumpAddr, nextInstrAddr);
    ast.E.visit(this, frame);
    emit(Machine.JUMPIFop, Machine.trueRep, Machine.CBr, loopAddr);
    return null;
  }
  
  
  
  // New Loop ASTs
  
  // @author        Andres
  // @descripcion   Metodos encoder para comandos loop
  // @funcionalidad Metodo encoder para Until loop
  // @codigo        J.1
  public Object visitUntilLoopCommand(UntilLoopCommand ast, Object o) {
    Frame frame = (Frame) o;
    int jumpAddr, loopAddr;
    
    jumpAddr = nextInstrAddr;
    emit(Machine.JUMPop, 0, Machine.CBr, 0);
    loopAddr = nextInstrAddr;
    ast.C.visit(this, frame);
    patch(jumpAddr, nextInstrAddr);
    ast.E.visit(this, frame);
    emit(Machine.JUMPIFop, Machine.falseRep, Machine.CBr, loopAddr);
    return null;
  }
  // END CAMBIO Joseph
  
  public Object visitDoLoopWhileCommand(DoLoopWhileCommand ast, Object o) {
    Frame frame = (Frame) o;
    int loopAddr;
    loopAddr = nextInstrAddr;
    ast.C.visit(this, frame);
    ast.E.visit(this, frame);
    emit(Machine.JUMPIFop, Machine.trueRep, Machine.CBr, loopAddr);
    return null;
  }
  
  public Object visitDoLoopUntilCommand(DoLoopUntilCommand ast, Object o) {
    Frame frame = (Frame) o;
    int loopAddr;
    loopAddr = nextInstrAddr;
    ast.C.visit(this, frame);
    ast.E.visit(this, frame);
    emit(Machine.JUMPIFop, Machine.falseRep, Machine.CBr, loopAddr);
    return null;
  }
  
  public Object visitForFromCommand(ForFromCommand ast, Object o) {
      return null;
  }  
  
  public Object visitForLoopDoCommand(ForLoopDoCommand ast, Object o) {
    Frame frame = (Frame) o;
    int loopAddr, jumpAddr;
    int supSize = ((Integer) ast.E.visit(this, frame)).intValue();
    Frame frame1 = new Frame(frame, supSize);
    int idSize = ((Integer) ast.FFC.E.visit(this, frame1)).intValue();
    ast.FFC.D.entity = new KnownAddress(Machine.addressSize, frame1.level, frame1.size);
    jumpAddr = nextInstrAddr;
    emit(Machine.JUMPop, 0, Machine.CBr, 0);
    loopAddr = nextInstrAddr;
    ast.C.visit(this, new Frame(frame, supSize + idSize));
    emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.succDisplacement);
    patch(jumpAddr,nextInstrAddr);
    emit(Machine.LOADop, supSize + idSize, Machine.LBr, 0);
    emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.geDisplacement);
    emit(Machine.JUMPIFop, Machine.trueRep, Machine.CBr, loopAddr);
    emit(Machine.POPop, 0, 0, supSize + idSize);
    return null;
  }
  
  public Object visitForLoopWhileCommand(ForLoopWhileCommand ast, Object o) {
      return null;
  }
  
  public Object visitForLoopUntilCommand(ForLoopUntilCommand ast, Object o) {
      return null;
  }
 // End loops ASTs
  
  
  public Object visitFunction(Function ast, Object o) {
      return null;
  }
  
  public Object visitProcedure(Procedure ast, Object o) {
      return null;
  }
 
  public Object visitSequentialProcFuncs(SequentialProcFuncs ast, Object o) {
      return null;
  }
  
  // @author        Ignacio
  // @descripcion   Implementación de visitVarExpDeclaration
  // @funcionalidad Implementación de visitVarExpDeclaration
  // @codigo        I.2
  
  public Object visitVarExpDeclaration(VarExpDeclaration ast, Object o) {
    Frame frame = (Frame) o;
    int extraSize;
    extraSize = ((Integer)ast.T.visit(this, null));
    emit(Machine.PUSHop, 0, 0, extraSize);
    ast.entity = new KnownAddress(Machine.addressSize, frame.level, frame.size);
    
   
    Integer valSize = (Integer) ast.E.visit(this, frame);
    Identifier id = new Identifier(ast.I.spelling,ast.position);
    id.decl = ast;
    
    VarName varn = new SimpleVarName(id,ast.position);

    SimpleVname vn = new SimpleVname(varn,ast.position);
    encodeStore(vn, new Frame (frame, valSize), valSize);
    writeTableDetails(ast);  
    return extraSize;
  }
  
  //END CAMBIO IGNACIO
  
  public Object visitRecDeclaration(RecDeclaration ast, Object o) {
      return null;
  }
  
  public Object visitPrivDeclaration(PrivDeclaration ast, Object o) {
      return null;
  }
     
    
  // END CAMBIO Joseph
  
    // @author        Andres
    // @descripcion   Metodo encoder para visitar visitCaseLiterals
    // @funcionalidad AST visitCaseLiterals
    // @codigo        A.58
    public Object visitCaseLiterals(CaseLiterals ast, Object o) {
        return null;
    }
     // END Cambio Andres
 
 // @author        Andres
    // @descripcion   Metodo encoder para visitar Choose command
    // @funcionalidad AST ChooseCommand
    // @codigo        A.100
    public Object visitChooseCommand(ChooseCommand ast, Object o) {
      return null;
    }
    // END cambio Andres

  // Cases
  // @author        Andres
  // @descripcion   Metodo encoder para visitar CaseLiteral
  // @funcionalidad AST CaseLiteral
  // @codigo        A.32
  public Object visitCaseLiteral(CaseLiteral ast, Object o) {
    return null;
  }
  // END cambio Andres

  // @author        Andres
  // @descripcion   Metodo encoder para visitar SimpleCaseRange
  // @funcionalidad AST SimpleCaseRange
  // @codigo        A.43
  public Object visitSimpleCaseRange(SimpleCaseRange ast, Object o) {
    return null;
  }
  // END cambio Andres

  // @author        Andres
  // @descripcion   Metodo encoder para visitar CompoundCaseRange
  // @funcionalidad AST CompoundCaseRange
  // @codigo        A.44
  public Object visitCompoundCaseRange(CompoundCaseRange ast, Object o) {
    return null;
  }
  // END cambio Andres

    // @author        Andres
    // @descripcion   Metodo encoder para visitar SequentialCaseRange
    // @funcionalidad AST SequentialCaseRange
    // @codigo        A.59
    public Object visitSequentialCaseRange(SequentialCaseRange ast, Object o) {
        return null;
    }
    // END Cambio Andres
    
    // @author        Andres
    // @descripcion   Metodos encoder para visitar ASTS nuevos
    // @funcionalidad ElseCase AST
    // @codigo        A.83
    public Object visitElseCase(ElseCase ast, Object o) {
        return null;
    }
     // END Cambio Andres
    
    // @author        Andres
    // @descripcion   Metodos encoder para visitar ASTS nuevos
    // @funcionalidad CompoundCases AST
    // @codigo        A.84
    public Object visitCompoundCases(CompoundCases ast, Object o) {
       return null;
    }
     // END Cambio Andres
    
    // @author        Andres
    // @descripcion   Metodos encoder para visitar ASTS nuevos
    // @funcionalidad SequentialCase AST
    // @codigo        A.85
    public Object visitSequentialCase(SequentialCase ast, Object o) {
       return null;
    }
     // END Cambio Andres
    
    // @author        Andres
    // @descripcion   Metodos encoder para visitar ASTS nuevos
    // @funcionalidad SimpleCases AST
    // @codigo        A.86
    public Object visitSimpleCases(SimpleCases ast, Object o) {
        return null;
    }
     // END Cambio Andres
    
    // @author        Andres
    // @descripcion   Metodos encoder para visitar ASTS nuevos
    // @funcionalidad SingleCase AST
    // @codigo        A.87
    public Object visitSingleCase(SingleCase ast, Object o) {
        return null;
    }
     // END Cambio Andres

  // Expressions
  public Object visitArrayExpression(ArrayExpression ast, Object o) {
    ast.type.visit(this, null);
    return ast.AA.visit(this, o);
  }

  public Object visitBinaryExpression(BinaryExpression ast, Object o) {
    Frame frame = (Frame) o;
    Integer valSize = (Integer) ast.type.visit(this, null);
    int valSize1 = ((Integer) ast.E1.visit(this, frame)).intValue();
    Frame frame1 = new Frame(frame, valSize1);
    int valSize2 = ((Integer) ast.E2.visit(this, frame1)).intValue();
    Frame frame2 = new Frame(frame.level, valSize1 + valSize2);
    ast.O.visit(this, frame2);
    return valSize;
  }

   // @author        Joseph
  // @descripcion   Cambio en metodo encoder para visitar CallExpression
  // @funcionalidad Cambio en las alternativas de single-command
  // @codigo        J.68
  public Object visitCallExpression(CallExpression ast, Object o) {
    Frame frame = (Frame) o;
    Integer valSize = (Integer) ast.type.visit(this, null);
    Integer argsSize = (Integer) ast.APS.visit(this, frame);
    ast.LI.visit(this, new Frame(frame.level, argsSize));
    return valSize;
  }
  /*J.68
   public Object visitCallExpression(CallExpression ast, Object o) {
    Frame frame = (Frame) o;
    Integer valSize = (Integer) ast.type.visit(this, null);
    Integer argsSize = (Integer) ast.APS.visit(this, frame);
    ast.I.visit(this, new Frame(frame.level, argsSize));
    return valSize;
  }
  */
  // END CAMBIO Joseph

  public Object visitCharacterExpression(CharacterExpression ast,
						Object o) {
    Frame frame = (Frame) o;
    Integer valSize = (Integer) ast.type.visit(this, null);
    emit(Machine.LOADLop, 0, 0, ast.CL.spelling.charAt(1));
    return valSize;
  }

  public Object visitEmptyExpression(EmptyExpression ast, Object o) {
    return new Integer(0);
  }

  public Object visitIfExpression(IfExpression ast, Object o) {
    Frame frame = (Frame) o;
    Integer valSize;
    int jumpifAddr, jumpAddr;

    ast.type.visit(this, null);
    ast.E1.visit(this, frame);
    jumpifAddr = nextInstrAddr;
    emit(Machine.JUMPIFop, Machine.falseRep, Machine.CBr, 0);
    valSize = (Integer) ast.E2.visit(this, frame);
    jumpAddr = nextInstrAddr;
    emit(Machine.JUMPop, 0, Machine.CBr, 0);
    patch(jumpifAddr, nextInstrAddr);
    valSize = (Integer) ast.E3.visit(this, frame);
    patch(jumpAddr, nextInstrAddr);
    return valSize;
  }

  public Object visitIntegerExpression(IntegerExpression ast, Object o) {
    Frame frame = (Frame) o;
    Integer valSize = (Integer) ast.type.visit(this, null);
    emit(Machine.LOADLop, 0, 0, Integer.parseInt(ast.IL.spelling));
    return valSize;
  }
  
  // @author        Ignacio
  // @descripcion   Implementación Let Command
  // @funcionalidad Implementación Let Command
  // @codigo        I.3

  public Object visitLetExpression(LetExpression ast, Object o) {
    Frame frame = (Frame) o;
    ast.type.visit(this, null);
    int extraSize = ((Integer) ast.D.visit(this, frame)).intValue();
    Frame frame1 = new Frame(frame, extraSize);
    Integer valSize = (Integer) ast.E.visit(this, frame1);
    if (extraSize > 0)
      emit(Machine.POPop, valSize.intValue(), 0, extraSize);
    return valSize;
  }
  
  // END CAMBIO IGNACIO

  public Object visitRecordExpression(RecordExpression ast, Object o){
    ast.type.visit(this, null);
    return ast.RA.visit(this, o);
  }

  public Object visitUnaryExpression(UnaryExpression ast, Object o) {
    Frame frame = (Frame) o;
    Integer valSize = (Integer) ast.type.visit(this, null);
    ast.E.visit(this, frame);
    ast.O.visit(this, new Frame(frame.level, valSize.intValue()));
    return valSize;
  }

  public Object visitVnameExpression(VnameExpression ast, Object o) {
    Frame frame = (Frame) o;
    Integer valSize = (Integer) ast.type.visit(this, null);
    encodeFetch(ast.V, frame, valSize);
    return valSize;
  }


  // Declarations
  public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast,
					       Object o){
    return new Integer(0);
  }

  public Object visitConstDeclaration(ConstDeclaration ast, Object o) {
    Frame frame = (Frame) o;
    int extraSize = 0;

    if (ast.E instanceof CharacterExpression) {
        CharacterLiteral CL = ((CharacterExpression) ast.E).CL;
        ast.entity = new KnownValue(Machine.characterSize,
                                 characterValuation(CL.spelling));
    } else if (ast.E instanceof IntegerExpression) {
        IntegerLiteral IL = ((IntegerExpression) ast.E).IL;
        ast.entity = new KnownValue(Machine.integerSize,
				 Integer.parseInt(IL.spelling));
    } else {
      int valSize = ((Integer) ast.E.visit(this, frame)).intValue();
      ast.entity = new UnknownValue(valSize, frame.level, frame.size);
      extraSize = valSize;
    }
    writeTableDetails(ast);
    return new Integer(extraSize);
  }

  public Object visitFuncDeclaration(FuncDeclaration ast, Object o) {
    Frame frame = (Frame) o;
    int jumpAddr = nextInstrAddr;
    int argsSize = 0, valSize = 0;

    emit(Machine.JUMPop, 0, Machine.CBr, 0);
    ast.entity = new KnownRoutine(Machine.closureSize, frame.level, nextInstrAddr);
    writeTableDetails(ast);
    if (frame.level == Machine.maxRoutineLevel)
      reporter.reportRestriction("can't nest routines more than 7 deep");
    else {
      Frame frame1 = new Frame(frame.level + 1, 0);
      argsSize = ((Integer) ast.FPS.visit(this, frame1)).intValue();
      Frame frame2 = new Frame(frame.level + 1, Machine.linkDataSize);
      valSize = ((Integer) ast.E.visit(this, frame2)).intValue();
    }
    emit(Machine.RETURNop, valSize, 0, argsSize);
    patch(jumpAddr, nextInstrAddr);
    return new Integer(0);
  }

  public Object visitProcDeclaration(ProcDeclaration ast, Object o) {
    Frame frame = (Frame) o;
    int jumpAddr = nextInstrAddr;
    int argsSize = 0;

    emit(Machine.JUMPop, 0, Machine.CBr, 0);
    ast.entity = new KnownRoutine (Machine.closureSize, frame.level,
                                nextInstrAddr);
    writeTableDetails(ast);
    if (frame.level == Machine.maxRoutineLevel)
      reporter.reportRestriction("can't nest routines so deeply");
    else {
      Frame frame1 = new Frame(frame.level + 1, 0);
      argsSize = ((Integer) ast.FPS.visit(this, frame1)).intValue();
      Frame frame2 = new Frame(frame.level + 1, Machine.linkDataSize);
      ast.C.visit(this, frame2);
    }
    emit(Machine.RETURNop, 0, 0, argsSize);
    patch(jumpAddr, nextInstrAddr);
    return new Integer(0);
  }

  public Object visitSequentialDeclaration(SequentialDeclaration ast, Object o) {
    Frame frame = (Frame) o;
    int extraSize1, extraSize2;
    
    if(ast.packageEx != null){
        ast.D1.packageEx = ast.packageEx;
        ast.D2.packageEx = ast.packageEx;
    }
    extraSize1 = ((Integer) ast.D1.visit(this, frame)).intValue();
    Frame frame1 = new Frame (frame, extraSize1);
    extraSize2 = ((Integer) ast.D2.visit(this, frame1)).intValue();
    return new Integer(extraSize1 + extraSize2);
  }

  public Object visitTypeDeclaration(TypeDeclaration ast, Object o) {
    // just to ensure the type's representation is decided
    ast.T.visit(this, null);
    return new Integer(0);
  }

  public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast,
					      Object o) {
    return new Integer(0);
  }

   // @author        Joseph
   // @descripcion   Cambio de var como alternativa de single-declaration
   // @funcionalidad Cambio en las alternativas de single-declaration
   // @codigo        J.45

   public Object visitVarTDDeclaration(VarTDDeclaration ast, Object o) {
       
       Frame frame = (Frame) o;
       int extraSize;
       extraSize = ((Integer) ast.T.visit(this, null)).intValue();
       emit(Machine.PUSHop, 0, 0, extraSize);
       ast.entity = new KnownAddress(Machine.addressSize, frame.level, frame.size);
       writeTableDetails(ast);
       
       return new Integer(extraSize);
     }


   /* J.45
      public Object visitVarDeclaration(VarDeclaration ast, Object o) {
       Frame frame = (Frame) o;
       int extraSize;
       extraSize = (Integer) ast.T.visit(this, null)).intValue();
       emit(Machine.PUSHop, 0, 0, extraSize);
       ast.entity = new KnownAddress(Machine.addressSize, frame.level, frame.size);
       writeTableDetails(ast);
       return new Integer(extraSize);
     }
    */
  // END CAMBIO Joseph

  // Array Aggregates
  public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast,
					    Object o) {
    Frame frame = (Frame) o;
    int elemSize = ((Integer) ast.E.visit(this, frame)).intValue();
    Frame frame1 = new Frame(frame, elemSize);
    int arraySize = ((Integer) ast.AA.visit(this, frame1)).intValue();
    return new Integer(elemSize + arraySize);
  }

  public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object o) {
    return ast.E.visit(this, o);
  }


  // Record Aggregates
  public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast,
					     Object o) {
    Frame frame = (Frame) o;
    int fieldSize = ((Integer) ast.E.visit(this, frame)).intValue();
    Frame frame1 = new Frame (frame, fieldSize);
    int recordSize = ((Integer) ast.RA.visit(this, frame1)).intValue();
    return new Integer(fieldSize + recordSize);
  }

  public Object visitSingleRecordAggregate(SingleRecordAggregate ast,
					   Object o) {
    return ast.E.visit(this, o);
  }


  // Formal Parameters
  public Object visitConstFormalParameter(ConstFormalParameter ast, Object o) {
    Frame frame = (Frame) o;
    int valSize = ((Integer) ast.T.visit(this, null)).intValue();
    ast.entity = new UnknownValue (valSize, frame.level, -frame.size - valSize);
    writeTableDetails(ast);
    return new Integer(valSize);
  }

  public Object visitFuncFormalParameter(FuncFormalParameter ast, Object o) {
    Frame frame = (Frame) o;
    int argsSize = Machine.closureSize;
    ast.entity = new UnknownRoutine (Machine.closureSize, frame.level,
				  -frame.size - argsSize);
    writeTableDetails(ast);
    return new Integer(argsSize);
  }

  public Object visitProcFormalParameter(ProcFormalParameter ast, Object o) {
    Frame frame = (Frame) o;
    int argsSize = Machine.closureSize;
    ast.entity = new UnknownRoutine (Machine.closureSize, frame.level,
				  -frame.size - argsSize);
    writeTableDetails(ast);
    return new Integer(argsSize);
  }

  public Object visitVarFormalParameter(VarFormalParameter ast, Object o) {
    Frame frame = (Frame) o;
    ast.T.visit(this, null);
    ast.entity = new UnknownAddress (Machine.addressSize, frame.level,
				  -frame.size - Machine.addressSize);
    writeTableDetails(ast);
    return new Integer(Machine.addressSize);
  }


  public Object visitEmptyFormalParameterSequence(
	 EmptyFormalParameterSequence ast, Object o) {
    return new Integer(0);
  }

  public Object visitMultipleFormalParameterSequence(
 	 MultipleFormalParameterSequence ast, Object o) {
    Frame frame = (Frame) o;
    int argsSize1 = ((Integer) ast.FPS.visit(this, frame)).intValue();
    Frame frame1 = new Frame(frame, argsSize1);
    int argsSize2 = ((Integer) ast.FP.visit(this, frame1)).intValue();
    return new Integer(argsSize1 + argsSize2);
  }

  public Object visitSingleFormalParameterSequence(
	 SingleFormalParameterSequence ast, Object o) {
    return ast.FP.visit (this, o);
  }


  // Actual Parameters
  public Object visitConstActualParameter(ConstActualParameter ast, Object o) {
    return ast.E.visit (this, o);
  }

  public Object visitFuncActualParameter(FuncActualParameter ast, Object o) {
    Frame frame = (Frame) o;
    if (ast.I.decl.entity instanceof KnownRoutine) {
      ObjectAddress address = ((KnownRoutine) ast.I.decl.entity).address;
      // static link, code address
      emit(Machine.LOADAop, 0, displayRegister(frame.level, address.level), 0);
      emit(Machine.LOADAop, 0, Machine.CBr, address.displacement);
    } else if (ast.I.decl.entity instanceof UnknownRoutine) {
      ObjectAddress address = ((UnknownRoutine) ast.I.decl.entity).address;
      emit(Machine.LOADop, Machine.closureSize, displayRegister(frame.level,
           address.level), address.displacement);
    } else if (ast.I.decl.entity instanceof PrimitiveRoutine) {
      int displacement = ((PrimitiveRoutine) ast.I.decl.entity).displacement;
      // static link, code address
      emit(Machine.LOADAop, 0, Machine.SBr, 0);
      emit(Machine.LOADAop, 0, Machine.PBr, displacement);
    }
    return new Integer(Machine.closureSize);
  }

  public Object visitProcActualParameter(ProcActualParameter ast, Object o) {
    Frame frame = (Frame) o;
    if (ast.I.decl.entity instanceof KnownRoutine) {
      ObjectAddress address = ((KnownRoutine) ast.I.decl.entity).address;
      // static link, code address
      emit(Machine.LOADAop, 0, displayRegister(frame.level, address.level), 0);
      emit(Machine.LOADAop, 0, Machine.CBr, address.displacement);
    } else if (ast.I.decl.entity instanceof UnknownRoutine) {
      ObjectAddress address = ((UnknownRoutine) ast.I.decl.entity).address;
      emit(Machine.LOADop, Machine.closureSize, displayRegister(frame.level,
           address.level), address.displacement);
    } else if (ast.I.decl.entity instanceof PrimitiveRoutine) {
      int displacement = ((PrimitiveRoutine) ast.I.decl.entity).displacement;
      // static link, code address
      emit(Machine.LOADAop, 0, Machine.SBr, 0);
      emit(Machine.LOADAop, 0, Machine.PBr, displacement);
    }
    return new Integer(Machine.closureSize);
  }

  public Object visitVarActualParameter(VarActualParameter ast, Object o) {
    encodeFetchAddress(ast.V, (Frame) o);
    return new Integer(Machine.addressSize);
  }


  public Object visitEmptyActualParameterSequence(
	 EmptyActualParameterSequence ast, Object o) {
    return new Integer(0);
  }

  public Object visitMultipleActualParameterSequence(
	 MultipleActualParameterSequence ast, Object o) {
    Frame frame = (Frame) o;
    int argsSize1 = ((Integer) ast.AP.visit(this, frame)).intValue();
    Frame frame1 = new Frame (frame, argsSize1);
    int argsSize2 = ((Integer) ast.APS.visit(this, frame1)).intValue();
    return new Integer(argsSize1 + argsSize2);
  }

  public Object visitSingleActualParameterSequence(
	 SingleActualParameterSequence ast, Object o) {
    return ast.AP.visit (this, o);
  }


  // Type Denoters
  public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o) {
    return new Integer(0);
  }

  public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o) {
    int typeSize;
    if (ast.entity == null) {
      int elemSize = ((Integer) ast.T.visit(this, null)).intValue();
      typeSize = Integer.parseInt(ast.IL.spelling) * elemSize;
      ast.entity = new TypeRepresentation(typeSize);
      writeTableDetails(ast);
    } else
      typeSize = ast.entity.size;
    return new Integer(typeSize);
  }

  public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o) {
    if (ast.entity == null) {
      ast.entity = new TypeRepresentation(Machine.booleanSize);
      writeTableDetails(ast);
    }
    return new Integer(Machine.booleanSize);
  }

  public Object visitCharTypeDenoter(CharTypeDenoter ast, Object o) {
    if (ast.entity == null) {
      ast.entity = new TypeRepresentation(Machine.characterSize);
      writeTableDetails(ast);
    }
    return new Integer(Machine.characterSize);
  }

  public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o) {
    return new Integer(0);
  }

  public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast,
					   Object o) {
    return new Integer(0);
  }

  public Object visitIntTypeDenoter(IntTypeDenoter ast, Object o) {
    if (ast.entity == null) {
      ast.entity = new TypeRepresentation(Machine.integerSize);
      writeTableDetails(ast);
    }
    return new Integer(Machine.integerSize);
  }

  public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object o) {
    int typeSize;
    if (ast.entity == null) {
      typeSize = ((Integer) ast.FT.visit(this, new Integer(0))).intValue();
      ast.entity = new TypeRepresentation(typeSize);
      writeTableDetails(ast);
    } else
      typeSize = ast.entity.size;
    return new Integer(typeSize);
  }


  public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast,
					      Object o) {
    int offset = ((Integer) o).intValue();
    int fieldSize;

    if (ast.entity == null) {
      fieldSize = ((Integer) ast.T.visit(this, null)).intValue();
      ast.entity = new Field (fieldSize, offset);
      writeTableDetails(ast);
    } else
      fieldSize = ast.entity.size;

    Integer offset1 = new Integer(offset + fieldSize);
    int recSize = ((Integer) ast.FT.visit(this, offset1)).intValue();
    return new Integer(fieldSize + recSize);
  }

  public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast,
					    Object o) {
    int offset = ((Integer) o).intValue();
    int fieldSize;

    if (ast.entity == null) {
      fieldSize = ((Integer) ast.T.visit(this, null)).intValue();
      ast.entity = new Field (fieldSize, offset);
      writeTableDetails(ast);
    } else
      fieldSize = ast.entity.size;

    return new Integer(fieldSize);
  }


  // Literals, Identifiers and Operators
  public Object visitCharacterLiteral(CharacterLiteral ast, Object o) {
    return null;
  }

  public Object visitIdentifier(Identifier ast, Object o) {
    Frame frame = (Frame) o;
    if (ast.decl.entity instanceof KnownRoutine) {
      ObjectAddress address = ((KnownRoutine) ast.decl.entity).address;
      emit(Machine.CALLop, displayRegister(frame.level, address.level),
	   Machine.CBr, address.displacement);
    } else if (ast.decl.entity instanceof UnknownRoutine) {
      ObjectAddress address = ((UnknownRoutine) ast.decl.entity).address;
      emit(Machine.LOADop, Machine.closureSize, displayRegister(frame.level,
           address.level), address.displacement);
      emit(Machine.CALLIop, 0, 0, 0);
    } else if (ast.decl.entity instanceof PrimitiveRoutine) {
      int displacement = ((PrimitiveRoutine) ast.decl.entity).displacement;
      if (displacement != Machine.idDisplacement)
        emit(Machine.CALLop, Machine.SBr, Machine.PBr, displacement);
    } else if (ast.decl.entity instanceof EqualityRoutine) { // "=" or "\="
      int displacement = ((EqualityRoutine) ast.decl.entity).displacement;
      emit(Machine.LOADLop, 0, 0, frame.size / 2);
      emit(Machine.CALLop, Machine.SBr, Machine.PBr, displacement);
    }
    return null;
  }

  public Object visitIntegerLiteral(IntegerLiteral ast, Object o) {
    return null;
  }

  public Object visitOperator(Operator ast, Object o) {
    Frame frame = (Frame) o;
    if (ast.decl.entity instanceof KnownRoutine) {
      ObjectAddress address = ((KnownRoutine) ast.decl.entity).address;
      emit(Machine.CALLop, displayRegister (frame.level, address.level),
	   Machine.CBr, address.displacement);
    } else if (ast.decl.entity instanceof UnknownRoutine) {
      ObjectAddress address = ((UnknownRoutine) ast.decl.entity).address;
      emit(Machine.LOADop, Machine.closureSize, displayRegister(frame.level,
           address.level), address.displacement);
      emit(Machine.CALLIop, 0, 0, 0);
    } else if (ast.decl.entity instanceof PrimitiveRoutine) {
      int displacement = ((PrimitiveRoutine) ast.decl.entity).displacement;
      if (displacement != Machine.idDisplacement)
        emit(Machine.CALLop, Machine.SBr, Machine.PBr, displacement);
    } else if (ast.decl.entity instanceof EqualityRoutine) { // "=" or "\="
      int displacement = ((EqualityRoutine) ast.decl.entity).displacement;
      emit(Machine.LOADLop, 0, 0, frame.size / 2);
      emit(Machine.CALLop, Machine.SBr, Machine.PBr, displacement);
    }
    return null;
  }


  // Value-or-variable names
  
    // @author        Andres
    // @descripcion   Agregar metodos de visita encoder de nuevos ASTs VarName, Vname y package
    // @funcionalidad metodos de visita encoder para AST de Varname, Vname y package
    // @codigo        A.116
  public Object visitDotVarName(DotVarName ast, Object o) {
    Frame frame = (Frame) o;
    RuntimeEntity baseObject = (RuntimeEntity) ast.V.visit(this, frame);
    ast.offset = ast.V.offset + ((Field) ast.I.decl.entity).fieldOffset;
                   // I.decl points to the appropriate record field
    ast.indexed = ast.V.indexed;
    return baseObject;
  }

  public Object visitSimpleVarName(SimpleVarName ast, Object o) {
    ast.offset = 0;
    ast.indexed = false;
    return ast.I.decl.entity;
  }

  public Object visitSubscriptVarName(SubscriptVarName ast, Object o) {
    Frame frame = (Frame) o;
    RuntimeEntity baseObject;
    int elemSize, indexSize;

    baseObject = (RuntimeEntity) ast.V.visit(this, frame);
    ast.offset = ast.V.offset;
    ast.indexed = ast.V.indexed;
    elemSize = ((Integer) ast.type.visit(this, null)).intValue();
    if (ast.E instanceof IntegerExpression) {
      IntegerLiteral IL = ((IntegerExpression) ast.E).IL;
      ast.offset = ast.offset + Integer.parseInt(IL.spelling) * elemSize;
    } else {
      // v-name is indexed by a proper expression, not a literal
      if (ast.indexed)
        frame.size = frame.size + Machine.integerSize;
      indexSize = ((Integer) ast.E.visit(this, frame)).intValue();
      if (elemSize != 1) {
        emit(Machine.LOADLop, 0, 0, elemSize);
        emit(Machine.CALLop, Machine.SBr, Machine.PBr,
             Machine.multDisplacement);
      }
      if (ast.indexed)
        emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
      else
        ast.indexed = true;
    }
    return baseObject;
  }
  
  public Object visitSimpleVname(SimpleVname ast, Object o) {
      return ast.VN.visit(this, o);
  }
  
  public Object visitPackageIdentifier(PackageIdentifier ast, Object o) {
      ast.I.visit(this, o);
      return null;
  }
  
  public Object visitPackageVname(PackageVname ast, Object o) {
      return ast.VN.visit(this, o);
  }
  
  public Object visitSimpleLongIdentifier(SimpleLongIdentifier ast, Object o) {
    return ast.I.visit(this, o);
  }
    
  public Object visitPackageLongIdentifier(PackageLongIdentifier ast, Object o) {
    return null;
  }
    
  public Object visitSinglePackageDeclaration(SinglePackageDeclaration ast, Object o) {
      System.out.println("1");
    Frame frame = (Frame) o;
    ast.D.packageEx = ast.PI.I.spelling;
    int extraSize = ((Integer) ast.D.visit(this, frame)).intValue();
    
    return extraSize;
  }
  
  public Object visitSequentialPackageDeclaration(SequentialPackageDeclaration ast, Object o) {
      Frame frame = (Frame) o;
      int extraSize = ((Integer) ast.PD1.visit(this, frame)).intValue();
      int extraSizeSeq = ((Integer) ast.PD2.visit(this,new Frame(frame,extraSize))).intValue();
      return extraSize + extraSizeSeq;
  }
  
  /*
  public Object visitDotVname(DotVname ast, Object o) {
    Frame frame = (Frame) o;
    RuntimeEntity baseObject = (RuntimeEntity) ast.V.visit(this, frame);
    ast.offset = ast.V.offset + ((Field) ast.I.decl.entity).fieldOffset;
                   // I.decl points to the appropriate record field
    ast.indexed = ast.V.indexed;
    return baseObject;
  }

  public Object visitSimpleVname(SimpleVname ast, Object o) {
    ast.offset = 0;
    ast.indexed = false;
    return ast.I.decl.entity;
  }

  public Object visitSubscriptVname(SubscriptVname ast, Object o) {
    Frame frame = (Frame) o;
    RuntimeEntity baseObject;
    int elemSize, indexSize;

    baseObject = (RuntimeEntity) ast.V.visit(this, frame);
    ast.offset = ast.V.offset;
    ast.indexed = ast.V.indexed;
    elemSize = ((Integer) ast.type.visit(this, null)).intValue();
    if (ast.E instanceof IntegerExpression) {
      IntegerLiteral IL = ((IntegerExpression) ast.E).IL;
      ast.offset = ast.offset + Integer.parseInt(IL.spelling) * elemSize;
    } else {
      // v-name is indexed by a proper expression, not a literal
      if (ast.indexed)
        frame.size = frame.size + Machine.integerSize;
      indexSize = ((Integer) ast.E.visit(this, frame)).intValue();
      if (elemSize != 1) {
        emit(Machine.LOADLop, 0, 0, elemSize);
        emit(Machine.CALLop, Machine.SBr, Machine.PBr,
             Machine.multDisplacement);
      }
      if (ast.indexed)
        emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
      else
        ast.indexed = true;
    }
    return baseObject;
  }
  */
  // END Cambio Andres


  // Programs
  
  
// @author        Andres
// @descripcion   Agregar metodos de visita encoder de nuevos ASTs VarName, Vname y package
// @funcionalidad metodos de visita encoder para AST de Varname, Vname y package
// @codigo        A.139
  public Object visitSimpleProgram(SimpleProgram ast, Object o) {
      return ast.C.visit(this, o);
  }
  
  public Object visitCompoundProgram(CompoundProgram ast, Object o) {
      Frame frame = (Frame) o;
      Integer extraSize = (Integer)ast.PD.visit(this, frame);
      ast.C.visit(this, new Frame(frame,extraSize));
      
      if (extraSize > 0)
        emit(Machine.POPop, 0, 0, extraSize);  
      
      return null;
      
  }
  /*
  public Object visitProgram(Program ast, Object o) {
    return ast.C.visit(this, o);
  }
  */
  // END CAMBIO Andres

  public Encoder (ErrorReporter reporter) {
    this.reporter = reporter;
    nextInstrAddr = Machine.CB;
    elaborateStdEnvironment();
  }

  private ErrorReporter reporter;

  // Generates code to run a program.
  // showingTable is true iff entity description details
  // are to be displayed.
  public final void encodeRun (Program theAST, boolean showingTable) {
    tableDetailsReqd = showingTable;
    //startCodeGeneration();
    theAST.visit(this, new Frame (0, 0));
    emit(Machine.HALTop, 0, 0, 0);
  }

  // Decides run-time representation of a standard constant.
  private final void elaborateStdConst (Declaration constDeclaration,
					int value) {

    if (constDeclaration instanceof ConstDeclaration) {
      ConstDeclaration decl = (ConstDeclaration) constDeclaration;
      int typeSize = ((Integer) decl.E.type.visit(this, null)).intValue();
      decl.entity = new KnownValue(typeSize, value);
      writeTableDetails(constDeclaration);
    }
  }

  // Decides run-time representation of a standard routine.
  private final void elaborateStdPrimRoutine (Declaration routineDeclaration,
                                          int routineOffset) {
    routineDeclaration.entity = new PrimitiveRoutine (Machine.closureSize, routineOffset);
    writeTableDetails(routineDeclaration);
  }

  private final void elaborateStdEqRoutine (Declaration routineDeclaration,
                                          int routineOffset) {
    routineDeclaration.entity = new EqualityRoutine (Machine.closureSize, routineOffset);
    writeTableDetails(routineDeclaration);
  }

  private final void elaborateStdRoutine (Declaration routineDeclaration,
                                          int routineOffset) {
    routineDeclaration.entity = new KnownRoutine (Machine.closureSize, 0, routineOffset);
    writeTableDetails(routineDeclaration);
  }

  private final void elaborateStdEnvironment() {
    tableDetailsReqd = false;
    elaborateStdConst(StdEnvironment.falseDecl, Machine.falseRep);
    elaborateStdConst(StdEnvironment.trueDecl, Machine.trueRep);
    elaborateStdPrimRoutine(StdEnvironment.notDecl, Machine.notDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.andDecl, Machine.andDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.orDecl, Machine.orDisplacement);
    elaborateStdConst(StdEnvironment.maxintDecl, Machine.maxintRep);
    elaborateStdPrimRoutine(StdEnvironment.addDecl, Machine.addDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.subtractDecl, Machine.subDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.multiplyDecl, Machine.multDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.divideDecl, Machine.divDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.moduloDecl, Machine.modDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.lessDecl, Machine.ltDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.notgreaterDecl, Machine.leDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.greaterDecl, Machine.gtDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.notlessDecl, Machine.geDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.chrDecl, Machine.idDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.ordDecl, Machine.idDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.eolDecl, Machine.eolDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.eofDecl, Machine.eofDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.getDecl, Machine.getDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.putDecl, Machine.putDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.getintDecl, Machine.getintDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.putintDecl, Machine.putintDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.geteolDecl, Machine.geteolDisplacement);
    elaborateStdPrimRoutine(StdEnvironment.puteolDecl, Machine.puteolDisplacement);
    elaborateStdEqRoutine(StdEnvironment.equalDecl, Machine.eqDisplacement);
    elaborateStdEqRoutine(StdEnvironment.unequalDecl, Machine.neDisplacement);
  }

  // Saves the object program in the named file.

  public void saveObjectProgram(String objectName) {
    FileOutputStream objectFile = null;
    DataOutputStream objectStream = null;

    int addr;

    try {
      objectFile = new FileOutputStream (objectName);
      objectStream = new DataOutputStream (objectFile);

      addr = Machine.CB;
      for (addr = Machine.CB; addr < nextInstrAddr; addr++)
        Machine.code[addr].write(objectStream);
      objectFile.close();
    } catch (FileNotFoundException s) {
      System.err.println ("Error opening object file: " + s);
    } catch (IOException s) {
      System.err.println ("Error writing object file: " + s);
    }
  }

  boolean tableDetailsReqd;

  public static void writeTableDetails(AST ast) {
  }

  // OBJECT CODE

  // Implementation notes:
  // Object code is generated directly into the TAM Code Store, starting at CB.
  // The address of the next instruction is held in nextInstrAddr.

  private int nextInstrAddr;

  // Appends an instruction, with the given fields, to the object code.
  private void emit (int op, int n, int r, int d) {
    Instruction nextInstr = new Instruction();
    if (n > 255) {
        reporter.reportRestriction("length of operand can't exceed 255 words");
        n = 255; // to allow code generation to continue
    }
    nextInstr.op = op;
    nextInstr.n = n;
    nextInstr.r = r;
    nextInstr.d = d;
    if (nextInstrAddr == Machine.PB)
      reporter.reportRestriction("too many instructions for code segment");
    else {
        Machine.code[nextInstrAddr] = nextInstr;
        nextInstrAddr = nextInstrAddr + 1;
    }
  }

  // Patches the d-field of the instruction at address addr.
  private void patch (int addr, int d) {
    Machine.code[addr].d = d;
  }

  // DATA REPRESENTATION

  public int characterValuation (String spelling) {
  // Returns the machine representation of the given character literal.
    return spelling.charAt(1);
      // since the character literal is of the form 'x'}
  }

  // REGISTERS

  // Returns the register number appropriate for object code at currentLevel
  // to address a data object at objectLevel.
  private int displayRegister (int currentLevel, int objectLevel) {
    if (objectLevel == 0)
      return Machine.SBr;
    else if (currentLevel - objectLevel <= 6)
      return Machine.LBr + currentLevel - objectLevel; // LBr|L1r|...|L6r
    else {
      reporter.reportRestriction("can't access data more than 6 levels out");
      return Machine.L6r;  // to allow code generation to continue
    }
  }

  // Generates code to fetch the value of a named constant or variable
  // and push it on to the stack.
  // currentLevel is the routine level where the vname occurs.
  // frameSize is the anticipated size of the local stack frame when
  // the constant or variable is fetched at run-time.
  // valSize is the size of the constant or variable's value.
  
  
  // @author        Ignacio
  // @descripcion   Compatibilidad de Encodes Arreglada
  // @funcionalidad Compatibilidad con VarName
  // @codigo        I.1

  private void encodeStore(Vname V, Frame frame, int valSize) {

    if (V instanceof SimpleVname){
        SimpleVname svn = (SimpleVname) V;
        RuntimeEntity baseObject = (RuntimeEntity) svn.VN.visit(this, frame);
        // If indexed = true, code will have been generated to load an index value.
        if (valSize > 255) {
          reporter.reportRestriction("can't store values larger than 255 words");
          valSize = 255; // to allow code generation to continue
        }
        if (baseObject instanceof KnownAddress) {
          ObjectAddress address = ((KnownAddress) baseObject).address;
          if (svn.VN.indexed) {
            emit(Machine.LOADAop, 0, displayRegister(frame.level, address.level),
                 address.displacement + svn.VN.offset);
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
            emit(Machine.STOREIop, valSize, 0, 0);
          } else {
            emit(Machine.STOREop, valSize, displayRegister(frame.level,
                 address.level), address.displacement + svn.VN.offset);
          }
        } else if (baseObject instanceof UnknownAddress) {
          ObjectAddress address = ((UnknownAddress) baseObject).address;
          emit(Machine.LOADop, Machine.addressSize, displayRegister(frame.level,
               address.level), address.displacement);
          if (svn.VN.indexed)
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
          if (svn.VN.offset != 0) {
            emit(Machine.LOADLop, 0, 0, svn.VN.offset);
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
          }
          emit(Machine.STOREIop, valSize, 0, 0);
        }
    }
    
    else{
        PackageVname pvn = (PackageVname) V;
        RuntimeEntity baseObject = (RuntimeEntity) pvn.VN.visit(this, frame);
        // If indexed = true, code will have been generated to load an index value.
        if (valSize > 255) {
          reporter.reportRestriction("can't store values larger than 255 words");
          valSize = 255; // to allow code generation to continue
        }
        if (baseObject instanceof KnownAddress) {
          ObjectAddress address = ((KnownAddress) baseObject).address;
          if (pvn.VN.indexed) {
            emit(Machine.LOADAop, 0, displayRegister(frame.level, address.level),
                 address.displacement + pvn.VN.offset);
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
            emit(Machine.STOREIop, valSize, 0, 0);
          } else {
            emit(Machine.STOREop, valSize, displayRegister(frame.level,
                 address.level), address.displacement + pvn.VN.offset);
          }
        } else if (baseObject instanceof UnknownAddress) {
          ObjectAddress address = ((UnknownAddress) baseObject).address;
          emit(Machine.LOADop, Machine.addressSize, displayRegister(frame.level,
               address.level), address.displacement);
          if (pvn.VN.indexed)
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
          if (pvn.VN.offset != 0) {
            emit(Machine.LOADLop, 0, 0, pvn.VN.offset);
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
          }
          emit(Machine.STOREIop, valSize, 0, 0);
        }
    }
    
  }
  


  // Generates code to fetch the value of a named constant or variable
  // and push it on to the stack.
  // currentLevel is the routine level where the vname occurs.
  // frameSize is the anticipated size of the local stack frame when
  // the constant or variable is fetched at run-time.
  // valSize is the size of the constant or variable's value.

  private void encodeFetch(Vname V, Frame frame, int valSize) {
    
    if (V instanceof SimpleVname){
        SimpleVname svn = (SimpleVname) V;
        RuntimeEntity baseObject = (RuntimeEntity) svn.VN.visit(this, frame);
        // If indexed = true, code will have been generated to load an index value.
        if (valSize > 255) {
          reporter.reportRestriction("can't load values larger than 255 words");
          valSize = 255; // to allow code generation to continue
        }
        if (baseObject instanceof KnownValue) {
          // presumably offset = 0 and indexed = false
          int value = ((KnownValue) baseObject).value;
          emit(Machine.LOADLop, 0, 0, value);
        } else if ((baseObject instanceof UnknownValue) ||
                   (baseObject instanceof KnownAddress)) {
          ObjectAddress address = (baseObject instanceof UnknownValue) ?
                                  ((UnknownValue) baseObject).address :
                                  ((KnownAddress) baseObject).address;
          if (svn.VN.indexed) {
            emit(Machine.LOADAop, 0, displayRegister(frame.level, address.level),
                 address.displacement + svn.VN.offset);
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
            emit(Machine.LOADIop, valSize, 0, 0);
          } else
            emit(Machine.LOADop, valSize, displayRegister(frame.level,
                 address.level), address.displacement + svn.VN.offset);
        } else if (baseObject instanceof UnknownAddress) {
          ObjectAddress address = ((UnknownAddress) baseObject).address;
          emit(Machine.LOADop, Machine.addressSize, displayRegister(frame.level,
               address.level), address.displacement);
          if (svn.VN.indexed)
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
          if (svn.VN.offset != 0) {
            emit(Machine.LOADLop, 0, 0, svn.VN.offset);
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
          }
          emit(Machine.LOADIop, valSize, 0, 0);
        }
    }
    else{
        PackageVname pvn = (PackageVname) V;
        RuntimeEntity baseObject = (RuntimeEntity) pvn.VN.visit(this, frame);
        // If indexed = true, code will have been generated to load an index value.
        if (valSize > 255) {
          reporter.reportRestriction("can't load values larger than 255 words");
          valSize = 255; // to allow code generation to continue
        }
        if (baseObject instanceof KnownValue) {
          // presumably offset = 0 and indexed = false
          int value = ((KnownValue) baseObject).value;
          emit(Machine.LOADLop, 0, 0, value);
        } else if ((baseObject instanceof UnknownValue) ||
                   (baseObject instanceof KnownAddress)) {
          ObjectAddress address = (baseObject instanceof UnknownValue) ?
                                  ((UnknownValue) baseObject).address :
                                  ((KnownAddress) baseObject).address;
          if (pvn.VN.indexed) {
            emit(Machine.LOADAop, 0, displayRegister(frame.level, address.level),
                 address.displacement + pvn.VN.offset);
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
            emit(Machine.LOADIop, valSize, 0, 0);
          } else
            emit(Machine.LOADop, valSize, displayRegister(frame.level,
                 address.level), address.displacement + pvn.VN.offset);
        } else if (baseObject instanceof UnknownAddress) {
          ObjectAddress address = ((UnknownAddress) baseObject).address;
          emit(Machine.LOADop, Machine.addressSize, displayRegister(frame.level,
               address.level), address.displacement);
          if (pvn.VN.indexed)
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
          if (pvn.VN.offset != 0) {
            emit(Machine.LOADLop, 0, 0, pvn.VN.offset);
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
          }
          emit(Machine.LOADIop, valSize, 0, 0);
        }
    }
  }

  // Generates code to compute and push the address of a named variable.
  // vname is the program phrase that names this variable.
  // currentLevel is the routine level where the vname occurs.
  // frameSize is the anticipated size of the local stack frame when
  // the variable is addressed at run-time.

  private void encodeFetchAddress (Vname V, Frame frame) {

    if (V instanceof SimpleVname){
        
        SimpleVname svn = (SimpleVname) V;
        RuntimeEntity baseObject = (RuntimeEntity) svn.VN.visit(this, frame);
        // If indexed = true, code will have been generated to load an index value.
        if (baseObject instanceof KnownAddress) {
          ObjectAddress address = ((KnownAddress) baseObject).address;
          emit(Machine.LOADAop, 0, displayRegister(frame.level, address.level),
               address.displacement + svn.VN.offset);
          if (svn.VN.indexed)
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
        } else if (baseObject instanceof UnknownAddress) {
          ObjectAddress address = ((UnknownAddress) baseObject).address;
          emit(Machine.LOADop, Machine.addressSize,displayRegister(frame.level,
               address.level), address.displacement);
          if (svn.VN.indexed)
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
          if (svn.VN.offset != 0) {
            emit(Machine.LOADLop, 0, 0, svn.VN.offset);
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
          }
        }
    }
    
    else{
        
        PackageVname pvn = (PackageVname) V;
        RuntimeEntity baseObject = (RuntimeEntity) pvn.VN.visit(this, frame);
        // If indexed = true, code will have been generated to load an index value.
        if (baseObject instanceof KnownAddress) {
          ObjectAddress address = ((KnownAddress) baseObject).address;
          emit(Machine.LOADAop, 0, displayRegister(frame.level, address.level),
               address.displacement + pvn.VN.offset);
          if (pvn.VN.indexed)
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
        } else if (baseObject instanceof UnknownAddress) {
          ObjectAddress address = ((UnknownAddress) baseObject).address;
          emit(Machine.LOADop, Machine.addressSize,displayRegister(frame.level,
               address.level), address.displacement);
          if (pvn.VN.indexed)
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
          if (pvn.VN.offset != 0) {
            emit(Machine.LOADLop, 0, 0, pvn.VN.offset);
            emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
          }
        }
    }
    
  }
  
  /*
  
  private void encodeStore(Vname V, Frame frame, int valSize) {

    RuntimeEntity baseObject = (RuntimeEntity) V.visit(this, frame);
    // If indexed = true, code will have been generated to load an index value.
    if (valSize > 255) {
      reporter.reportRestriction("can't store values larger than 255 words");
      valSize = 255; // to allow code generation to continue
    }
    if (baseObject instanceof KnownAddress) {
      ObjectAddress address = ((KnownAddress) baseObject).address;
      if (V.indexed) {
        emit(Machine.LOADAop, 0, displayRegister(frame.level, address.level),
             address.displacement + V.offset);
        emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
        emit(Machine.STOREIop, valSize, 0, 0);
      } else {
        emit(Machine.STOREop, valSize, displayRegister(frame.level,
	     address.level), address.displacement + V.offset);
      }
    } else if (baseObject instanceof UnknownAddress) {
      ObjectAddress address = ((UnknownAddress) baseObject).address;
      emit(Machine.LOADop, Machine.addressSize, displayRegister(frame.level,
           address.level), address.displacement);
      if (V.indexed)
        emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
      if (V.offset != 0) {
        emit(Machine.LOADLop, 0, 0, V.offset);
        emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
      }
      emit(Machine.STOREIop, valSize, 0, 0);
    }
  }

  // Generates code to fetch the value of a named constant or variable
  // and push it on to the stack.
  // currentLevel is the routine level where the vname occurs.
  // frameSize is the anticipated size of the local stack frame when
  // the constant or variable is fetched at run-time.
  // valSize is the size of the constant or variable's value.

  private void encodeFetch(Vname V, Frame frame, int valSize) {

    RuntimeEntity baseObject = (RuntimeEntity) V.visit(this, frame);
    // If indexed = true, code will have been generated to load an index value.
    if (valSize > 255) {
      reporter.reportRestriction("can't load values larger than 255 words");
      valSize = 255; // to allow code generation to continue
    }
    if (baseObject instanceof KnownValue) {
      // presumably offset = 0 and indexed = false
      int value = ((KnownValue) baseObject).value;
      emit(Machine.LOADLop, 0, 0, value);
    } else if ((baseObject instanceof UnknownValue) ||
               (baseObject instanceof KnownAddress)) {
      ObjectAddress address = (baseObject instanceof UnknownValue) ?
                              ((UnknownValue) baseObject).address :
                              ((KnownAddress) baseObject).address;
      if (V.indexed) {
        emit(Machine.LOADAop, 0, displayRegister(frame.level, address.level),
             address.displacement + V.offset);
        emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
        emit(Machine.LOADIop, valSize, 0, 0);
      } else
        emit(Machine.LOADop, valSize, displayRegister(frame.level,
	     address.level), address.displacement + V.offset);
    } else if (baseObject instanceof UnknownAddress) {
      ObjectAddress address = ((UnknownAddress) baseObject).address;
      emit(Machine.LOADop, Machine.addressSize, displayRegister(frame.level,
           address.level), address.displacement);
      if (V.indexed)
        emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
      if (V.offset != 0) {
        emit(Machine.LOADLop, 0, 0, V.offset);
        emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
      }
      emit(Machine.LOADIop, valSize, 0, 0);
    }
  }

  // Generates code to compute and push the address of a named variable.
  // vname is the program phrase that names this variable.
  // currentLevel is the routine level where the vname occurs.
  // frameSize is the anticipated size of the local stack frame when
  // the variable is addressed at run-time.

  private void encodeFetchAddress (Vname V, Frame frame) {

    RuntimeEntity baseObject = (RuntimeEntity) V.visit(this, frame);
    // If indexed = true, code will have been generated to load an index value.
    if (baseObject instanceof KnownAddress) {
      ObjectAddress address = ((KnownAddress) baseObject).address;
      emit(Machine.LOADAop, 0, displayRegister(frame.level, address.level),
           address.displacement + V.offset);
      if (V.indexed)
        emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
    } else if (baseObject instanceof UnknownAddress) {
      ObjectAddress address = ((UnknownAddress) baseObject).address;
      emit(Machine.LOADop, Machine.addressSize,displayRegister(frame.level,
           address.level), address.displacement);
      if (V.indexed)
        emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
      if (V.offset != 0) {
        emit(Machine.LOADLop, 0, 0, V.offset);
        emit(Machine.CALLop, Machine.SBr, Machine.PBr, Machine.addDisplacement);
      }
    }
  }
  
  */
  
  //END CAMBIO IGNACIO

    @Override
    public Object visitFuncDeclaration2(FuncDeclaration ast, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitProcDeclaration2(ProcDeclaration ast, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitProcedure2(Procedure ast, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitFunction2(Function ast, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
