package br.com.projeto.agenda.util.app;


import br.com.projeto.agenda.util.jsf.JsfUtil;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FiltroPhaseListener implements PhaseListener {

    private static final Log log = LogFactory.getLog(FiltroPhaseListener.class);

    public static final String paginaLogin = "/login.xhtml";
    public static final String paginaInicial = "/home.xhtml";
    public static final String paginaAcessoNegado = "/access.xhtml";
    public static final String paginaOperadora = "/alteraOperadora.xhtml";
    public static final String paginaTimeOut = "/login.xhtml?sessaoExpirou=S";
    public static final String paginaEditaTrimestre = "/edicaoTrimestres.xhtml";
    public static final String paginaValores = "/valores.xhtml";

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    //** Método afterPhase verifica se o Usuário se logou antes de acessar qq pagina **/
    @Override
    public void afterPhase(PhaseEvent pe) {
        //pega dados do contexto
        ExternalContext context = pe.getFacesContext().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        FacesContext fc = pe.getFacesContext();
        

        //se a sessão não estiver expirada
        if (context.getSession(false) != null && fc.getViewRoot() != null) {

            //pagina atual do sistema
            String paginaAtual = fc.getViewRoot().getViewId();

            /*
             Se o usuario não estiver logada 
             E Não for a página de login 
             E Não for um recurso do sistema (css, js, imagens)
             E a página terminar com .xhtml
             Redireciona para o login e mata a limpa a sessão
             */
            

            Object usuarioLogado = context.getSessionMap().get("usuarioLogado");
           
            //Object operadoraUrl = context.getRequestMap().get(usuarioLogado);
            if ((usuarioLogado == null)
                    && (!paginaAtual.contains(paginaLogin))
                    && (!paginaAtual.contains("/javax.faces.resource/"))
                    && (paginaAtual.endsWith(".xhtml"))) {
                try {

                    //Redireciona pra pagina de login
                    context.redirect(request.getContextPath() + paginaLogin);
                    JsfUtil.fecharSessao();

                } catch (IOException ex) {
                    log.error("Erro no afterPhase do Sistema: " + ex.getMessage(), ex);
                }
            } /*else if ((usuarioLogado != null) && (!paginaAtual.contains(paginaLogin)) && (!paginaAtual.contains(paginaAcessoNegado))  && (!paginaAtual.contains(paginaOperadora)) && (!paginaAtual.contains(paginaInicial)) && (!paginaAtual.contains("/javax.faces.resource/")) && (paginaAtual.endsWith(".xhtml"))) {
                String operadoraUrl = request.getParameter("o");

                if (Util.isNotBlank(operadoraUrl)) {
                    Integer operadoraIdUrl = Integer.parseInt(operadoraUrl);
                   

                    if (operadoraIdUrl != operadora.getId()) {
                        try {
                            context.redirect(request.getContextPath() + paginaAcessoNegado);
                        } catch (IOException ex) {
                            log.error("Erro no afterPhase do Sistema de verificação acesso negado: " + ex.getMessage(), ex);
                        }
                    }

                }
            }*/
            
        }
    }

    //** Método beforePhase verifica se ocorreu erro de timeout **/
    @Override
    public void beforePhase(PhaseEvent pe) {
        //pega dados do contexto
        ExternalContext context = pe.getFacesContext().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        String paginaAtual = request.getRequestURL().toString();

        /*
         Se a sessão estiver expirada         
         Redireciona para o login (ou para página de timeout) 
         */

        Object usuarioLogado = context.getSessionMap().get("usuarioLogado");
        if (context.getSession(false) == null) {
            if ((!paginaAtual.endsWith(paginaLogin))) {
                try {
                    //Redireciona pra pagina de timeOut
                    context.redirect(request.getContextPath() + paginaTimeOut);

                } catch (IOException ex) {
                    log.error("Erro no beforePhase do Sistema: " + ex.getMessage(), ex);
                }
            }

        }

    }
}
