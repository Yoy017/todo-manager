package ch.heigvd.dai.commands;

import ch.heigvd.dai.commands.Root;
import ch.heigvd.dai.util.SubTask;
import ch.heigvd.dai.util.Task;
import ch.heigvd.dai.util.fileReader;
import ch.heigvd.dai.util.fileWriter;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "subtask", description = "Add a subtask to a specific task")
public class Subtask implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @CommandLine.Option(names = {"-id"}, description = "ID of the parent task", required = true)
    private int parentId;

    @CommandLine.Option(names = {"-t", "--title"}, description = "Title of the subtask", required = true)
    private String title;

    @Override
    public Integer call() {
        fileReader fr = new fileReader(parent.filename);
        Vector<Task> tasks;
        try {
            tasks = fr.getAllTask();
        } catch (FileNotFoundException e) {
            return 1;
        }

        if (tasks.isEmpty()) {
            System.err.println("No tasks created yet.");
            return 0;
        }

        if (parentId < 0 || parentId >= tasks.size()) {
            System.err.println("Invalid parent task ID.");
            return 1;
        }

        Task parentTask = tasks.get(parentId);
        parentTask.addSubTask(new SubTask(title, parentTask.id));

        // Mettre à jour le fichier .tdm
        fileWriter fw = new fileWriter(parent.filename);
        fw.overwriteTasks(tasks);

        System.out.println("Subtask successfully added to task: " + parentTask.name);
        return 0;
    }
}