package uj.jwzp2019.controller;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import uj.jwzp2019.model.Person;
import uj.jwzp2019.service.PeopleService;
import uj.jwzp2019.service.SystemService;
import uj.jwzp2019.service.saver.JsonSaverService;
import uj.jwzp2019.service.saver.YamlSaverService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
    public void saveToFilesWithDefaults() throws Exception {
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

        //URI uri=SaveControllerTest.class.getResource("src/test/resources/result-correct/request1-correct.json").toURI();
        //List<String> resultCorrect= Files.readAllLines(Paths.get("src/test/resources/result-correct/request1-correct.json"));
        List<String> resultCorrect= Files.readAllLines(Paths.get("build/resources/test/result-correct/request1-correct.json"));

        //when
        saveController.saveToFiles(1);
        //List<String> result=Files.readAllLines(Paths.get("src/test/resources/result/request1.json"));
        List<String> result=Files.readAllLines(Paths.get("build/resources/test/result/request1.json"));
        //then
        //Assertions.assertThat(jan2.toString()).isEqualTo("request123456");
        //Assertions.assertThat(result).is(resultCorrect);
        Assert.assertEquals(result, resultCorrect);
    }

    @Test
    public void saveToFilesWithChangedPrefix(){

    }

    @Test
    public void saveToFilesWithChangedId(){

    }

    @Test
    public void saveToFilesWithEmptyResult(){
        //Assertions.assertThat("nieok").isEqualTo("ok");
    }

}
