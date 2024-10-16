package ch.heigvd.dai.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class fileWriter {
    private final String filePath;

    public fileWriter(String fileName) {
        String fileExtension = ".tdm";
        this.filePath = "todoManagerFiles/" + fileName + fileExtension;
    }
    public void writeFile(Task task, boolean append) {

        try (
                Writer writer = new FileWriter(filePath, StandardCharsets.UTF_8, append);
                BufferedWriter bw = new BufferedWriter(writer)
        ) {
            // Écriture de la tâche principale
            String content = "[" + task.name + (task.description != null ? "{" + task.description + "}" : "{}") + "] [Status: " + task.state.getLabel() + "]\n";
            bw.write(content);

            // Écriture des sous-tâches
            if (task.sub_tasks != null) {
                for (SubTask subTask : task.sub_tasks) {
                    String subTaskContent = "\t- [" + subTask.name + "]\n"; // Format de sous-tâche
                    bw.write(subTaskContent);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void overwriteTasks(Vector<Task> tasks) {
        // Cette méthode réécrit tout le fichier
        for (int i = 0; i < tasks.size(); ++i) {
            writeFile(tasks.elementAt(i), i != 0); // append = false pour la première tâche, true ensuite
        }
    }
}