package com.annimon.jmr.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Consumer;

public final class IOUtil {

    public static Optional<String> resourceToString(String path) {
        try (InputStream is = IOUtil.class.getResourceAsStream(path)) {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final byte[] buffer = new byte[1024];
            int readed;
            while ( (readed = is.read(buffer, 0, buffer.length)) != -1 ) {
                baos.write(buffer, 0, readed);
            }
            final String content = baos.toString("UTF-8");
            return Optional.of(content);
        } catch (IOException ex) {
            return Optional.empty();
        }
    }

    public static boolean stringToFile(File file, String content, Consumer<File> onSuccess, Consumer<File> onError) {
        try (OutputStream os = new FileOutputStream(file);
             OutputStreamWriter writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            writer.write(content);
            onSuccess.accept(file);
            return true;
        } catch (IOException ioe) {
            onError.accept(file);
            return false;
        }
    }
}
