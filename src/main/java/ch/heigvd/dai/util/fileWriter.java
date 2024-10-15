package ch.heigvd.dai.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import ch.heigvd.dai.util.Task;

public class fileWriter {

    public void writeFile(Task task, String outputFilename) {
        try (
                Writer writer = new FileWriter(outputFilename, StandardCharsets.UTF_8, true);
                BufferedWriter bw = new BufferedWriter(writer)
        ) {
            // Write the content to the file
            String content = "\n- [ ] [" + task.name + "{" + task.description + "}]";
            bw.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}