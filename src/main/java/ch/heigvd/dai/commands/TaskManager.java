package ch.heigvd.dai.commands;

import picocli.CommandLine;

import java.util.Scanner;
import java.util.Vector;
import ch.heigvd.dai.util.fileWriter;
import ch.heigvd.dai.util.fileReader;
import ch.heigvd.dai.util.Task;

import java.util.concurrent.Callable;

// Commande principale pour gérer la todo-list
@CommandLine.Command(
        name = "task",
        description = "Todo list management commands.\n",
        subcommands = { CommandLine.HelpCommand.class } // Ajoute une commande d'aide intégrée
)
public class TaskManager {
    // Sous-commande pour créer une tâche
    @CommandLine.Command(name = "create", description = "Create a new todo list")
    public static class CreateTask implements Callable<Integer> {
        @CommandLine.Option(names = {"-t", "--title"}, description = "Title of the list")
        private String title;

        @CommandLine.Option(names = {"-d", "--description"}, description = "Description of the task")
        private String description;

        @Override
        public Integer call() {
            Task task;
            if(title == null)
                task = new Task();
            else if(description == null)
                task = new Task(title);
            else
                task = new Task(title, description);

            fileWriter fw = new fileWriter();
            fw.writeFile(task, true);
            System.out.println("Task successfully created.");
            return 0;
        }
    }

    // Sous-commande pour afficher toutes les tâches
    @CommandLine.Command(name = "show", description = "Show all tasks")
    public static class ShowTasks implements Callable<Integer> {

        @Override
        public Integer call() {
            fileReader fi = new fileReader();
            Vector<Task> tasks = fi.getAllTask();
            if(tasks.isEmpty()) {
                System.err.println("No task created yet.");
                return 0;
            }
            for(int i = 0; i < tasks.size(); ++i)
                System.err.println(tasks.elementAt(i));
            return 0;
        }
    }

    // Sous-commande pour supprimer une tâche spécifique
    @CommandLine.Command(name = "delete", description = "Delete a specific")
    public static class deleteTask implements Callable<Integer> {

        @CommandLine.Option(names = {"-id"}, description = "id of the task", required = true)
        private int id;

        @Override
        public Integer call() {
            fileReader fi = new fileReader();
            Vector<Task> tasks = fi.getAllTask();
            if(tasks.isEmpty()){
                System.err.println("No task to delete yet.");
                return 0;
            }
            if(id < 0 || id > tasks.size()){
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

            fileWriter fo = new fileWriter();
            fo.overwriteTasks(tasks);
            System.out.println("Task " + id + " successfully deleted.");
            return 0;
        }
    }

    // Sous-commande pour mettre à jour les informations d'une tâche
    @CommandLine.Command(name = "update", description = "Update a task")
    public static class updateTask implements Callable<Integer> {

        @CommandLine.Option(names = {"-id"}, description = "ID of the task to update", required = true)
        private int id;

        @CommandLine.Option(names = {"-t", "--title"}, description = "New title of the task")
        private String title;

        @CommandLine.Option(names = {"-d", "--description"}, description = "New description of the task")
        private String description;

        @Override
        public Integer call() {
            fileReader fi = new fileReader();
            Vector<Task> tasks = fi.getAllTask();

            if (tasks.isEmpty()) {
                System.err.println("No tasks to update.");
                return 0;
            }

            if (id <= 0 || id > tasks.size()) {
                System.err.println("ID is out of bounds!");
                return 0;
            }

            Task taskToUpdate = tasks.elementAt(id);

            if (title != null)
                taskToUpdate.name = title;

            if (description != null)
                taskToUpdate.description = description;

            if(title != null && description != null){
                System.out.println("No changes made.");
                return 0;
            }

            fileWriter fo = new fileWriter();
            fo.overwriteTasks(tasks);

            System.out.println("Task " + id + " successfully updated.");
            return 0;
        }
    }
}