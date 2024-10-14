package ch.heigvd.dai.commands;

import picocli.CommandLine;
import java.util.concurrent.Callable;
import ch.heigvd.dai.util.fileReader;
import ch.heigvd.dai.util.fileWriter;

// Commande principale pour gérer la todo-list
@CommandLine.Command(
        name = "list",
        description = "Todo list management commands",
        subcommands = { CommandLine.HelpCommand.class } // Ajoute une commande d'aide intégrée
)
public class List {

    // Sous-commande pour créer une liste
    @CommandLine.Command(name = "create", description = "Create a new todo list")
    public static class CreateList implements Callable<Integer> {

        @CommandLine.Option(names = {"-t", "--title"}, description = "Title of the todo list")
        private String title;

        @CommandLine.Option(names = {"-d", "--deadline"}, description = "Deadline for the todo list")
        private String deadline;

        @Override
        public Integer call() {
            fileWriter fw = new fileWriter();
            fw.writeFile("Title: " + (title != null ? title : "<Untitled>") + '\n' +  "Deadline: " + (deadline != null ? deadline : "<No deadline>"), (title != null ? title : "<Untitled>") + ".txt");
            // Logique pour créer une liste (affichage pour l'instant)
            System.out.println("Todo List Created");
            System.out.println("Title: " );
            System.out.println("Deadline: " + (deadline != null ? deadline : "<No deadline>"));
            return 0;
        }
    }

    // Sous-commande pour mettre à jour une liste
    @CommandLine.Command(name = "update", description = "Update an existing todo list")
    public static class UpdateList implements Callable<Integer> {

        @CommandLine.Option(names = {"-t", "--title"}, description = "Title of the todo list to update")
        private String title;

        @CommandLine.Option(names = {"-d", "--deadline"}, description = "New deadline for the todo list")
        private String deadline;

        @Override
        public Integer call() {
            // Logique pour mettre à jour la liste (affichage pour l'instant)
            System.out.println("Updating Todo List");
            System.out.println("Title: " + (title != null ? title : "<Untitled>"));
            System.out.println("New Deadline: " + (deadline != null ? deadline : "<No deadline>"));
            return 0;
        }
    }
}