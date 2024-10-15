package ch.heigvd.dai.util;

public class Task {
    private static int id;

    public String name, description;

    public Task(){
        this.name = ("<untilted_" + id + ">");
        ++id;
    }

    public Task(String name, String desc){
        this.name = name;
        this.description = desc;
    }
}
