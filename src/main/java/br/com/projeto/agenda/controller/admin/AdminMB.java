/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.controller.admin;

import br.com.projeto.agenda.model.Usuario;
import br.com.projeto.agenda.service.UsuarioService;
import br.com.projeto.agenda.util.app.NegocioException;
import br.com.projeto.agenda.util.app.Util;
import br.com.projeto.agenda.util.jsf.JsfUtil;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Usuario
 */
@Named
@ViewScoped
public class AdminMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioService usuarioService;

    private List<Usuario> listaDeUsuarios;

    private Usuario usuario;

    private Integer idTrocaPagina;

    public AdminMB() {
        listaDeUsuarios = new ArrayList<>();
        usuario = new Usuario();
    }

    public void inicializaEdicaoInclusao() {

        if (this.idTrocaPagina == null) {
            this.usuario = new Usuario();
        } else {
            this.usuario = usuarioService.buscaPorId(idTrocaPagina, false);
        }

    }

    public void redirecionaCadastroEdicao() {

        JsfUtil.redirect("/pages/admin/cadastroadmin.xhtml");
    }

    public void excluir() throws NegocioException {

        usuarioService.excluir(this.usuario);

        JsfUtil.addInfoMessage("Usuário excluído com sucesso!");

        this.inicializaListaUsuarios();

        this.usuario = new Usuario();
    }

    public void inicializaListaUsuarios() {

        this.listaDeUsuarios = usuarioService.buscaTodos();
    }

    public String salvar() throws NegocioException {

        if (this.usuario.getId() == null) {
            this.usuario.setId(0);
        }

        if (Util.isBlank(this.usuario.getEmail())) {
            JsfUtil.addErrorMessage("Campo E-mail não pode ser vazio!");
            return null;
        }

        if (Util.isBlank(this.usuario.getSenha())) {
            JsfUtil.addErrorMessage("Campo senha não pode ser vazio!");
            return null;
        }

        if (this.usuario.getId() == 0) {
            if (!usuarioService.verificaUsoEmail(this.usuario.getEmail())) {
                JsfUtil.addErrorMessage("Já existe um usuário com este e-mail!");
                return null;
            }
        }

        this.usuario.setSenha(Util.calculaHashMD5(this.usuario.getSenha()));

        this.usuario = usuarioService.salvar(this.usuario);

        if (this.usuario != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Usuário salvo com Sucesso."));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return "listausuariosadmin?faces-redirect=true";
        }

        this.usuario = new Usuario();
        return "";

    }

    public List<Usuario> getListaDeUsuarios() {
        return listaDeUsuarios;
    }

    public void setListaDeUsuarios(List<Usuario> listaDeUsuarios) {
        this.listaDeUsuarios = listaDeUsuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getIdTrocaPagina() {
        return idTrocaPagina;
    }

    public void setIdTrocaPagina(Integer idTrocaPagina) {
        this.idTrocaPagina = idTrocaPagina;
    }

}
