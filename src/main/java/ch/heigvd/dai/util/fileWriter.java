package ch.heigvd.dai.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class fileWriter {
    private final String filePath;
    private long duration = 0;

    public fileWriter(String fileName) {
        String fileExtension = ".tdm";
        this.filePath = "todoManagerFiles/" + fileName + fileExtension;
    }

    public void writeTask(Task task, boolean append) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();

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
        } catch (FileNotFoundException e) {
            System.out.println("File " + filePath + " not found");
            throw new FileNotFoundException();
        } catch (IOException e) {
            System.out.println("Error when writing" + filePath);
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis(); // Fin de la mesure du temps
        this.duration = endTime - startTime;
    }

    public void createFileAndDirectory(String fileName) {
        // Chemin vers le répertoire
        File directory = new File("todoManagerFiles");

        // Vérification et création du répertoire s'il n'existe pas
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (dirCreated) {
                System.out.println("Directory 'todoManagerFiles/' successfully created.");
            } else {
                System.err.println("Failed to create directory 'todoManagerFiles/'.");
                return;
            }
        }

        // Création du fichier dans le répertoire
        File file = new File(directory, fileName + ".tdm");
        try {
            if (file.createNewFile()) {
                System.out.println("File '" + fileName + ".tdm' created successfully.");
            } else {
                System.out.println("File '" + fileName + ".tdm' already exists.");
            }
        } catch (IOException e) {
            System.err.println("Error creating the file: " + e.getMessage());
        }
    }

    public void overwriteTasks(Vector<Task> tasks) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < tasks.size(); ++i) {
            try{
                writeTask(tasks.elementAt(i), i != 0); // append = false pour la première tâche, true ensuite
            } catch (FileNotFoundException e) {
                break;
            }
        }

        long endTime = System.currentTimeMillis();
        this.duration = endTime - startTime;
        writeDuration();
    }

    private void writeDuration(){
    try(
            Writer writer = new FileWriter(filePath, StandardCharsets.UTF_8, true);
            BufferedWriter bw = new BufferedWriter(writer)
            ) {
            bw.newLine();
            bw.write("Execution duration: " + this.duration + "ms");
    } catch (IOException e) {
        System.out.println("Error when writing" + filePath);
        throw new RuntimeException(e);
    }
    }
}