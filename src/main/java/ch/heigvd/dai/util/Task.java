package ch.heigvd.dai.util;

import java.util.Vector;

public class Task {
    private static int tId = 0;
    public int id;
    public String name, description;
    public Vector<SubTask> sub_tasks; // Liste des sous-tâches

    public Task(){
        this.name = ("<untilted_" + id + ">");
        id = tId;
        ++tId;
        this.sub_tasks = new Vector<>(); // Initialisation de la liste des sous-tâches
    }

    public Task(String name, String desc){
        this.name = name;
        this.description = desc;
        id = tId;
        ++tId;
        this.sub_tasks = new Vector<>(); // Initialisation de la liste des sous-tâches
    }

    public Task(String name){
        this.name = name;
        id = tId;
        ++tId;
        this.sub_tasks = new Vector<>(); // Initialisation de la liste des sous-tâches
    }

    public void addSubTask(SubTask subTask){
        this.sub_tasks.add(subTask); // Ajout de la sous-tâche
    }

    public Vector<SubTask> getSubTasks(){
        return sub_tasks;
    }

    @Override
    public String toString(){
        return this.id + "] " + this.name + "\t| " + this.description;
    }
}
