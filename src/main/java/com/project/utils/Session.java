package com.project.utils;

import lombok.Data;

@Data
public class Session {
    private Boolean success=false;
    private String successMessage;
    private Boolean error=false;
    private String errorMessage;
    public Session(){}
    public void setSuccess(String message){
        this.success=true;
        this.successMessage=message;
    }
    public void setError(String message){
        this.error=false;
        this.errorMessage=message;
    }
    public String getSuccessMessage(){
        // this.success=false;
        return this.successMessage;
    }
    public void hide(){
        this.success=false;
        this.error=false;
    }
}
