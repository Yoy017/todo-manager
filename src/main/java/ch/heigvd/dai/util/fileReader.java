package ch.heigvd.dai.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class fileReader {
    private final String filePath;

    public fileReader(String filename) {
        String fileExtension = ".tdm";
        this.filePath = "todoManagerFiles/" + filename + fileExtension;
    }

    public Vector<Task> getAllTask() {
        Vector<Task> tasks = new Vector<>();
        Task currentTask = null;

        try (
                Reader fi = new FileReader(filePath, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(fi)
        ) {
            String line;
            Pattern taskPattern = Pattern.compile("\\[(.*?)\\{(.*?)\\}\\]");
            Pattern subTaskPattern = Pattern.compile("\\t- \\[(.*?)\\]");

            while ((line = br.readLine()) != null) {
                Matcher taskMatcher = taskPattern.matcher(line);
                Matcher subTaskMatcher = subTaskPattern.matcher(line);

                if (taskMatcher.find()) {
                    // Nouvelle tâche principale
                    String taskName = taskMatcher.group(1);
                    String taskDescription = taskMatcher.group(2);
                    currentTask = new Task(taskName, taskDescription);
                    tasks.add(currentTask);
                } else if (subTaskMatcher.find() && currentTask != null) {
                    // Sous-tâche associée à la tâche actuelle
                    String subTaskName = subTaskMatcher.group(1);
                    SubTask subTask = new SubTask(subTaskName, currentTask.id);
                    currentTask.addSubTask(subTask); // Ajouter la sous-tâche à la tâche actuelle
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tasks;
    }
}
