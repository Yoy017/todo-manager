package ch.heigvd.dai.commands;

import ch.heigvd.dai.util.Task;
import ch.heigvd.dai.util.fileReader;
import ch.heigvd.dai.util.fileWriter;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "delete", description = "Delete a specific task")
public class Delete implements Callable<Integer> {

    @CommandLine.ParentCommand
    protected Root parent;

    @CommandLine.Parameters(index = "0", description = "The name of the file.")
    protected String filename;

    @CommandLine.Option(names = {"-id"}, description = "ID of the task", required = true)
    private int id;

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

        // Lire les tâches à partir du fichier
        fileReader fi = new fileReader(filename);
        Vector<Task> tasks;
        try {
            tasks = fi.getAllTask(); // Récupérer toutes les tâches
        } catch (FileNotFoundException e) {
            System.out.println("You can only delete tasks in an existing list.");
            return 1;
        }

        // Vérifier s'il y a des tâches dans le fichier
        if (tasks.isEmpty()) {
            System.err.println("No task to delete yet.");
            return 1;
        }

        // Vérifier si l'ID de la tâche est valide
        if (id < 0 || id >= tasks.size()) {
            System.err.println("ID is out of bounds!");
            return 1;
        }

        // Récupérer la tâche à supprimer
        Task taskToDelete = tasks.elementAt(id);

        // Demander confirmation à l'utilisateur
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure you want to delete task ID " + id + ": \"" + taskToDelete.name + "\" (y/N)?");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        // Vérifier la réponse de l'utilisateur
        if (!confirmation.equals("y")) {
            System.out.println("Task deletion canceled.");
            return 0;
        }

        tasks.remove(id);

        // Réécrit les tâches dans le fichier
        fileWriter fo = new fileWriter(filename);
        fo.overwriteTasks(tasks);
        System.out.println("Task " + id + " successfully deleted.");
        return 0;
    }
}