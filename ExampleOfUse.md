# Real life use case example
````ad-info
The `todo-manager` specified at the entry of each commands refers
to the executing command `java -jar target/todo-manager-1.0-SNAPSHOT.jar`
to run the .jar file and therefore launch the application.
````

Welcome to Todo-Manager©. It's your first time here isn't it ?

Now that you have installed correctly the application and package it in an archive `.jar`, you will be able to enjoy the true power of todo-lists.

## First step
You will need to create your first **list**. As you are new to the application, you don't have a **list** to store your tasks.
As a todo-list is useless without tasks create your first awesome **list** with running the command :
```shell
$ todo-manager newList FIRST
```

A folder todoManagerList/ will be created at your current location containing the file `FIRST.tdm`.

You can see all your lists by running the command :
```shell
$ todo-manager showLists
```

## Divide to conquer
You now have a list so let's add your first task.
```shell
$ todo-manager create -t "Tuto" -d "The tutorial enjoyer" -s DONE FIRST
```

A task `TUTO` is added to the list `FIRST`.

You can check that with the command (Show all tasks in the list FIRST) :
```shell
$ todo-manager show FIRST
```

You can create all the tasks you want in the list. Don't be shy !

## You made a mistake ?
Don't worry. If your task contains mistake, you can update or delete it.

Update the task with id 0 in the list FIRST :
```shell
$ todo-manager update -id 0 -t "NewTuto" -d "The new tutorial description" -s IN_PROGRESS FIRST
```

Delete the task with id 0 in the list FIRST :
```shell
$ todo-manager delete -id 0 FIRST
```

As you may don't know the id of the task you want to update or delete. You have to show all tasks in the list and it'll
tell you the id of each. To show the the task, [remember this section](##Divide to conquer)

## Divide divide divide
You can create subTask :
```shell
$ todo-manager subtask -id 0 -t "Extension of the task 1" FIRST
```

Then, show all task to see the result.

## Are you still with me ?
Well, this conclude this tutorial. You are now a master at creating your own todo list. You can review this document as
it is quite short, to recall how to manage your lists and tasks.

Bye bye.