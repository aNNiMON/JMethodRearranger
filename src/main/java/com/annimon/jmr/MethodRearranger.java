package com.annimon.jmr;

import com.annimon.jmr.models.Method;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Rearrange methods.
 * 
 * @author aNNiMON
 */
public final class MethodRearranger {

    /**
     * Reads source from string.
     *
     * @param source  string source code
     * @return {@code MethodRearranger} instance
     */
    public static MethodRearranger from(String source) {
        try (InputStream is = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8))) {
            return from(is);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Reads source from stream.
     *
     * @param stream  source code stream
     * @return {@code MethodRearranger} instance
     */
    public static MethodRearranger from(InputStream stream) {
        try {
            return new MethodRearranger(JavaParser.parse(stream));
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    private final CompilationUnit compilationUnit;

    public MethodRearranger(CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    /**
     * Returns {@code CompilationUnit}.
     *
     * @return {@code CompilationUnit}
     */
    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }

    /**
     * Returns {@code Stream} with method declarations.
     *
     * @return {@code Stream} with method declarations
     */
    public Stream<MethodDeclaration> methodDeclarations() {
        return compilationUnit.getTypes().stream()
                .flatMap(type -> type.getMembers().stream())
                .filter(member -> member instanceof MethodDeclaration)
                .map(member -> (MethodDeclaration) member);
    }

    /**
     * Returns {@code Stream} with method declarations wrapped by {@code Method} class.
     *
     * @return {@code Stream} of with method declarations wrapped by {@code Method} class
     */
    public Stream<Method> methods() {
        return methodDeclarations().map(Method::new);
    }

    /**
     * Modifies methods order and returns new {@code CompilationUnit}.
     *
     * @param methods  list of methods to check ordering
     * @return new {@code CompilationUnit}
     */
    public CompilationUnit modifyMethods(List<Method> methods) {
        final CompilationUnit result = new CompilationUnit(
                compilationUnit.getRange(),
                compilationUnit.getPackage(),
                compilationUnit.getImports(),
                compilationUnit.getTypes());

        final List<TypeDeclaration> types = result.getTypes();
        for (TypeDeclaration type : types) {
            final List<BodyDeclaration> members = type.getMembers();
            final List<BodyDeclaration> membersModified = new ArrayList<>(members.size());

            final Iterator<Method> it = methods.iterator();
            for (BodyDeclaration member : members) {
                if (member instanceof MethodDeclaration) {
                    membersModified.add(it.next().getMethod());
                } else {
                    membersModified.add(member);
                }
            }

            type.setMembers(membersModified);
        }
        
        return result;
    }
}
