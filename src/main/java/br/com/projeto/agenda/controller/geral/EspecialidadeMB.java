/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.controller.geral;

import br.com.projeto.agenda.model.Especialidade;
import br.com.projeto.agenda.service.EspecialidadeService;
import br.com.projeto.agenda.util.app.NegocioException;
import br.com.projeto.agenda.util.app.Util;
import br.com.projeto.agenda.util.jsf.JsfUtil;
import java.io.Serializable;
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
public class EspecialidadeMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EspecialidadeService service;

    private List<Especialidade> lista;

    private Especialidade especialidade;

    private Integer idTrocaPagina;
    
    
    public EspecialidadeMB(){
        lista = new ArrayList<>();
        
        especialidade = new Especialidade();
    }
    
    
     public void inicializaTela() {

        this.lista = service.buscaTodos();
    }

    public void inicializaEdicaoInclusao() {

        if (this.idTrocaPagina == null) {
            this.especialidade = new Especialidade();
        } else {
            this.especialidade = service.buscaPorId(idTrocaPagina, false);
        }

    }
    
     public void redirecionaCadastroEdicao() {

        JsfUtil.redirect("/pages/especialidade/cadastroespecialidade.xhtml");
    }

    public void excluir() throws NegocioException {

        service.excluir(this.especialidade);

        JsfUtil.addInfoMessage("Especialidade excluída com sucesso!");

        this.inicializaTela();

        this.especialidade = new Especialidade();
    }

   

    public String salvar() throws NegocioException {

        if (this.especialidade.getId() == null) {
            this.especialidade.setId(0);
        }

        if (Util.isBlank(this.especialidade.getDescricao())) {
            JsfUtil.addErrorMessage("Descrição não pode ser nula!");
            return null;
        }

       

        if (this.especialidade.getId() == 0) {
            if (!service.verificaUsoDescricao(this.especialidade.getDescricao())) {
                JsfUtil.addErrorMessage("Já existe um especialidade com esta descrição!");
                return null;
            }
        }

       

        this.especialidade = service.salvar(this.especialidade);

        if (this.especialidade != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Especialidade salva com Sucesso."));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return "listaespecialidade?faces-redirect=true";
        }

        this.especialidade = new Especialidade();
        return "";

    }

    public List<Especialidade> getLista() {
        return lista;
    }

    public void setLista(List<Especialidade> lista) {
        this.lista = lista;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public Integer getIdTrocaPagina() {
        return idTrocaPagina;
    }

    public void setIdTrocaPagina(Integer idTrocaPagina) {
        this.idTrocaPagina = idTrocaPagina;
    }
    
    
    
}
