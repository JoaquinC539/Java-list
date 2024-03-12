// package com.project.services.auth;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.project.config.AuthUser;
// import com.project.models.Usersj;
// import com.project.repository.usersj.UsersjRepository;

// @Service
// public class UserDetailServiceImplementation implements UserDetailsService{

//     @Autowired
//     UsersjRepository usersjRepository;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Usersj userj=usersjRepository.findByUsername(username);
//        if(userj==null){
//         throw new UsernameNotFoundException("Username not found");
//        }
//        AuthUser user=new AuthUser(userj);
//        return user;
            
//     }    
// }
