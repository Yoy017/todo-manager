package ch.heigvd.dai.commands;

import picocli.CommandLine;

import java.util.Scanner;
import java.util.Vector;
import ch.heigvd.dai.util.fileWriter;
import ch.heigvd.dai.util.fileReader;
import ch.heigvd.dai.util.Task;
import ch.heigvd.dai.util.SubTask;
import ch.heigvd.dai.util.Status;

import java.util.concurrent.Callable;

// Commande principale pour gérer la todo-list
@CommandLine.Command(
        name = "todo-manager",
        description = "Tasks management commands.\n",
        subcommands = { CommandLine.HelpCommand.class } // Ajoute une commande d'aide intégrée
)
public class TaskManager {
    // Sous-commande pour créer une tâche
    @CommandLine.Command(name = "create", description = "Create a new task")
    public static class CreateTask implements Callable<Integer> {
        @CommandLine.Parameters(index = "0", description = "The name of the file.")
        protected String filename;

        @CommandLine.Option(names = {"-t", "--title"}, description = "Title of the task")
        private String title;

        @CommandLine.Option(names = {"-d", "--description"}, description = "Description of the task")
        private String description;

        @CommandLine.Option(names = {"-s", "--status"}, description = "Status of the task")
        private Status state;

        @Override
        public Integer call() {
            Task task;
            if(title == null)
                task = new Task();
            else if(description == null && state == null)
                task = new Task(title);
            else if(description != null && state == null)
                task = new Task(title, description);
            else
                task = new Task(title, description, state);

            fileWriter fw = new fileWriter(filename);
            fw.writeFile(task, true);
            System.out.println("Task successfully created.");
            return 0;
        }
    }

    // Sous-commande pour afficher toutes les tâches
    @CommandLine.Command(name = "show", description = "Display all created tasks")
    public static class ShowTasks implements Callable<Integer> {

        @CommandLine.Parameters(index = "0", description = "The name of the file.")
        protected String filename;

        @Override
        public Integer call() {
            fileReader fi = new fileReader(filename);
            Vector<Task> tasks = fi.getAllTask();

            if(tasks.isEmpty()) {
                System.err.println("No task created yet.");
                return 0;
            }

            for(int i = 0; i < tasks.size(); ++i) {
                System.out.println(tasks.elementAt(i)); // Affiche la tâche
                for (SubTask subTask : tasks.elementAt(i).getSubTasks()) {
                    System.out.println(subTask); // Affiche des sous-tâches
                }
            }
            return 0;
        }
    }

    // Sous-commande pour supprimer une tâche spécifique
    @CommandLine.Command(name = "delete", description = "Delete a specific task")
    public static class deleteTask implements Callable<Integer> {

        @CommandLine.Parameters(index = "0", description = "The name of the file.")
        protected String filename;

        @CommandLine.Option(names = {"-id"}, description = "id of the task", required = true)
        private int id;

        @Override
        public Integer call() {
            fileReader fi = new fileReader(filename);
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

            fileWriter fo = new fileWriter(filename);
            fo.overwriteTasks(tasks);
            System.out.println("Task " + id + " successfully deleted.");
            return 0;
        }
    }

    // Sous-commande pour mettre à jour les informations d'une tâche
    @CommandLine.Command(name = "update", description = "Update a task")
    public static class updateTask implements Callable<Integer> {

        @CommandLine.Parameters(index = "0", description = "The name of the file.")
        protected String filename;

        @CommandLine.Option(names = {"-id"}, description = "ID of the task to update", required = true)
        private int id;

        @CommandLine.Option(names = {"-t", "--title"}, description = "New title of the task")
        private String title;

        @CommandLine.Option(names = {"-d", "--description"}, description = "New description of the task")
        private String description;

        @CommandLine.Option(names = {"-s", "--status"}, description = "Status of the task")
        private Status state;

        @Override
        public Integer call() {
            fileReader fi = new fileReader(filename);
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

            if(title == null && description == null && state == null){
                System.out.println("No changes made");
                return 0;
            }

            if (title != null)
                taskToUpdate.name = title;

            if (description != null)
                taskToUpdate.description = description;

            if(state != null)
                taskToUpdate.state = state;

            fileWriter fo = new fileWriter(filename);

            fo.overwriteTasks(tasks);

            System.out.println("Task " + id + " successfully updated.");
            return 0;
        }
    }

    // Sous-commande pour ajouter une sous-tâche à une tâche existante
    @CommandLine.Command(name = "subtask", description = "Add a subtask to a specific task")
    public static class AddSubTask implements Callable<Integer> {
        @CommandLine.Parameters(index = "0", description = "The name of the file.")
        protected String filename;


        @CommandLine.Option(names = {"-id"}, description = "ID of the parent task", required = true)
        private int parentId;

        @CommandLine.Option(names = {"-t", "--title"}, description = "Title of the subtask", required = true)
        private String title;

        @Override
        public Integer call() {
            fileReader fr = new fileReader(filename);
            Vector<Task> tasks = fr.getAllTask();

            if (tasks.isEmpty()) {
                System.err.println("No tasks created yet.");
                return 0;
            }

            if (parentId < 0 || parentId >= tasks.size()) {
                System.err.println("Invalid parent task ID.");
                return 1;
            }

            Task parentTask = tasks.get(parentId);
            parentTask.addSubTask(new SubTask(title, parentTask.id));

            // Mettre à jour le fichier .tdm
            fileWriter fw = new fileWriter(filename);
            fw.overwriteTasks(tasks);

            System.out.println("Subtask successfully added to task: " + parentTask.name);
            return 0;
        }
    }

}