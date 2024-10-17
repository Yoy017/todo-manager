package ch.heigvd.dai.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class fileWriter {
    private final String filePath, directoryname = "todoManagerList";

    public fileWriter(String fileName) {
        String fileExtension = ".tdm";
        this.filePath = directoryname + "/" + fileName + fileExtension;
    }

    public void writeFile(Task task, boolean append) throws FileNotFoundException {
        try (
                Writer writer = new FileWriter(filePath, StandardCharsets.UTF_8, append);
                BufferedWriter bw = new BufferedWriter(writer)
        ) {
            if(task == null){ // It happens when a list contain one task and this task is deleted
                bw.write("");
                return;
            } else {

                // Écriture de la tâche principale
                String content = "[" + task.name + (task.description != null ? "{" + task.description + "}" : "{}") + "] [" + task.state.getLabel() + "]\n";
                bw.write(content);

                // Écriture des sous-tâches
                if (task.sub_tasks != null) {
                    for (SubTask subTask : task.sub_tasks) {
                        String subTaskContent = "\t- [" + subTask.name + "]\n"; // Format de sous-tâche
                        bw.write(subTaskContent);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("List " + filePath + " not found");
            throw new FileNotFoundException();
        } catch (IOException e) {
            System.out.println("Error when writing" + filePath);
            throw new RuntimeException(e);
        }
    }

    public void createFileAndDirectory(String fileName) {
        // Chemin vers le répertoire
        File directory = new File(directoryname);

        // Vérification et création du répertoire s'il n'existe pas
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                System.err.println("Failed to create " + this.filePath);
                return;
            }
        }

        // Création du fichier dans le répertoire
        File file = new File(directory, fileName + ".tdm");
        try {
            if (file.createNewFile()) {
                System.out.println("New list " + fileName + " created successfully.");
            } else {
                System.out.println("List " + fileName + " already exists.");
            }
        } catch (IOException e) {
            System.err.println("Error creating the list: " + e.getMessage());
        }
    }

    // Cette méthode réécrit tout le fichier
    public void overwriteTasks(Vector<Task> tasks) {
        if (tasks.size() == 0) {
            // Si la liste est vide, on efface le contenu du fichier
            try {
                writeFile(null, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // Sinon, on réécrit toutes les tâches comme d'habitude
            for (int i = 0; i < tasks.size(); ++i) {
                try {
                    writeFile(tasks.elementAt(i), i != 0);
                } catch (FileNotFoundException e) {
                    break;
                }
            }
        }

    }
}