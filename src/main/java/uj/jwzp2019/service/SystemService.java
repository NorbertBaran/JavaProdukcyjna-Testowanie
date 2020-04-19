package uj.jwzp2019.service;

import org.springframework.stereotype.Service;

@Service
public class SystemService {
    public String getProperty(String key){
        return System.getProperty(key);
    }

    public long currentTimeMillis(){
        return System.currentTimeMillis();
    }
}
