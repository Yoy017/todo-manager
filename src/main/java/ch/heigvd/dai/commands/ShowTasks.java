package ch.heigvd.dai.commands;

import ch.heigvd.dai.commands.Root;
import ch.heigvd.dai.util.Status;
import ch.heigvd.dai.util.SubTask;
import ch.heigvd.dai.util.Task;
import ch.heigvd.dai.util.fileReader;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.concurrent.Callable;




@CommandLine.Command(name = "show", description = "Display all created tasks")
public class ShowTasks implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @CommandLine.Parameters(index = "0", description = "The name of the file.")
    protected String filename;

    @CommandLine.Option(names = {"-s", "--status"}, description = "Select a task by status [PENDING, IN_PROGRESS, DONE]")
    private Status state;

    @Override
    public Integer call() {
        fileReader fi = new fileReader(filename);
        Vector<Task> tasks;
        try {
            tasks = fi.getAllTask();
        } catch (FileNotFoundException e) {
            System.out.println("You can only show tasks in an existing file.");
            return 1;
        }

        if(tasks.isEmpty()) {
            System.err.println("No task created yet.");
            return 0;
        }

        for(int i = 0; i < tasks.size(); ++i) {
            if(tasks.elementAt(i).state == state || state == null) {
                System.out.println(tasks.elementAt(i)); // Affiche la tâche
                for (SubTask subTask : tasks.elementAt(i).getSubTasks()) {
                    System.out.println(subTask); // Affiche des sous-tâches
                }
            }
        }
        return 0;
    }
}