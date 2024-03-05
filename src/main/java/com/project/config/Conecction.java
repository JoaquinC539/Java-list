package com.project.config;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class Conecction {

    public static Connection connection() throws SQLException {

        String db_url ;
        String db_user ;
        String db_pass ;
        File envFile=new File("./.env");
        if(!envFile.exists() && !envFile.isFile()){
			 db_url = System.getenv("DATABASE_URL");
            db_user = System.getenv("DATABASE_USERNAME");
            db_pass = System.getenv("DATABASE_PASSWORD");
			
		}else{
            Dotenv dotenv=Dotenv.load();
            db_url=dotenv.get("DATABASE_URL");
            db_user=dotenv.get("DATABASE_USERNAME");
            db_pass=dotenv.get("DATABASE_PASSWORD");

        }
       try {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(db_url, db_user, db_pass);
       } catch (Exception e) {
            System.err.println("Error at connectiong to db: "+e);
            return null;
       }
    }
    public static void closeConnection(Connection conn) throws SQLException{
        conn.close();
    }
    public static void closeConnection(ResultSet rs) throws SQLException{
        rs.close();
    }
    public static void closeConnection(PreparedStatement ps) throws SQLException{
        ps.close();
    }


}
