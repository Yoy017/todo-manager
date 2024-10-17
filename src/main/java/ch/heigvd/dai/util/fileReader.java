package ch.heigvd.dai.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ch.heigvd.dai.util.Status;

public class fileReader {
    private final String filePath;

    public fileReader(String filename) {
        String fileExtension = ".tdm";
        this.filePath = "todoManagerList/" + filename + fileExtension;
    }

    public Vector<Task> getAllTask() throws FileNotFoundException {
        Vector<Task> tasks = new Vector<>();
        Task currentTask = null;

        try (
                Reader fi = new FileReader(filePath, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(fi)
        ) {
            String line;
            Pattern taskPattern = Pattern.compile("\\[(.*?)\\{(.*?)\\}\\] \\[(.*?)\\]");
            Pattern subTaskPattern = Pattern.compile("\\t- \\[(.*?)\\]");

            while ((line = br.readLine()) != null) {
                Matcher taskMatcher = taskPattern.matcher(line);
                Matcher subTaskMatcher = subTaskPattern.matcher(line);

                if (taskMatcher.find()) {
                    String name = taskMatcher.group(1);
                    String description = taskMatcher.group(2);
                    String stateString = taskMatcher.group(3);

                    Status state = Status.fromLabel(stateString);
                    currentTask = new Task(name, description, state); // Initialise currentTask ici
                    tasks.add(currentTask); // Ajoute la tâche à la liste
                } else if (subTaskMatcher.find()) {
                    if (currentTask != null) {
                        String subTaskName = subTaskMatcher.group(1);
                        SubTask subTask = new SubTask(subTaskName, currentTask.id);
                        currentTask.addSubTask(subTask); // Ajouter la sous-tâche à la tâche actuelle
                    } else {
                        System.err.println("Error: Found a sub-task without a parent task");
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File " + filePath + " not found");
            throw new FileNotFoundException();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tasks;
    }
}
