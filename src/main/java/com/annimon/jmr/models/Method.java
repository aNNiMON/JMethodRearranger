package com.annimon.jmr.models;

import com.github.javaparser.ast.body.MethodDeclaration;

public final class Method {

    private final MethodDeclaration method;

    public Method(MethodDeclaration method) {
        this.method = method;
    }

    public MethodDeclaration getMethod() {
        return method;
    }

    public String getDeclaration() {
        return method.getDeclarationAsString();
    }

    @Override
    public String toString() {
        return getDeclaration();
    }
}
