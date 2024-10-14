package ch.heigvd.dai;

import picocli.CommandLine;
import ch.heigvd.dai.commands.List;

public class Main {

    public static void main(String[] args) {
        // Crée la commande principale 'list' et ses sous-commandes
        CommandLine cmd = new CommandLine(new List())
                .addSubcommand("create", new List.CreateList())   // Ajoute la sous-commande 'create'
                .addSubcommand("update", new List.UpdateList());  // Ajoute la sous-commande 'update'

        cmd.execute(args); // Exécute la commande avec les arguments passés
    }
}