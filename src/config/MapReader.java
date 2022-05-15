package config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapReader {

    public static List<String> read(String mapName) {
        List<String> map = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(mapName));
            String line = reader.readLine();
            while (line != null) {
                map.add(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public static void main(String[] args) {
        List<String> a = MapReader.read("maps/plain.txt");
        for(String s: a) {
            System.out.println(s);
        }
    }
}
