package uj.jwzp2019.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uj.jwzp2019.YamlAndJsonSaver;
import uj.jwzp2019.model.Person;
import uj.jwzp2019.service.PeopleService;

import java.io.IOException;
import java.util.List;

@RestController
public class SaveController {

    private static String PREFIX;

    public SaveController() {
        init();
    }

    private static void init() {
        if (PREFIX == null) {
            PREFIX = "/" + System.getProperty("PREFIX");
        }
    }

    @RequestMapping("/save")
    public String saveToFiles(@RequestParam(value="id", defaultValue="1") int id) throws IOException {

        long time = System.currentTimeMillis();
        String fileName = PREFIX + time;

        PeopleService peopleService = new PeopleService();
        Person person = peopleService.getPersonById(id);
        person.setEye_color("pink");

        YamlAndJsonSaver.saveListToJson(List.of(person), fileName + ".json");
        YamlAndJsonSaver.saveListToYaml(List.of(person), fileName + ".yaml");
        return "Ok";
    }

    @RequestMapping("/prefix")
    public void changePrefix(@RequestParam String prefix) {
        PREFIX = "/" + prefix;
    }

}
