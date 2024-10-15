package ch.heigvd.dai.util;

public class Task {
    private static int tId = 0;
    public int id;

    public String name, description;

    public Task(){
        this.name = ("<untilted_" + id + ">");
        id = tId;
        ++tId;
    }

    public Task(String name, String desc){
        this.name = name;
        this.description = desc;
        id = tId;
        ++tId;
    }

    public Task(String name){
        this.name = name;
        id = tId;
        ++tId;
    }

    @Override
    public String toString(){
        return this.id + "] " + this.name + "\t| " + this.description;
    }
}
