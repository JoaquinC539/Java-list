package com.project.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;






public class Querier {
    public static Object query(String sql,LinkedList<Object> values){
        try (Connection conn=com.project.config.Conecction.connection()){
            PreparedStatement ps=conn.prepareStatement(sql);
            for(int i=0;i<values.size();i++){
                ps.setObject(i+1, values.get(i));
            }
            
            ResultSet rs=ps.executeQuery();
            LinkedList<Object> results=new LinkedList<>();        
            
            while (rs.next()) {
                HashMap<String,Object> row=new HashMap<>();            
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {                   
                    row.put(rs.getMetaData().getColumnName(i), rs.getObject(i));                    
                }
                results.add(row);
            }
            ps.close();
            conn.close();
            rs.close();
            return results;            
        } catch (Exception e) {
            System.err.println("Error at querying: "+e);
            return null;
        } 
    }
}
