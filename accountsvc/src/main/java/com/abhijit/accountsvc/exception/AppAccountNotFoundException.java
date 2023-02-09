package com.abhijit.accountsvc.exception;

public class AppAccountNotFoundException extends Exception {
    public AppAccountNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
