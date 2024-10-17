package ch.heigvd.dai.commands;

import ch.heigvd.dai.commands.Root;
import ch.heigvd.dai.util.Task;
import ch.heigvd.dai.util.fileReader;
import ch.heigvd.dai.util.fileWriter;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.Callable;


@CommandLine.Command(name = "delete", description = "Delete a specific task")
public class Delete implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @CommandLine.Option(names = {"-id"}, description = "id of the task", required = true)
    private int id;

    @Override
    public Integer call() {
        fileReader fi = new fileReader(parent.filename);
        Vector<Task> tasks;
        try{
            tasks = fi.getAllTask();
        } catch (FileNotFoundException e) {
            System.out.println("You can only delete tasks in an existing file.");
            return 1;
        }

        if(tasks.isEmpty()){
            System.err.println("No task to delete yet.");
            return 0;
        }
        if(id < 0 || id >= tasks.size()){
            System.err.println("id is out of bound !");
            return 0;
        }

        Task taskToDelete = tasks.elementAt(id);  // Récupérer la tâche à supprimer

        // Demander confirmation à l'utilisateur
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure you want to delete task ID " + id + ": \"" + taskToDelete.name + "\" (y/N)?");
        String confirmation = scanner.nextLine().trim().toLowerCase(); // Source: https://stackoverflow.com/questions/22523770/short-way-to-ask-for-confirmation-java

        // Vérifier la réponse
        if (!confirmation.equals("y")) {
            System.out.println("Task deletion canceled.");
            return 0;
        }

        tasks.remove(id);

        fileWriter fo = new fileWriter(parent.filename);
        fo.overwriteTasks(tasks);
        System.out.println("Task " + id + " successfully deleted.");
        return 0;
    }
}