package ch.heigvd.dai.commands;

import ch.heigvd.dai.commands.Root;
import ch.heigvd.dai.util.Status;
import ch.heigvd.dai.util.Task;
import ch.heigvd.dai.util.fileReader;
import ch.heigvd.dai.util.fileWriter;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "update", description = "Update a task")
public class Update implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @CommandLine.Parameters(index = "0", description = "The name of the file.")
    protected String filename;

    @CommandLine.Option(names = {"-id"}, description = "ID of the task to update", required = true)
    private int id;

    @CommandLine.Option(names = {"-t", "--title"}, description = "New title of the task")
    private String title;

    @CommandLine.Option(names = {"-d", "--description"}, description = "New description of the task")
    private String description;

    @CommandLine.Option(names = {"-s", "--status"}, description = "Select a task by status [PENDING, IN_PROGRESS, DONE]")
    private Status state;

    @Override
    public Integer call() {
        fileReader fi = new fileReader(filename);
        Vector<Task> tasks;
        try{
            tasks = fi.getAllTask();
        } catch (FileNotFoundException e) {
            System.err.println("You can only update tasks in an existing file.");
            return 1;
        }

        if (tasks.isEmpty()) {
            System.err.println("No tasks to update.");
            return 0;
        }

        if (id <= 0 || id > tasks.size()) {
            System.err.println("ID is out of bounds!");
            return 0;
        }

        Task taskToUpdate = tasks.elementAt(id);

        if(title == null && description == null && state == null){
            System.out.println("No changes made");
            return 0;
        }

        if (title != null)
            taskToUpdate.name = title;

        if (description != null)
            taskToUpdate.description = description;

        if(state != null)
            taskToUpdate.state = state;

        fileWriter fo = new fileWriter(filename);

        fo.overwriteTasks(tasks);

        System.out.println("Task " + id + " successfully updated.");
        return 0;
    }
}