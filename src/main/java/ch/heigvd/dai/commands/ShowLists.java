package ch.heigvd.dai.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "showLists", description = "Show all existing lists")
public class ShowLists implements Callable<Integer> {

    @Override
    public Integer call() {
        long start = System.currentTimeMillis();

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
                String nameToDisplay = entry.getFileName().toString();
                String result = nameToDisplay.split(".tdm")[0];
                System.out.println(result);
                hasFiles = true;
            }
            if (!hasFiles) {
                System.out.println("No lists found.");
            }
        } catch (java.io.IOException e) {
            System.err.println("Error reading the directory: " + e.getMessage());
            return 1;
        }

        long end = System.currentTimeMillis();
        System.out.println("Execution time: " + (end - start) + "ms");
        return 0;
    }
}