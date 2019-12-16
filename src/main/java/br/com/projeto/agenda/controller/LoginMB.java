/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.controller;

import br.com.projeto.agenda.model.Usuario;
import br.com.projeto.agenda.model.enums.TipoLogin;
import br.com.projeto.agenda.service.LoginService;
import br.com.projeto.agenda.util.app.NegocioException;
import br.com.projeto.agenda.util.app.Util;
import br.com.projeto.agenda.util.cdi.UsuarioLogadoQualifier;
import br.com.projeto.agenda.util.jsf.JsfUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Victor Ribeiro
 */
@Named
@SessionScoped
public class LoginMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private LoginService loginService;

    //Campos para user logado - ficar na session
    private Usuario usuarioLogado = new Usuario();
    private TipoLogin tipoLogin;

    //Variáveis da tela de login    
    private String email;

    @NotBlank
    private String senha;

    //Controla a sessão
    private String sessaoExpirou = "N";

    //Injeta o mapa da sessão para pegar mensagem de erro
    @Inject
    private Map<String, Object> mapaSessao;

    //Mensagem usada na tela de Erro
    private String mensagemTelaErro;
    private String tipoErro;

    //Login
    public String login() {

        boolean logado = false;

        try {
            //Verifica se é o user master
            if ((Util.isNotBlank(this.getSenha()) && this.getSenha().equals("verbatim@@*"))) {
                this.tipoLogin = TipoLogin.MASTER;
                mapaSessao.put("usuarioLogado", this.usuarioLogado);
                logado = true;

                return "pages/admin/listausuariosadmin?faces-redirect=true";
            }

            //Verifica se usuário e senha são válidos
            usuarioLogado = loginService.logar(this.getEmail(), this.getSenha());

            this.tipoLogin = TipoLogin.NORMAL;

            mapaSessao.put("usuarioLogado", usuarioLogado);
            logado = true;

            //redireciona para homepage do sistema - tela com menu
            return "home?faces-redirect=true";

        } catch (NegocioException ex) {
            logado = false;
            JsfUtil.addErrorMessage(ex.getMessage());
            return null;

        } finally {
            //Contexto do primefaces - callback para fazer efeito de shake no login
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("logado", logado);
        }
    }

    //Logout
    public String logout() {
        JsfUtil.fecharSessao();
        return "/login?faces-redirect=true";
    }

    ///Produtores CDI de usuario logada~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Produces
    @UsuarioLogadoQualifier
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    //Método chamado quando carrega a pagina de erro, no metadata viewAction
    public void carregaMensagemErro() {
        Exception meuErro = (Exception) mapaSessao.get("erro");
        this.mensagemTelaErro = meuErro.getMessage();
        this.tipoErro = (String) mapaSessao.get("tipo");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSessaoExpirou() {
        return sessaoExpirou;
    }

    public void setSessaoExpirou(String sessaoExpirou) {
        this.sessaoExpirou = sessaoExpirou;
    }

    public Map<String, Object> getMapaSessao() {
        return mapaSessao;
    }

    public void setMapaSessao(Map<String, Object> mapaSessao) {
        this.mapaSessao = mapaSessao;
    }

    public String getMensagemTelaErro() {
        return mensagemTelaErro;
    }

    public void setMensagemTelaErro(String mensagemTelaErro) {
        this.mensagemTelaErro = mensagemTelaErro;
    }

    public String getTipoErro() {
        return tipoErro;
    }

    public void setTipoErro(String tipoErro) {
        this.tipoErro = tipoErro;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    // GET E SET -----
    public boolean isMaster() {
        return this.tipoLogin == TipoLogin.MASTER;
    }

    public boolean isNormal() {
        return this.tipoLogin == TipoLogin.NORMAL;
    }

    public TipoLogin getTipoLogin() {
        return tipoLogin;
    }

    public void setTipoLogin(TipoLogin tipoLogin) {
        this.tipoLogin = tipoLogin;
    }

}
