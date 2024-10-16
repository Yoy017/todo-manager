package ch.heigvd.dai.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import ch.heigvd.dai.util.Task;

public class fileWriter {

    public void writeFile(Task task, boolean append) {
        try (
                Writer writer = new FileWriter("src/main/java/ch/heigvd/dai/output.md", StandardCharsets.UTF_8, append);
                BufferedWriter bw = new BufferedWriter(writer)
        ) {
            // Write the content to the file
            String content = "[" + task.name + (task.description != null ? "{" + task.description + "}" : "{}") + "]\n";
            bw.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void overwriteTasks(Vector<Task> tasks){
        for(int i = 0; i < tasks.size(); ++i){
            writeFile(tasks.elementAt(i), i != 0);
        }
    }
}