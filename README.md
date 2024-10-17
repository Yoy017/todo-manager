# todo-manager

> Authors : Yoann Changanaqui, Camille Theubet

Welcome ! This is todo-manager's repository, a simple command line application to create and manage simple todo lists. Using todo-manager, you can write and read simple tasks with todo-manager, with different status each.

Each user has it's personnal file so that everyone can have it's own todo list.

**This is only usable on a linux terminal.** If you use Windows or MacOS devices, you can still use paper.


Don't know where to start ? See section [Getting Started](#getting-started)

Curious about how it works ? See section [How it works](#how-it-works)

Don't know how to use ? See section [How to use](#how-it-use)

To see a list of available commands, see section [Commands](#commands)


## Getting Started

These instructions will give you a copy of the project up and running on
your local machine for development and testing purposes. See deployment
for notes on deploying the project on a live system.

### Prerequisites

You need to have a compatible version of java. If you don't know how to install java, see [this 
very well written document.](https://github.com/heig-vd-dai-course/heig-vd-dai-course/blob/e57a2205b48ce2a435624adbb713d83e30b408b0/04-java-intellij-idea-and-maven/COURSE_MATERIAL.md#install-sdkman)

### Installing

Clone the repository with

```
git clone git@github.com:Yoy017/todo-manager.git
```

Go into the cloned repository

```
cd todo-manager
```

Build the .jar file using the maven wrapper.
```shell
./mvnw dependency:go-offline clean compile package
```

You can now run the application using the created .jar file

```shell
java -jar target/todo-manager-1.0-SNAPSHOT.jar --version
```

Output should be
```shell
1.0.0
```

From now on, each time you see the words "todo-manager", either in this file, or in the the help
provided by the application, replace this with :

```shell
java -jar target/todo-manager-1.0-SNAPSHOT.jar
```

## How it works

todo-manager interacts with ".tdm" files. It will look for .tdm files in a "todoManagerFiles" folder (if it doesn't
exist, it will be created by the newList command, or you can create it manually).

The .tdm files are interpreted by todo-manager as "lists" of tasks. The lists follow a certain format (see formatDescription.md).


### Format description

A task starts with character '[', and ends with character ']'. 
It has a name and a description (additionnal text). A name should never be empty. The app will add a default name if no name is provided.
A description starts with character '{' and ends with character '}'.
After the name and the description is the status. Status can either be "En attente", "En cours", or "Terminé".



Down here is a example of a tdm file
```text
[<untilted>{}] [Status: En cours]
[Vaisselle{Faire la vaisselle}] [Status: En cours]
[Faire mes devoirs{}] [Status: En cours]
[<untilted>{Pas d'idée de titre, mais ne pas rien faire}] [Status: En attente]
[name{}] [Status: En cours]
[name{}] [Status: Terminé]
```

Tasks will be considered sorted in the order they come in the file.


## How to use

This section is for users that simply want to use the application without knowing how it works.

todo-manager allow user to create tasks in .tdm files.
For almost every command (except command "showLists"), you will need to write something like :

```shell
todo-manager [COMMAND] [OPTIONS] <listname>
````
with `[COMMAND]` being the command you want to use , and `<listname>` being the file you want to write in.
For the list of commands available, see the following section Commands.


A .tdm file can be created manually with the "touch" command, or can be created with command

```shell
todo-manager newList <listname>
```

The file, listname


## Commands

The following section explicits how to use every command todo-manager offers. During you use of todo-manager, if you have any doubt,
run the command + optional argument -h, to get indications on how to use the command. Like :

```shell
todo-manager [COMMAND] -h 
```

#### newList
Used to create a new list. If folder "todoManagerFiles" does not exist yet, it will be created as well

```shell
todo-manager newList <listname>
```

Will create a file "listname.tdm" in folder "todoManagerFiles"

#### showLists

This command will show the locally created lists. This command has to be run without any argument.

Use with : 
```shell
todo-manager showLists
```

The output will show you every list that you have previously created.

#### create
Create a task in a created list.

```shell
todo-manager create <listname>
```
Required arguments :
- listname : the name of the list (without the .tdm extension)

Options arguments :
- -t : title of the task
- -d : description of the task
- -s : status of the task. Either "PENDING", "IN_PROGRESS", or "DONE"
- -h : see the help message of the command


#### show

The command lists every task in a given list

```shell
todo-manager show <listname>
```

Required arguments :
- listname : the name of the list to see the tasks from (without the .tdm extension)

Optional arguments :
- -s : write one status ("PENDING", "IN_PROGRESS", or "DONE") to see only the tasks with this status.
- -h : see the help message of the command
- -V : see the version

#### delete

Delete a task from a list using the id of the task. Id's are not permanent to tasks. Make sure to always run a
"show" command of the file the see which task has what id at the moment.

```shell
todo-manager delete <listname> -id idOfTaskToDelete
```

Required arguments :
- listname : the name of the list to see the tasks from (without the .tdm extension)
- -id : id of the task to delete.

Optional arguments :
- -h : see the help message
- -V : version

#### update

Update an already existing task in a given file.

```shell
todo-manager update <listname> -id idOfTaskToUpdate
```

Required arguments :
- listname : the name of the list to see the tasks from (without the .tdm extension)
- -id : id of the task to update.

Optional arguments :
- -t : new title you want the task to have
- -d : new description you want the task to have
- -s : new status you want the task to have 


#### subtask

Add a subtask to an existing task 

```shell
todo-manager subtask <listname> -id parentTaskId -t  subTaskName
```

Required arguments :
- -listname : the name of the list to see the tasks from (without the .tdm extension)
- -id : id of the task to update.
- -t : new title you want the task to have

Optional arguments :
- None

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code
of conduct, and the process for submitting pull requests to us.

## Authors
- Yoann Changanaqui yoann.changanaqui@heig-vd.ch
- Camille Theubet camille.theubet@heig-vd.ch


## License

This project is licensed under the [CC0 1.0 Universal](LICENSE.md)
Creative Commons License - see the [LICENSE.md](LICENSE.md) file for
details

