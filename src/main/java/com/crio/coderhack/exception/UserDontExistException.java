package com.crio.coderhack.exception;

public class UserDontExistException extends Exception{

    public UserDontExistException(){}

    public UserDontExistException(String message){
        super(message);
    }
    
}
