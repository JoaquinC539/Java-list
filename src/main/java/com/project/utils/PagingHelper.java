package com.project.utils;

import java.util.HashMap;
import java.util.LinkedList;

import jakarta.servlet.http.HttpServletRequest;

public class PagingHelper {
    private static final int DEFAULT_MAX_ROWS = 10;
    private static final int DEFAULT_OFFSET = 0;

    
    public static PaginatedDataGeneric fetchPaginatedDataGeneric(String baseSql,LinkedList<Object> values,HttpServletRequest request){
        HashMap<String, String> queryParams = RequestParser.parseQueryParams(request);
        int maxRows = queryParams.containsKey("max") ? Integer.parseInt(queryParams.get("max")) : DEFAULT_MAX_ROWS;
        int offset = queryParams.containsKey("offset") ? Integer.parseInt(queryParams.get("offset")) : DEFAULT_OFFSET;
        String paginatedSql = baseSql + " LIMIT ? OFFSET ?";
        LinkedList<Object> paginatedValues = new LinkedList<Object>(values);
        paginatedValues.add(maxRows);
        paginatedValues.add(offset);
        LinkedList<HashMap<String, Object>> data = Querier.query(paginatedSql, paginatedValues);
        LinkedList<HashMap<String, Object>> count = Querier.query("SELECT COUNT(*) FROM (" + baseSql + ")", values);
        Long countInt=(Long) count.getFirst().get("count");
        PaginatedDataGeneric result=new PaginatedDataGeneric(data, countInt);
        return result;
    }
    public static <T> PaginatedData<T> fetchPaginatedDataModel(String baseSql, LinkedList<Object> values,
            HttpServletRequest request, Class<T> class1) {
        HashMap<String, String> queryParams = RequestParser.parseQueryParams(request);
        int maxRows = queryParams.containsKey("max") ? Integer.parseInt(queryParams.get("max")) : DEFAULT_MAX_ROWS;
        int offset = queryParams.containsKey("offset") ? Integer.parseInt(queryParams.get("offset")) : DEFAULT_OFFSET;
        String paginatedSql = baseSql + " LIMIT ? OFFSET ?";
        LinkedList<Object> paginatedValues = new LinkedList<>(values);
        paginatedValues.add(maxRows);
        paginatedValues.add(offset);
        LinkedList<T> data=Querier.queryModel(paginatedSql, paginatedValues, class1);
        LinkedList<HashMap<String,Object>> count=Querier.query("SELECT COUNT(*) FROM (" + baseSql + ")", values);
        Long countInt=count.get(0).size()>0? (Long) count.get(0).get("count"): Long.valueOf(0);
        PaginatedData<T> result=new PaginatedData<>(data, countInt);


        return result;
    }

}
