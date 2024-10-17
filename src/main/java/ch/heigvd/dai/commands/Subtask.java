package ch.heigvd.dai.commands;

import ch.heigvd.dai.commands.Root;
import ch.heigvd.dai.util.SubTask;
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

@CommandLine.Command(name = "subtask", description = "Add a subtask to a specific task")
public class Subtask implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @CommandLine.Parameters(index = "0", description = "The name of the file.")
    protected String filename;

    @CommandLine.Option(names = {"-id"}, description = "ID of the parent task", required = true)
    private int parentId;

    @CommandLine.Option(names = {"-t", "--title"}, description = "Title of the subtask", required = true)
    private String title;

    @Override
    public Integer call() {
        long start = System.currentTimeMillis();

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

        // Lecture des tâches depuis le fichier
        fileReader fr = new fileReader(filename);
        Vector<Task> tasks;
        try {
            tasks = fr.getAllTask();
        } catch (FileNotFoundException e) {
            System.err.println("Error: The specified file does not exist or cannot be read.");
            return 1;
        }

        // Vérification si des tâches existent
        if (tasks.isEmpty()) {
            System.err.println("No tasks created yet.");
            return 0;
        }

        // Vérification si l'ID de la tâche parente est valide
        if (parentId < 0 || parentId >= tasks.size()) {
            System.err.println("Invalid parent task ID: " + parentId);
            return 1;
        }

        Task parentTask = tasks.get(parentId);

        // Ajout de la sous-tâche à la tâche parente
        SubTask newSubTask = new SubTask(title, parentTask.id);
        parentTask.addSubTask(newSubTask);

        // Sauvegarde des modifications dans le fichier
        fileWriter fw = new fileWriter(filename);
        fw.overwriteTasks(tasks);

        System.out.println("Subtask successfully added to task: " + parentTask.name);

        long end = System.currentTimeMillis();
        System.out.println("Execution time: " + (end - start) + "ms");
        return 0;
    }
}