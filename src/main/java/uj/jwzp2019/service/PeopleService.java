package uj.jwzp2019.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uj.jwzp2019.model.Person;
import uj.jwzp2019.model.Planet;

@Service
public class PeopleService {

    public Person getPersonById(int id) {
        RestTemplate restTemplate = new RestTemplate();
        Person person = restTemplate.getForObject("https://swapi.co/api/people/" + id, Person.class);
        if (person != null) {
            person.setPlanet(restTemplate.getForObject(person.getHomeworld(), Planet.class));
        }
        return person;
    }
}
