package com.annimon.jmr.models;

import com.github.javaparser.ast.body.MethodDeclaration;
import java.util.Comparator;
import java.util.function.Function;

public final class MethodComparators {

    private static final Comparator<Method> BY_NAME = comparator(MethodDeclaration::getName);
    private static final Comparator<Method> BY_PARAMETERS_COUNT = comparator(m -> m.getParameters().size());

    public static Comparator<Method> byName() {
        return BY_NAME;
    }

    public static Comparator<Method> byParametersCount() {
        return BY_PARAMETERS_COUNT;
    }

    private static <T extends Comparable<? super T>> Comparator<Method> comparator(
            Function<MethodDeclaration, ? extends T> extractor) {
        return Comparator.comparing(m -> extractor.apply(m.getMethod()));
    }
}
