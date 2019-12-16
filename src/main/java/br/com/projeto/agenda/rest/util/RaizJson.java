package br.com.projeto.agenda.rest.util;





import com.auth0.jwt.internal.com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;



/**
 *
 * @info   Classe que representa raiz de todos response para ser renderizada JSON

@XmlRootElement
* */
public class RaizJson implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @JsonInclude(JsonInclude.Include.NON_NULL) 
    private Object data;
    
    @JsonInclude(JsonInclude.Include.NON_NULL) 
    private ErrorMessage error;

    public RaizJson() {
    }

    public RaizJson(Object data) {
        this.data = data;
    }

    public RaizJson(ErrorMessage error) {
        this.error = error;
    }

    public RaizJson(Object data, ErrorMessage error) {
        this.data = data;
        this.error = error;
    }  

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ErrorMessage getError() {
        return error;
    }

    public void setError(ErrorMessage error) {
        this.error = error;
    }        

}
