package com.project;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;


@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication app=new SpringApplication(ProjectApplication.class);
		File envFile=new File("./.env");
		if(!envFile.exists() && !envFile.isFile()){
			app.run(args);
			return;
		}
		
		Dotenv dotenv=Dotenv.configure().load();
		StandardEnvironment environmentstd=new StandardEnvironment();
		Map<String, Object> map=new HashMap<String,Object>();
		for(DotenvEntry entry:dotenv.entries()){
			map.put(entry.getKey(), entry.getValue());
		}		
		environmentstd.getPropertySources().addFirst(new MapPropertySource("dotenv", map));
		app.setEnvironment(environmentstd);
		app.run(args);
		
		return;
	}
	

}
