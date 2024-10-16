package ch.heigvd.dai.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import java.util.regex.Matcher; // Source: https://stackoverflow.com/questions/30661733/how-to-extract-content-between-two-words-in-a-text-file-using-java
import java.util.regex.Pattern;
import ch.heigvd.dai.util.Task;

public class fileReader {

    public Vector<Task> getAllTask() {
        Vector<Task> tasks = new Vector<>();
        try (
                Reader fi = new FileReader("src/main/java/ch/heigvd/dai/output.md", StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(fi)
        ) {
            String line;
            // Regex pour extraire le nom et la description des tâches
            Pattern pattern = Pattern.compile("\\[(.*?)\\{(.*?)\\}\\]");

            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String taskName = matcher.group(1);
                    String taskDescription = matcher.group(2);

                    // Crée un objet Task avec les valeurs extraites et l'ajoute à la liste
                    Task task = new Task(taskName, taskDescription);
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }
}
