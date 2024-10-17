package ch.heigvd.dai.commands;

import ch.heigvd.dai.commands.Root;
import ch.heigvd.dai.util.Status;
import ch.heigvd.dai.util.Task;
import ch.heigvd.dai.util.fileReader;
import ch.heigvd.dai.util.fileWriter;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "update", description = "Update a task")
public class Update implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @CommandLine.Parameters(index = "0", description = "The name of the file.")
    protected String filename;

    @CommandLine.Option(names = {"-id"}, description = "ID of the task to update", required = true)
    private int id;

    @CommandLine.Option(names = {"-t", "--title"}, description = "New title of the task")
    private String title;

    @CommandLine.Option(names = {"-d", "--description"}, description = "New description of the task")
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

        fileReader fi = new fileReader(filename);
        Vector<Task> tasks;

        try {
            tasks = fi.getAllTask();
        } catch (FileNotFoundException e) {
            System.err.println("Error: The specified list does not exist or cannot be read.");
            return 1;
        }

        if (tasks.isEmpty()) {
            System.err.println("No tasks to update.");
            return 0;
        }

        if (id <= 0 || id > tasks.size()) {
            System.err.println("Error: Task ID is out of bounds. It should be between 1 and " + tasks.size());
            return 1;
        }

        Task taskToUpdate = tasks.get(id);

        if (title == null && description == null && state == null) {
            System.out.println("No changes made.");
            return 0;
        }

        // Application des modifications
        if (title != null) {
            taskToUpdate.name = title;
        }

        if (description != null) {
            taskToUpdate.description = description;
        }

        if (state != null) {
            taskToUpdate.state = state;
        }

        // Sauvegarder les changements dans le fichier
        fileWriter fw = new fileWriter(filename);
        fw.overwriteTasks(tasks);

        System.out.println("Task " + id + " successfully updated.");
        return 0;
    }
}