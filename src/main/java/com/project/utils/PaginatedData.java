package com.project.utils;

import java.util.List;

import lombok.Data;

@Data
public class  PaginatedData<T> {
    private List<T> data;
    private Long count;

    public PaginatedData(List<T> data,Long count){
        this.data=data;
        this.count=count;
    }
    
    public List<T> getData() {
        return data;
    }

    public Long getCount() {
        return count;
    }
}
