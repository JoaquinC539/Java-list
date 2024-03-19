package com.project.services.auth;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.models.Usersj;
import com.project.repository.usersj.UsersjRepository;
import com.project.utils.JwtUtil;
import com.project.utils.TypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {
   @Autowired
   UsersjRepository usersjRepository;

   public AuthPayload auth(LinkedHashMap<String,Object> body)throws Exception{
      String username=TypeConverter.convertToString(body.get("username"));
      if(username==null){
         return null;
      }
      Usersj usersj=usersjRepository.findByUsername(username);
      if(usersj==null){
         return null;
      }
      String reqPassword=TypeConverter.convertToString(body.get("password"));
      String hashedPwd=usersj.getPassword();

      AuthPayload auth=new AuthPayload(BCrypt.checkpw(reqPassword, hashedPwd), usersj);

      return auth;
   }
   public Boolean checkTokens(HttpServletRequest request,HttpServletResponse response)throws Exception{
      try {
         Jws<Claims> refreshClaim=JwtUtil.descipherRefreshToken(request);
         Jws<Claims> authClaim=JwtUtil.descipherAuthToken(request);
         if((refreshClaim==null && authClaim==null) ){
            return false;
         }
         if(authClaim==null && refreshClaim!=null && refreshClaim.getPayload().containsKey("id")  ){
            Integer id=TypeConverter.convertToInteger(refreshClaim.getPayload().get("id"));
            @SuppressWarnings("null")
            Optional<Usersj> user=usersjRepository.findById(id);
            if(user.isPresent()){
                String refreshToken=JwtUtil.generateRefreshToken(user.get());
               if(refreshToken==null){
                  System.err.println("Error at renewing refresh token based on itself");
                  return false;
               }
               Integer refreshDuration=10*7*24*60*60;
               Cookie refreshCookie=new Cookie("refreshAuth", refreshToken);
               refreshCookie.setHttpOnly(true);
               refreshCookie.setMaxAge(refreshDuration);
               refreshCookie.setAttribute("SameSite", "Strict");
               String authToken=JwtUtil.generateAuthToken(user.get());
               if(authToken==null){
                  System.err.println("Error at generating auth token based from refresh token");
                  return false;
               }
               Integer authDuration=1*60*60;
                Cookie authCookie=new Cookie("Auth", authToken);
               authCookie.setHttpOnly(false);
               authCookie.setMaxAge(authDuration);
               authCookie.setAttribute("SameSite", "Strict");
               response.addCookie(authCookie);
               request.setAttribute("user", user.get());
               return true;

            }
         }else if(refreshClaim==null && authClaim!=null && authClaim.getPayload().containsKey("id")){
            Integer id=TypeConverter.convertToInteger(authClaim.getPayload().get("id"));
            @SuppressWarnings("null")
            Optional<Usersj> user=usersjRepository.findById(id);
            request.setAttribute("user", user.get());
            
            return true;
         }else if(authClaim!=null && refreshClaim!=null && authClaim.getPayload().containsKey("id") && refreshClaim.getPayload().containsKey("id")){
            Integer id=TypeConverter.convertToInteger(refreshClaim.getPayload().get("id"));
            @SuppressWarnings("null")
            Optional<Usersj> user=usersjRepository.findById(id);
            request.setAttribute("user", user.get());
            return true;
         }else{
            return false;
         }
         
         return false;
      } catch (Exception e) {
         System.out.println("Error at generating the intercept tokens: "+e);
         return null;
      }
      
      
   }
}
