package com.project.utils;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    
    public Utils(){}

    public static LinkedHashMap<String, Object> JSONParse(String jsonString) throws JsonMappingException, JsonProcessingException{
        if (jsonString.toCharArray()[0] != '{') {
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("error", "Incompatible header and body request");
            return map;
        }
        ObjectMapper objectMapper=new ObjectMapper();
        LinkedHashMap<String,Object> map=objectMapper.readValue(jsonString, LinkedHashMap.class);
        return map;
    }

}
