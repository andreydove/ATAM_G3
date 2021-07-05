package helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    private final static Logger LOG = LogManager.getLogger("Обработчик файлов");

    public static List<String> readFile(String path){
        List<String> result = new ArrayList<>();
        try {
            result = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            LOG.error(e);
        }
        return result;
    }

    public static void writeToFile(String path, List<String> data){
        try {
            Files.write(Paths.get(path), data);
        } catch (IOException e) {
            LOG.error(e);
        }


    }

}
