package com.annimon.jmr.models;

import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class MethodComparators {

    private static final Comparator<Method> BY_NAME = comparator(MethodDeclaration::getName);
    private static final Comparator<Method> BY_NAME_REVERSED = comparator(MethodDeclaration::getName).reversed();
    private static final Comparator<Method> BY_PARAMETERS_COUNT = comparator(m -> m.getParameters().size());
    private static final Comparator<Method> BY_PARAMETERS_COUNT_REVERSED = comparator(m -> m.getParameters().size()).reversed();
    private static final Comparator<Method> BY_THROWS_COUNT = comparator(m -> m.getThrows().size());
    private static final Comparator<Method> BY_THROWS_COUNT_REVERSED = comparator(m -> m.getThrows().size()).reversed();

    private static final Comparator<Method> BY_ACCESS_PUBLIC = comparatorAccess(a -> a == AccessSpecifier.PUBLIC).reversed();
    private static final Comparator<Method> BY_ACCESS_PROTECTED = comparatorAccess(a -> a == AccessSpecifier.PROTECTED).reversed();
    private static final Comparator<Method> BY_ACCESS_DEFAULT = comparatorAccess(a -> a == AccessSpecifier.DEFAULT).reversed();
    private static final Comparator<Method> BY_ACCESS_PRIVATE = comparatorAccess(a -> a == AccessSpecifier.PRIVATE).reversed();

    private static final Comparator<Method> BY_MODIFIER_ABSTRACT = comparatorModifiers(ModifierSet::isAbstract).reversed();
    private static final Comparator<Method> BY_MODIFIER_FINAL = comparatorModifiers(ModifierSet::isFinal).reversed();
    private static final Comparator<Method> BY_MODIFIER_NATIVE = comparatorModifiers(ModifierSet::isNative).reversed();
    private static final Comparator<Method> BY_MODIFIER_STATIC = comparatorModifiers(ModifierSet::isStatic).reversed();
    private static final Comparator<Method> BY_MODIFIER_SYNCHRONIZED = comparatorModifiers(ModifierSet::isSynchronized).reversed();

    public static Comparator<Method> byName() {
        return BY_NAME;
    }

    public static Comparator<Method> byNameReversed() {
        return BY_NAME_REVERSED;
    }

    public static Comparator<Method> byParametersCount() {
        return BY_PARAMETERS_COUNT;
    }

    public static Comparator<Method> byParametersCountReversed() {
        return BY_PARAMETERS_COUNT_REVERSED;
    }

    public static Comparator<Method> byThrowsCount() {
        return BY_THROWS_COUNT;
    }

    public static Comparator<Method> byThrowsCountReversed() {
        return BY_THROWS_COUNT_REVERSED;
    }

    public static Comparator<Method> byAccessPublic() {
        return BY_ACCESS_PUBLIC;
    }

    public static Comparator<Method> byAccessProtected() {
        return BY_ACCESS_PROTECTED;
    }

    public static Comparator<Method> byAccessDefault() {
        return BY_ACCESS_DEFAULT;
    }

    public static Comparator<Method> byAccessPrivate() {
        return BY_ACCESS_PRIVATE;
    }

    public static Comparator<Method> byModifierAbstract() {
        return BY_MODIFIER_ABSTRACT;
    }

    public static Comparator<Method> byModifierFinal() {
        return BY_MODIFIER_FINAL;
    }

    public static Comparator<Method> byModifierNative() {
        return BY_MODIFIER_NATIVE;
    }

    public static Comparator<Method> byModifierStatic() {
        return BY_MODIFIER_STATIC;
    }

    public static Comparator<Method> byModifierSynchronized() {
        return BY_MODIFIER_SYNCHRONIZED;
    }

    public static Comparator<Method> build(List<Sort> sorts) {
        Comparator<Method> comparator = Comparator.naturalOrder();
        for (Sort sort : sorts) {
            if (sort.isEnabled()) {
                comparator = comparator.thenComparing(sort.getComparator());
            }
        }
        return comparator;
    }

    private static <T extends Comparable<? super T>> Comparator<Method> comparator(
            Function<MethodDeclaration, ? extends T> extractor) {
        return Comparator.comparing(m -> extractor.apply(m.getMethod()));
    }

    private static <T extends Comparable<? super T>> Comparator<Method> comparatorModifiers(
            IntFunction<? extends T> extractor) {
        return comparator(m -> extractor.apply(m.getModifiers()));
    }

    private static <T extends Comparable<? super T>> Comparator<Method> comparatorAccess(
            Function<AccessSpecifier, ? extends T> extractor) {
        return comparatorModifiers(m -> extractor.apply(ModifierSet.getAccessSpecifier(m)));
    }
}
