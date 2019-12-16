package br.com.projeto.agenda.util.jsf;

import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

public class JsfProducer {

    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    @Produces
    @RequestScoped
    public ExternalContext getExternalContext() {
        return getFacesContext().getExternalContext();
    }

    @Produces
    @RequestScoped
    public HttpServletResponse getHttpServletResponse() {
        return ((HttpServletResponse) getExternalContext().getResponse());
    }
        
    @Produces
    @RequestScoped
    public Map<String, Object> geSeessionMap() {
        return getExternalContext().getSessionMap();
    }

}
