package ch.heigvd.dai.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class fileReader {

    public void readFile(String filename) {
        try (
                Reader fi = new FileReader(filename, StandardCharsets.UTF_8);
        ) {
            BufferedReader br = new BufferedReader(fi);

            String str;
            while((str = br.readLine()) != null){
                System.out.println(str);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
