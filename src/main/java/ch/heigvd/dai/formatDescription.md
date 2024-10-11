# Format description

This file contains the description of the format for the todo-manager mini project.

### General idea

The files are written in file (with maybe some extension to define later).

A task starts with character '[', and ends with character ']'. It has a name (pure text in allowed characters) and a description (additionnal text). A description starts with character '{' and ends with character '}'. A task name is **mandatory** but the description is optionnal. If no description is written, the opening and closing characters shall be written anyway. The following is a valid todo-manager file.

```text
[taskName{task description}]
[Go to groceries store{Buy bread, Yogurt, brussels sprout}]
[Clean the bathroom{}][ThisIsAlsoOkay{Just so we don't have to deal with Windows/Mac compatibility}]
```
_You can find the example as a file "formatExample"_

Tasks will be considered sorted in the order they come in the file.

### Forbidden characters

For functional reasons, the following characters can not be written by user when writing tasks names or  
description.

```text
'[', ']', '{', '}'  
```