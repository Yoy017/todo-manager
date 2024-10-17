package ch.heigvd.dai.commands;

import ch.heigvd.dai.util.fileWriter;
import picocli.CommandLine;

import java.util.concurrent.Callable;

// Sous-commande pour créer une nouvelle liste
@CommandLine.Command(name = "newList", description = "Create a new list")
public class NewList implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @CommandLine.Parameters(index = "0", description = "The name of the file.")
    protected String filename;

    @Override
    public Integer call() {
        fileWriter fw = new fileWriter(filename);
        fw.createFileAndDirectory(filename);
        return 0;
    }
}