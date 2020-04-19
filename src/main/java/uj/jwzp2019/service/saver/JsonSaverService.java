package uj.jwzp2019.service.saver;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uj.jwzp2019.model.Person;
import uj.jwzp2019.service.SystemService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JsonSaverService {

    private SystemService systemService;

    @Autowired
    public JsonSaverService(SystemService systemService){
        this.systemService=systemService;
    }

    public void saveListToJson(List<Person> list, String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(systemService.getProperty("user.dir") + fileName), list);
    }

}
