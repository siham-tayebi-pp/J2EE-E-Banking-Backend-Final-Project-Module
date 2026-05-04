package net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions;

//public class CustomerNotFoundException extends RuntimeException { non surveille
public class CustomerNotFoundException extends Exception {// surveille
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
