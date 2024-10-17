package ch.heigvd.dai.commands;

import ch.heigvd.dai.util.fileWriter;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

// Sous-commande pour créer une nouvelle liste
@CommandLine.Command(name = "newList", description = "Create a new list")
public class NewList implements Callable<Integer> {

    @CommandLine.ParentCommand
    protected Root parent;

    @CommandLine.Parameters(index = "0", description = "The name of the new list.")
    protected String filename;

    @Override
    public Integer call() {
        // Vérifier si le répertoire 'todoManagerList' existe, sinon le créer
        if (!Files.exists(Paths.get("todoManagerList"))) {
            try {
                Files.createDirectories(Paths.get("todoManagerList"));
                System.out.println("Directory 'todoManagerList' created.");
            } catch (IOException e) {
                System.err.println("Error creating directory: " + e.getMessage());
                return 1;
            }
        }

        // Vérifier si un fichier avec le même nom existe déjà
        Path filePath = Paths.get("todoManagerList").resolve(filename + ".tdm");
        if (Files.exists(filePath)) {
            System.err.println("A list with the name '" + filename + "' already exists.");
            return 1;
        }

        // Créer le fichier de la nouvelle liste
        fileWriter fw = new fileWriter(filename);
        fw.createFileAndDirectory(filename);
        return 0;
    }
}