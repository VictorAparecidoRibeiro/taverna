package br.com.projeto.agenda.util.jsf;

import java.io.IOException;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

public class JsfUtil {

    public static boolean isPostback() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public static boolean isNotPostback() {
        return !isPostback();
    }

    public static void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
    }

    public static void addInfoMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
    }

    public static void redirect(String page) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            String contextPath = externalContext.getRequestContextPath();

            externalContext.redirect(contextPath + page);
            facesContext.responseComplete();
        } catch (IOException e) {
            throw new FacesException(e);
        }
    }

    public static void fecharSessao() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static ExternalContext getExternalContext() {        
        return getFacesContext().getExternalContext();
    }
    
    public static ServletContext getServletContext() {        
        return (ServletContext) getExternalContext().getContext();
    }

    public static HttpServletResponse getHttpServletResponse() {
        return ((HttpServletResponse) getExternalContext().getResponse());
    }

    public static String pegaParametroPelaUrl(String nomeParametro) {
        Map paramMap = getExternalContext().getRequestParameterMap();
        return (String) paramMap.get(nomeParametro);
    }

    public static Object pegaObjetoDaSessao(String nomeChave) {
        Map sessionMap = getExternalContext().getSessionMap();
        return sessionMap.get(nomeChave);
    }

    public static void setaObjetoNaSessao(String nomeChave, Object objeto) {
        Map attrMap = getExternalContext().getSessionMap();
        attrMap.put(nomeChave, objeto);
    }
}
