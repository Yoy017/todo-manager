package ch.heigvd.dai.commands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "todo-manager",
        description = "A simple task manager",
        version = "1.0.0",
        subcommands = {
                Create.class,
                Delete.class,
                ShowTasks.class,
                Update.class,
                Subtask.class,
                NewList.class,
                ShowLists.class
        },
        scope = CommandLine.ScopeType.INHERIT,
        mixinStandardHelpOptions = true)
public class Root {

}
