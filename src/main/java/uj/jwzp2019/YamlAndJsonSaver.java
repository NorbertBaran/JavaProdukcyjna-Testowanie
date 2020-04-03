package uj.jwzp2019;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import uj.jwzp2019.model.Person;

public class YamlAndJsonSaver {

    public static void saveListToJson(List<Person> list, String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(System.getProperty("user.dir") + fileName), list);
    }

    public static void saveListToYaml(List<Person> list, String fileName) throws IOException {
        Yaml yaml = new Yaml();
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + fileName);
        yaml.dump(list, writer);
    }
}
