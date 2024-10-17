package ch.heigvd.dai.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "showLists", description = "Show all lists")
public class ShowLists implements Callable<Integer> {
    // @CommandLine.ParentCommand protected Root parent;

    @Override
    public Integer call() {
        // Répertoire où sont stockées les listes
        String directoryPath = "todoManagerList/";
        java.nio.file.Path dir = java.nio.file.Paths.get(directoryPath);

        // Vérifier si le répertoire existe
        if (!java.nio.file.Files.exists(dir)) {
            System.out.println("No lists created yet.");
            return 0;
        }

        // Lister les fichiers du répertoire
        try (java.nio.file.DirectoryStream<java.nio.file.Path> stream = java.nio.file.Files.newDirectoryStream(dir, "*.tdm")) {
            boolean hasFiles = false;
            for (java.nio.file.Path entry : stream) {
                System.out.println(entry.getFileName().toString());
                hasFiles = true;
            }
            if (!hasFiles) {
                System.out.println("No lists found.");
            }
        } catch (java.io.IOException e) {
            System.err.println("Error reading the directory: " + e.getMessage());
            return 1;
        }
        return 0;
    }
}