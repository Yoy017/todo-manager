package ch.heigvd.dai.commands;

import ch.heigvd.dai.commands.Root;
import ch.heigvd.dai.util.Status;
import ch.heigvd.dai.util.SubTask;
import ch.heigvd.dai.util.Task;
import ch.heigvd.dai.util.fileReader;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "show", description = "Display all created tasks")
public class ShowTasks implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @CommandLine.Parameters(index = "0", description = "The name of the file.")
    protected String filename;

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
            System.out.println("List \"" + filename + "\" does not exist.");
            return 1;
        }

        // Création d'une instance de fileReader pour lire les tâches depuis le fichier
        fileReader fi = new fileReader(filename);
        Vector<Task> tasks;
        try {
            tasks = fi.getAllTask();
        } catch (FileNotFoundException e) {
            System.out.println("List: " + filename + " doesn't exist.");
            return 1;
        }

        // Si aucune tâche n'est présente, afficher un message approprié
        if (tasks.isEmpty()) {
            System.out.println("No tasks created in the list.");
            return 0;
        }

        // Affichage des tâches, en filtrant par statut si un statut est fourni
        for(Task task : tasks){
            if (state == null || task.state == state) {
                System.out.println(task);
                task.getSubTasks().forEach(subTask -> System.out.println("\t" + subTask));
            }
        }
        return 0;
    }
}