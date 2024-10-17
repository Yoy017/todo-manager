package ch.heigvd.dai.commands;

import ch.heigvd.dai.util.fileWriter;
import picocli.CommandLine;

import java.util.concurrent.Callable;

// Sous-commande pour créer une nouvelle liste
@CommandLine.Command(name = "newList", description = "Create a new list")
public class NewList implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @Override
    public Integer call() {
        fileWriter fw = new fileWriter(parent.filename);
        fw.createFileAndDirectory(parent.filename);
        return 0;
    }
}