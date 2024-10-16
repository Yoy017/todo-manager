package ch.heigvd.dai.util;

public class SubTask {
    public String name;
    public int parentId;

    public SubTask(String name, int pid){
        this.name = name;
        this.parentId = pid;
    }

    @Override
    public String toString(){
        return "\t- " + name;
    }
}