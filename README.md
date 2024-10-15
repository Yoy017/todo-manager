# todo-manager

Write and read simple tasks with todo-manager.

Find the description of the file format in file "formatDescription.md"

## How to use ?
Compile and package the application with the given commands in the .idea/runConfigurations/Package_application_as_JAR_file.xml

Then execute the .jar file with for example:
```shell
$ java -jar target/todo-manager-1.0-SNAPSHOT.jar list create -t "Title" -d "01-01-2025"
$ java -jar target/todo-manager-1.0-SNAPSHOT.jar list update -t "newTitle" -d "01-02-2025"

more with the command $ java -jar target/todo-manager-1.0-SNAPSHOT.jar list help
```

## Still to be done

- define which encoding to use