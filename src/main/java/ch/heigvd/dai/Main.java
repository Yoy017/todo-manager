package ch.heigvd.dai;

import picocli.CommandLine;
//import ch.heigvd.dai.commands.TaskManager;
import ch.heigvd.dai.commands.Root;

public class Main {

    public static void main(String[] args) {
        // Crée la commande principale 'list' et ses sous-commandes
        /*
        CommandLine cmd = new CommandLine(new TaskManager())
                .addSubcommand("create", new TaskManager.CreateTask())
                .addSubcommand("show", new TaskManager.ShowTasks())
                .addSubcommand("delete", new TaskManager.deleteTask())
                .addSubcommand("update", new TaskManager.updateTask())
                .addSubcommand("subtask", new TaskManager.AddSubTask())
                .addSubcommand("newList", new TaskManager.NewList());

         */

        //cmd.execute(args); // Exécute la commande avec les arguments passés
        int exitCode =
                new CommandLine(new Root())
                        .execute(args);
        Long end = System.nanoTime();


        System.exit(exitCode);
    }

}