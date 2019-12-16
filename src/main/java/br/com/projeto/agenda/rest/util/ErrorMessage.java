package br.com.projeto.agenda.rest.util;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *

 * @info   Classe que representa exception para ser renderizada JSON
 */
@XmlRootElement
public class ErrorMessage implements Serializable{
    private static final long serialVersionUID = 1L;

    private int status;
    private String message;
    private String solution;

    public ErrorMessage() {
    }
    
    public ErrorMessage(AppException appException) {
        this.status = appException.getStatus();
        this.message = appException.getMessage();
        this.solution = appException.getSolution();
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
