package com.project.services;

import java.util.Date;
import java.util.LinkedHashMap;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.models.Usersj;
import com.project.repository.usersj.UsersjRepository;
import com.project.utils.PasswordValidator;
import com.project.utils.RequestParser;
import com.project.utils.TypeConverter;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UsersjService {

    @Autowired
    private UsersjRepository userRepository;

    public Usersj save(HttpServletRequest request) throws Exception{
        try {
           
            LinkedHashMap<String,Object> body=RequestParser.parseBody(request);
            String username=TypeConverter.convertToString(body.get("username"));
            if(PasswordValidator.isValidPassword(username)){
                return null;
            }
            String password=TypeConverter.convertToString(body.get("password"));
            if(PasswordValidator.isValidPassword(password)){
                return null;
            }
            Usersj user=new Usersj();
            user.setUsername(username);
            String hashedPassword=BCrypt.hashpw(password, BCrypt.gensalt(10));
            user.setPassword(hashedPassword);
            user.setCreated_at(new Date());
            user.setUpdated_at(new Date());
            Usersj userSaved=userRepository.save(user);
            return userSaved;
        } catch (Exception e) {
            System.err.println("There was an error to save: "+e);
            return null;
        }        
    }

    public Usersj test(){
        Usersj user=userRepository.findByUsername("fred");
        System.out.println(BCrypt.checkpw("123456", user.getPassword()));
        return user;
    }
}
