package com.project.services.auth;

import com.project.models.Usersj;

import lombok.Data;

@Data
public class AuthPayload {
    Boolean auth;
    Usersj user;

    public AuthPayload(Boolean auth,Usersj aUsersj){
        this.auth=auth;
        this.user=aUsersj;
    }
}
