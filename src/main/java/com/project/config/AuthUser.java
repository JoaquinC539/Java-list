// package com.project.config;

// import java.util.Collection;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import com.project.models.Usersj;

// public class AuthUser extends Usersj implements UserDetails {

//     private final Usersj user;

//     public AuthUser(Usersj user){
//         this.user=user;
//     }
//     public Usersj getUser() {
//         return user;
//     }

//     @Override
//     public String getPassword() {
//         return user.getPassword();
//     }

//     @Override
//     public String getUsername() {
//         return user.getUsername();
//     }

//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         return null;
//     }

//     @Override
//     public boolean isAccountNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         return true;
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isEnabled() {
//         return true;
//     }
    
// }
