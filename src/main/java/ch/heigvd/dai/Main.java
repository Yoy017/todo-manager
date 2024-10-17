package ch.heigvd.dai;

import picocli.CommandLine;
//import ch.heigvd.dai.commands.TaskManager;
import ch.heigvd.dai.commands.Root;

public class Main {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int exitCode =
                new CommandLine(new Root())
                        .execute(args);

        long end = System.currentTimeMillis();
        System.out.println("Execution time: " + (end - start) + "ms");
        System.exit(exitCode);
    }

}