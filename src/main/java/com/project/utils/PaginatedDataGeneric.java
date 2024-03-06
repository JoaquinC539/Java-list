package com.project.utils;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class PaginatedDataGeneric {
    private List<HashMap<String,Object>> data;
    private Long count;
    public PaginatedDataGeneric(List<HashMap<String,Object>> data,Long count){
        this.data=data;
        this.count=count;
    }
    
    public List<HashMap<String,Object>> getData() {
        return data;
    }

    public Long getCount() {
        return count;
    }
}
