package uj.jwzp2019.controller;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uj.jwzp2019.model.Person;
import uj.jwzp2019.service.PeopleService;
import uj.jwzp2019.service.SystemService;
import uj.jwzp2019.service.saver.JsonSaverService;
import uj.jwzp2019.service.saver.YamlSaverService;

import java.io.IOException;;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SaveControllerTest {

    @Mock
    private PeopleService peopleService;

    @Mock
    private SystemService systemService;

    private JsonSaverService jsonSaverService;

    private YamlSaverService yamlSaverService;

    private SaveController saveController;

    @Test
    void saveToFilesWithDefaults() throws Exception {
        //given
        Person jan=new Person();
        jan.setName("Jan Kowalski");
        BDDMockito.given(peopleService.getPersonById(1)).willReturn(jan);
        BDDMockito.given(systemService.getProperty("user.dir")).willReturn("src/test/resources/result");
        BDDMockito.given(systemService.getProperty("PREFIX")).willReturn("request");
        BDDMockito.given(systemService.currentTimeMillis()).willReturn(1L);
        jsonSaverService=BDDMockito.spy(new JsonSaverService(systemService));
        yamlSaverService=BDDMockito.spy(new YamlSaverService(systemService));
        saveController=BDDMockito.spy(new SaveController(peopleService, systemService, yamlSaverService, jsonSaverService));
        List<String> correctJson= Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result-correct/request1-correct.json").toURI()));
        //List<String> correctJson= Files.readAllLines(Paths.get("src/test/resources/result-correct/request1-correct.json"));
        List<String> correctYaml= Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result-correct/request1-correct.yaml").toURI()));
        //List<String> correctYaml= Files.readAllLines(Paths.get("src/test/resources/result-correct/request1-correct.yaml"));
        //when
        saveController.saveToFiles(1);
        //then
        List<String> resultJson=Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result/request1.json").toURI()));
        //List<String> resultJson=Files.readAllLines(Paths.get("src/test/resources/result/request1.json"));
        List<String> resultYaml=Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result/request1.yaml").toURI()));
        //List<String> resultYaml=Files.readAllLines(Paths.get("src/test/resources/result/request1.yaml"));

        Assert.assertEquals(resultJson, correctJson);
        Assert.assertEquals(resultYaml, correctYaml);

        Files.delete(Paths.get("src/test/resources/result/request1.json"));
        Files.delete(Paths.get("src/test/resources/result/request1.yaml"));
    }

    @Test
    void saveToFilesWithChangedPrefix() throws Exception{
        //given
        Person jan=new Person();
        jan.setName("Jan Kowalski");
        BDDMockito.given(peopleService.getPersonById(1)).willReturn(jan);
        BDDMockito.given(systemService.getProperty("user.dir")).willReturn("src/test/resources/result");
        BDDMockito.given(systemService.currentTimeMillis()).willReturn(1L);
        jsonSaverService=BDDMockito.spy(new JsonSaverService(systemService));
        yamlSaverService=BDDMockito.spy(new YamlSaverService(systemService));
        saveController=BDDMockito.spy(new SaveController(peopleService, systemService, yamlSaverService, jsonSaverService));
        List<String> correctJson= Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result-correct/request1-correct.json").toURI()));
        //List<String> correctJson= Files.readAllLines(Paths.get("src/test/resources/result-correct/request1-correct.json"));
        List<String> correctYaml= Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result-correct/request1-correct.yaml").toURI()));
        //List<String> correctYaml= Files.readAllLines(Paths.get("src/test/resources/result-correct/request1-correct.yaml"));
        //when
        saveController.changePrefix("changedRequest");
        saveController.saveToFiles(1);
        //then
        List<String> changedResultJson=Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result/request1.json").toURI()));
        //List<String> changedResultJson=Files.readAllLines(Paths.get("src/test/resources/result/changedRequest1.json"));
        List<String> changedResultYaml=Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result/request1.yaml").toURI()));
        //List<String> changedResultYaml=Files.readAllLines(Paths.get("src/test/resources/result/changedRequest1.yaml"));

        Assert.assertEquals(changedResultJson, correctJson);
        Assert.assertEquals(changedResultYaml, correctYaml);

        Files.delete(Paths.get("src/test/resources/result/changedRequest1.json"));
        Files.delete(Paths.get("src/test/resources/result/changedRequest1.yaml"));
    }


}
