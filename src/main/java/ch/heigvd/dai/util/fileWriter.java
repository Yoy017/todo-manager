package ch.heigvd.dai.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class fileWriter {

    public void writeFile(String content, String outputFilename) {
        try (
                Writer writer = new FileWriter(outputFilename, StandardCharsets.UTF_8);
                BufferedWriter bw = new BufferedWriter(writer)
        ) {
            // Write the content to the file
            bw.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}