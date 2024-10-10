package ch.heigvd.dai;

import ch.heigvd.dai.util.fileWriter;

public class Main {
    public static void main(String[] args) {
        fileWriter fo = new fileWriter();

        fo.writeFile("Contenue à ajouter au fichier\n d'output !", "src/main/java/ch/heigvd/dai/output.txt");
    }
}