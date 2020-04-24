package uj.jwzp2019.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uj.jwzp2019.model.Person;
import uj.jwzp2019.service.PeopleService;
import uj.jwzp2019.service.SystemService;
import uj.jwzp2019.service.saver.JsonSaverService;
import uj.jwzp2019.service.saver.YamlSaverService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class SaveControllerTest {

    @Mock
    private PeopleService peopleService;

    @Mock
    private SystemService systemService;

    private JsonSaverService jsonSaverService;

    private YamlSaverService yamlSaverService;

    private SaveController saveController;

    private MockMvc mockMvc;

    @Test
    void saveToFilesWithDefaults() throws Exception {
        //given
        Person jan=new Person();
        jan.setName("Jan Kowalski");
        given(peopleService.getPersonById(1)).willReturn(jan);
        given(systemService.getProperty("user.dir")).willReturn(SaveControllerTest.class.getResource("result").getPath());
        given(systemService.getProperty("PREFIX")).willReturn("request");
        given(systemService.currentTimeMillis()).willReturn(1L);
        jsonSaverService=spy(new JsonSaverService(systemService));
        yamlSaverService= spy(new YamlSaverService(systemService));
        saveController= spy(new SaveController(peopleService, systemService, yamlSaverService, jsonSaverService));

        InputStream input = SaveController.class.getResourceAsStream("result-correct/request1-correct.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        List<String> lines = br.lines().collect(Collectors.toList());

        List<String> correctJson= Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result-correct/request1-correct.json").getPath()));
        List<String> correctYaml= Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result-correct/request1-correct.yaml").getPath()));
        //when
        mockMvc = MockMvcBuilders.standaloneSetup(saveController)
                .build();
        mockMvc.perform(
                get("/save/")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        //then
        List<String> resultJson=Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result/request1.json").getPath()));
        List<String> resultYaml=Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result/request1.yaml").getPath()));

        Assert.assertEquals(resultJson, correctJson);
        Assert.assertEquals(resultYaml, correctYaml);

        Files.delete(Paths.get(SaveControllerTest.class.getResource("result/request1.json").getPath()));
        Files.delete(Paths.get(SaveControllerTest.class.getResource("result/request1.yaml").getPath()));
    }

    @Test
    void saveToFilesWithChangedId() throws Exception {
        //given
        Person jan=new Person();
        jan.setName("Jan Kowalski");
        given(peopleService.getPersonById(2)).willReturn(jan);
        given(systemService.getProperty("user.dir")).willReturn(SaveControllerTest.class.getResource("result").getPath());
        given(systemService.getProperty("PREFIX")).willReturn("request");
        given(systemService.currentTimeMillis()).willReturn(1L);
        jsonSaverService= spy(new JsonSaverService(systemService));
        yamlSaverService= spy(new YamlSaverService(systemService));
        saveController= spy(new SaveController(peopleService, systemService, yamlSaverService, jsonSaverService));
        List<String> correctJson= Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result-correct/request1-correct.json").getPath()));
        List<String> correctYaml= Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result-correct/request1-correct.yaml").getPath()));
        //when
        mockMvc = MockMvcBuilders.standaloneSetup(saveController)
                .build();
        mockMvc.perform(
                get("/save?id=2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        //then
        List<String> resultJson=Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result/request1.json").getPath()));
        List<String> resultYaml=Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result/request1.yaml").getPath()));

        Assert.assertEquals(resultJson, correctJson);
        Assert.assertEquals(resultYaml, correctYaml);

        Files.delete(Paths.get(SaveControllerTest.class.getResource("result/request1.json").getPath()));
        Files.delete(Paths.get(SaveControllerTest.class.getResource("result/request1.yaml").getPath()));
    }

    @Test
    void saveToFilesWithChangedPrefix() throws Exception{
        //given
        Person jan=new Person();
        jan.setName("Jan Kowalski");
        given(peopleService.getPersonById(1)).willReturn(jan);
        given(systemService.getProperty("user.dir")).willReturn(SaveControllerTest.class.getResource("result").getPath());
        given(systemService.currentTimeMillis()).willReturn(1L);
        jsonSaverService= spy(new JsonSaverService(systemService));
        yamlSaverService= spy(new YamlSaverService(systemService));
        saveController= spy(new SaveController(peopleService, systemService, yamlSaverService, jsonSaverService));
        List<String> correctJson= Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result-correct/request1-correct.json").getPath()));
        List<String> correctYaml= Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result-correct/request1-correct.yaml").getPath()));
        //when
        mockMvc = MockMvcBuilders.standaloneSetup(saveController)
                .build();
        mockMvc.perform(
                get("/prefix?prefix=changedRequest")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        mockMvc.perform(
                get("/save/")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        //then
        List<String> changedResultJson=Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result/changedRequest1.json").getPath()));
        List<String> changedResultYaml=Files.readAllLines(Paths.get(SaveControllerTest.class.getResource("result/changedRequest1.yaml").getPath()));

        Assert.assertEquals(changedResultJson, correctJson);
        Assert.assertEquals(changedResultYaml, correctYaml);

        Files.delete(Paths.get(SaveControllerTest.class.getResource("result/changedRequest1.json").getPath()));
        Files.delete(Paths.get(SaveControllerTest.class.getResource("result/changedRequest1.yaml").getPath()));
    }


}
