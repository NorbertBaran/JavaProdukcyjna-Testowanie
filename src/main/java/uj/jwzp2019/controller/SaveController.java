package uj.jwzp2019.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uj.jwzp2019.model.Person;
import uj.jwzp2019.service.PeopleService;
import uj.jwzp2019.service.SystemService;
import uj.jwzp2019.service.saver.JsonSaverService;
import uj.jwzp2019.service.saver.YamlSaverService;

import java.io.IOException;
import java.util.List;

@RestController
public class SaveController {

    private PeopleService peopleService;
    private SystemService systemService;
    private YamlSaverService yamlSaverService;
    private JsonSaverService jsonSaverService;

    private String PREFIX;

    @Autowired
    public SaveController(PeopleService peopleService, SystemService systemService, YamlSaverService yamlSaverService, JsonSaverService jsonSaverService) {
        this.peopleService=peopleService;
        this.systemService=systemService;
        this.yamlSaverService=yamlSaverService;
        this.jsonSaverService=jsonSaverService;
    }

    private void init() {
        if (PREFIX == null) {
            PREFIX = "/" + systemService.getProperty("PREFIX");
        }
    }

    @RequestMapping("/save")
    public String saveToFiles(@RequestParam(value="id", defaultValue="1") int id) throws IOException {
        init();
        long time = systemService.currentTimeMillis();
        String fileName = PREFIX + time;

        Person person = peopleService.getPersonById(id);
        person.setEye_color("pink");

        jsonSaverService.saveListToJson(List.of(person), fileName + ".json");
        yamlSaverService.saveListToYaml(List.of(person), fileName + ".yaml");
        return "Ok";
    }

    @RequestMapping("/prefix")
    public void changePrefix(@RequestParam String prefix) {
        PREFIX = "/" + prefix;
    }

}
