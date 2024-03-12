// package com.project.config;

// import org.mindrot.jbcrypt.BCrypt;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.ProviderManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import com.project.services.auth.UserDetailServiceImplementation;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig  {

//     @Autowired
//     private UserDetailServiceImplementation userDetailServiceImplementation;

//     @Bean
//     public AuthenticationManager authenticationManager(){
//         var authProvider=new DaoAuthenticationProvider();
//         authProvider.setUserDetailsService(userDetailServiceImplementation);
//         authProvider.setPasswordEncoder(passwordEncoder());
//         return new ProviderManager(authProvider);
//     }
    
//     // @SuppressWarnings("deprecation")
//     @SuppressWarnings("deprecation")
//     @Bean 
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//         System.out.println("FILTER CHAIN");
//         http            
//             .authorizeRequests((requests)->requests                
//                 .requestMatchers("/css/*","/js/*","/favicon.ico","/js/utils/*","/login","/vendedor","/","/register","/register/*").permitAll()
//                 .anyRequest().authenticated()
//             )
//             .formLogin((form)->form
//                 .loginPage("/login")
//                 .defaultSuccessUrl("/dashboard")
//                 .permitAll()
                
//             )
//             .logout((logout)->logout
//             .permitAll()
//             .logoutSuccessUrl("/")
//             );
            
//         return http.build();
//     }
    

//     @Bean
//     public PasswordEncoder passwordEncoder(){
//         return new PasswordEncoder() {
//             @Override
//             public String encode(CharSequence rawPassword){
//                 return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(10));
//             }
//             @Override
//             public boolean matches(CharSequence rawPassword,String encodedPassword){                
//                 return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
//             }
//         };
//     }
// }
