package uj.jwzp2019.service.saver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import uj.jwzp2019.model.Person;
import uj.jwzp2019.service.SystemService;

@Service
public class YamlSaverService {

    private SystemService systemService;

    @Autowired
    public YamlSaverService(SystemService systemService){
        this.systemService=systemService;
    }

    public void saveListToYaml(List<Person> list, String fileName) throws IOException {
        Yaml yaml = new Yaml();
        FileWriter writer = new FileWriter(systemService.getProperty("user.dir") + fileName);
        yaml.dump(list, writer);
    }
}
