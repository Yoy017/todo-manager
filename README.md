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
with `<command>` being the command you want to use, and `<filename>` being the file you want to write in.
For the list of commands available, see section Commands.


A .tdm file can be created manually with the "touch" command, or can be created with command

```shell
todo-manager newList <filename>
```

The file, filename


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

