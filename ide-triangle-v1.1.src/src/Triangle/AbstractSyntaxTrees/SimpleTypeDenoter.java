/*
 * @(#)SimpleTypeDenoter.java                        2.1 2003/10/07
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

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

// @author        Joseph
// @description   Cambio de alternativa de type-denoter, de identifier a long identifier
// @funcionalidad Cambio en las alternativas de type-denoter
// @codigo        J.63

public class SimpleTypeDenoter extends TypeDenoter {

  public SimpleTypeDenoter (LongIdentifier liAST, SourcePosition thePosition) {
    super (thePosition);
    LI = liAST;
  }

  public Object visit (Visitor v, Object o) {
    return v.visitSimpleTypeDenoter(this, o);
  }

  public Object visitXML(Visitor v, Object o) {
    return v.visitSimpleTypeDenoter(this, o);
  }

  public boolean equals (Object obj) {
    return false; // should not happen
  }

  public LongIdentifier LI;

    @Override
    public Object visit2(Visitor v, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

/* J.63
public class SimpleTypeDenoter extends TypeDenoter {

  public SimpleTypeDenoter (Identifier iAST, SourcePosition thePosition) {
    super (thePosition);
    I = iAST;
  }

  public Object visit (Visitor v, Object o) {
    return v.visitSimpleTypeDenoter(this, o);
  }

  public boolean equals (Object obj) {
    return false; // should not happen
  }

  public Identifier I;
}
*/
// END CAMBIO Joseph
