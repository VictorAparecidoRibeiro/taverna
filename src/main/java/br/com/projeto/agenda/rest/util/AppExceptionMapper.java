package br.com.projeto.agenda.rest.util;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *

 * @info   Captura as exceptions declaradas pela AppException e transforma em json
 */

@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException> {

    @Override
    public Response toResponse(AppException ex) {                
        ErrorMessage errorMessage = new ErrorMessage(ex);
        return Response.status(ex.getStatus()).entity(new RaizJson(errorMessage)).type(MediaType.APPLICATION_JSON).build();
    }

}
