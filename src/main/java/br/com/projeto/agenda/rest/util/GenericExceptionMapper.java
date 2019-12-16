package br.com.projeto.agenda.rest.util;



import br.com.projeto.agenda.util.app.Util;
import java.util.Date;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 
 
 * @info   Captura todas as exceptions de erros não tratados pela AppException e transforma em json
 */

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {        

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatus(this.statusHttp(ex).getStatusCode());
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setSolution("Log no servidor na data " + Util.formataDataHora(new Date()));  
        ex.printStackTrace();

        return Response.status(errorMessage.getStatus()).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
    }
    
    
    private Response.Status statusHttp(Throwable ex) {
        //401 - Unathorized
        if  (ex instanceof NotAuthorizedException) {
            return Response.Status.UNAUTHORIZED;
        }            
            
        //500 status default    
        else {
            ex.printStackTrace();
            return Response.Status.INTERNAL_SERVER_ERROR;
        }
    }
    
}
