package ch.heigvd.dai.commands;

import picocli.CommandLine;
import java.util.concurrent.Callable;

import ch.heigvd.dai.util.fileWriter;
import ch.heigvd.dai.util.Task;

// Commande principale pour gérer la todo-list
@CommandLine.Command(
        name = "task",
        description = "Todo list management commands.\n",
        subcommands = { CommandLine.HelpCommand.class } // Ajoute une commande d'aide intégrée
)
public class TaskManager {
    // Sous-commande pour créer une liste
    @CommandLine.Command(name = "create", description = "Create a new todo list")
    public static class CreateList implements Callable<Integer> {
        @CommandLine.Option(names = {"-t", "--title"}, description = "Title of the list")
        private String title;

        @CommandLine.Option(names = {"-d", "--description"}, description = "Description of the task")
        private String description;

        @Override
        public Integer call() {
            Task task = new Task(title, description);
            fileWriter fw = new fileWriter();
            fw.writeFile(task, "src/main/java/ch/heigvd/dai/output.md");
            return 0;
        }
    }

    // Sous-commande pour mettre à jour une liste
    @CommandLine.Command(name = "update", description = "Update an existing todo list")
    public static class UpdateList implements Callable<Integer> {

        @CommandLine.Option(names = {"-t", "--title"}, description = "Title of the task to update")
        private String title;

        @CommandLine.Option(names = {"-d", "--description"}, description = "New description for the task")
        private String description;

        @Override
        public Integer call() {
            Task task = new Task(title, description);
            fileWriter fw = new fileWriter();
            fw.writeFile(task, "src/main/java/ch/heigvd/dai/" + task.name + ".md");
            return 0;
        }
    }
}