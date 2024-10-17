package ch.heigvd.dai.commands;

import ch.heigvd.dai.util.Status;
import ch.heigvd.dai.util.Task;
import ch.heigvd.dai.util.fileWriter;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "create", description = "Create a task in a file")
public class Create implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @CommandLine.Parameters(index = "0", description = "The name of the file.")
    protected String filename;

    @CommandLine.Option(names = {"-t", "--title"}, description = "Title of the task")
    private String title;

    @CommandLine.Option(names = {"-d", "--description"}, description = "Description of the task")
    private String description;

    @CommandLine.Option(names = {"-s", "--status"}, description = "Select a task by status [PENDING, IN_PROGRESS, DONE]")
    private Status state;

    @Override
    public Integer call() {
        Task task;
        if(title == null)
            task = new Task();
        else if(description == null && state == null)
            task = new Task(title);
        else if(description != null && state == null)
            task = new Task(title, description);
        else
            task = new Task(title, description, state);

            fileWriter fw = new fileWriter(filename);
        try{
            fw.writeFile(task, true);
            System.out.println("Task successfully created.");
        } catch (FileNotFoundException e) {
            System.out.println("You can only create tasks in an existing file.");
        }
        return 0;
    }
}
