package ch.heigvd.dai.commands;

import ch.heigvd.dai.util.Status;
import ch.heigvd.dai.util.Task;
import ch.heigvd.dai.util.fileWriter;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "create", description = "Create a task in a file")
public class Create implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @CommandLine.Parameters(index = "0", description = "The name of the list.")
    protected String filename;

    @CommandLine.Option(names = {"-t", "--title"}, description = "Title of the task")
    private String title;

    @CommandLine.Option(names = {"-d", "--description"}, description = "Description of the task")
    private String description;

    @CommandLine.Option(names = {"-s", "--status"}, description = "Select a task by status [PENDING, IN_PROGRESS, DONE]")
    private Status state;

    @Override
    public Integer call() {
        // Vérifier si le répertoire todoManagerList existe
        Path dir = Paths.get("todoManagerList");
        if (!Files.exists(dir)) {
            System.out.println("No list created yet.");
            return 1;
        }

        // Vérifier si la liste spécifié existe dans le répertoire
        Path filePath = dir.resolve(filename + ".tdm");
        if (!Files.exists(filePath)) {
            System.out.println("The list \"" + filename + "\" does not exist.");
            return 1;
        }

        // Créer la tâche en fonction des options fournies
        Task task;
        if (title == null) {
            task = new Task();
        } else if (description == null && state == null) {
            task = new Task(title);
        } else if (description != null && state == null) {
            task = new Task(title, description);
        } else {
            task = new Task(title, description, state);
        }

        // Essayer d'écrire la tâche dans le fichier de liste
        fileWriter fw = new fileWriter(filename);
        try {
            fw.writeFile(task, true);
            System.out.println("Task successfully created.");
        } catch (FileNotFoundException e) {
            System.err.println("Error: The specified list file \"" + filename + "\" cannot be found.");
            return 1;
        }
        return 0;
    }
}