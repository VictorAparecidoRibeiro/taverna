package br.com.projeto.agenda.rest.util;

import javax.ws.rs.core.Response;

/**
 *

 * @info   Exception padrão para ser usada nas classes REST
 */
public class AppException extends Exception {
    private static final long serialVersionUID = 1L;

    private int status;
    private String message;
    private String solution = "";

    public AppException() {
    }
    
    public AppException(String message) {
        super(message);        
        this.status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();//500
        this.message = message;        
    }
    
    public AppException(String message, int status) {
        super(message);        
        this.status = status;
        this.message = message;
    }

    public AppException(String message, int status, String solution) {
        super(message);        
        this.status = status;
        this.message = message;
        this.solution = solution;
    }        

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }    
}
