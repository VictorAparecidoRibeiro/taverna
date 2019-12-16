package br.com.projeto.agenda.util.jsf;

import br.com.projeto.agenda.util.app.AcessoRestritoException;
import br.com.projeto.agenda.util.app.NegocioException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JsfExceptionHandler extends ExceptionHandlerWrapper {

    private static Log log = LogFactory.getLog(JsfExceptionHandler.class);

    private ExceptionHandler wrapped;   

    public JsfExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();

        while (events.hasNext()) {
            ExceptionQueuedEvent event = events.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            Throwable exception = context.getException();
            NegocioException negocioException = getNegocioException(exception);
            AcessoRestritoException acessoRestritoException = getAcessoRestritoException(exception);

            try {
                //Trata exceção de ViewExcpetion
                if (exception instanceof ViewExpiredException) {
                    ViewExpiredException vee = (ViewExpiredException) exception;                                                            
                    JsfUtil.redirect("/login.xhtml?sessaoExpirou=S");                    
                } 

                //Trata exceção de Negocio - joga erro no messages
                else if (negocioException != null) {
                    JsfUtil.addErrorMessage(negocioException.getMessage());
                } 

                //Outras exceções joga pra pagina de erro    
                else {
                    //grava o erro no log
                    log.error("Erro de sistema: " + exception.getMessage(), exception);

                    //colocao erro na sesseion para pegar na página de erro
                    FacesContext fc = FacesContext.getCurrentInstance();
                    ExternalContext externalContext = fc.getExternalContext();
                    Map<String, Object> sessionMap = externalContext.getSessionMap();
                    sessionMap.put("erro", exception);                                        
                    
                    if (acessoRestritoException != null){
                        sessionMap.put("tipo", "AcessoRestrito");                                        
                    } else {
                        sessionMap.put("tipo", "Sistema");                                        
                    }
                    

                    JsfUtil.redirect("/erro.xhtml");
                }
            } finally {
                events.remove();
            }
        }

        getWrapped().handle();
    }

    private NegocioException getNegocioException(Throwable exception) {
        
        if (exception instanceof NegocioException) {
            return (NegocioException) exception;
        } 

        //Inspeciona pilha recursivamente a procura da negocio exception
        else if (exception.getCause() != null) {
            return getNegocioException(exception.getCause());
        }

        return null;
    }
    
    private AcessoRestritoException getAcessoRestritoException(Throwable exception) {
        
        if (exception instanceof AcessoRestritoException) {
            return (AcessoRestritoException) exception;
        } 

        //Inspeciona pilha recursivamente a procura da exception
        else if (exception.getCause() != null) {
            return getAcessoRestritoException(exception.getCause());
        }

        return null;
    }

}
