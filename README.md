# todo-manager

> Authors : Yoann Changanaqui, Camille Theubet

Welcome ! This is todo-manager's repository, a simple command line application to create and manage simple todo lists. Using todo-manager, you can write and read simple tasks with todo-manager, with different status each.

Each user has it's personnal file so that everyone can have it's own todo list.

**This is only usable on a linux terminal.** Windows and mac users are pleased to wait for further developpment.

## Getting Started

These instructions will give you a copy of the project up and running on
your local machine for development and testing purposes. See deployment
for notes on deploying the project on a live system.

### Prerequisites

You need to have a compatible version of java.

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

## How to use

This section is for users that simply want to use the application without knowing how it works.

todo-manager allow user to create tasks in .tdm files.
For almost every command (except command "showLists"), you will need to write something like :

```shell
todo-manager <command> <filename> [OPTIONS]
```
with `<command>` being the command you want to use , and `<filename>` being the file you want to write in.
For the list of commands available, see the following section Commands.


A .tdm file can be created manually with the "touch" command, or can be created with command

```shell
todo-manager newList <filename>
```

The file, filename


### Commands

The following section explicits how to use every command todo-manager offers. During you use of todo-manager, if you have any doubt,
run the command + optional argument -h, to get indications on how to use the command. Like :

```shell
todo-manager <command> -h 
```

#### newList
Used to create a new list. If folder "todoManagerFiles" does not exist yet, it will be created as well

```shell
todo-manager newList <filename>
```

Will create a file "filename.tdm" in folder "todoManagerFiles"

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
todo-manager create <filename>
```
Required arguments :
- filename : the name of the list (without the .tdm extension)

Options arguments :
- -t : title of the task
- -d : description of the task
- -s : status of the task. Either "PENDING", "IN_PROGRESS", or "DONE"
- -h : see the help message of the command


#### show

The command lists every task in a given list

```shell
todo-manager show <filename>
```

Required arguments :
- filename : the name of the list to see the tasks from (without the .tdm extension)

Optional arguments :
- -s : write one status ("PENDING", "IN_PROGRESS", or "DONE") to see only the tasks with this status.
- -h : see the help message of the command
- -V : see the version

#### delete

Delete a task from a list using the id of the task. Id's are not permanent to tasks. Make sure to always run a
"show" command of the file the see which task has what id at the moment.

```shell
todo-manager delete <filename> -id idOfTaskToDelete
```

Required arguments :
- filename : the name of the list to see the tasks from (without the .tdm extension)
- -id : id of the task to delete.

Optional arguments :
- -h : see the help message
- -V : version

#### update

Update an already existing task in a given file.

```shell
todo-manager update <filename> -id idOfTaskToUpdate
```

Required arguments :
- filename : the name of the list to see the tasks from (without the .tdm extension)
- -id : id of the task to update.

Optional arguments :
- -t : new title you want the task to have
- -d : new description you want the task to have
- -s : new status you want the task to have 


#### subtask

Required arguments :

Optional arguments :




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

