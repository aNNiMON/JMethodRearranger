package com.annimon.jmr.visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

public final class ClassNameVisitor extends GenericVisitorAdapter<String, Void> {

    public static String getClassName(CompilationUnit cu) {
        return new ClassNameVisitor().visit(cu, null);
    }

    @Override
    public String visit(ClassOrInterfaceDeclaration n, Void arg) {
        return n.getName();
    }
}
