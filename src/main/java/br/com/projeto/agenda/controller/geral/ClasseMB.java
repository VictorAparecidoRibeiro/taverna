/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.controller.geral;

import br.com.projeto.agenda.model.Classe;
import br.com.projeto.agenda.model.Usuario;
import br.com.projeto.agenda.service.ClasseService;
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
public class ClasseMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ClasseService service;

    private List<Classe> lista;

    private Classe classe;

    private Integer idTrocaPagina;

    public ClasseMB() {

        lista = new ArrayList<>();

        classe = new Classe();
    }

    public void inicializaTela() {

        this.lista = service.buscaTodos();
    }

    public void inicializaEdicaoInclusao() {

        if (this.idTrocaPagina == null) {
            this.classe = new Classe();
        } else {
            this.classe = service.buscaPorId(idTrocaPagina, false);
        }

    }
    
     public void redirecionaCadastroEdicao() {

        JsfUtil.redirect("/pages/classe/cadastroclasse.xhtml");
    }

    public void excluir() throws NegocioException {

        service.excluir(this.classe);

        JsfUtil.addInfoMessage("Classe excluída com sucesso!");

        this.inicializaTela();

        this.classe = new Classe();
    }

   

    public String salvar() throws NegocioException {

        if (this.classe.getId() == null) {
            this.classe.setId(0);
        }

        if (Util.isBlank(this.classe.getDescricao())) {
            JsfUtil.addErrorMessage("Descrição não pode ser nula!");
            return null;
        }

       

        if (this.classe.getId() == 0) {
            if (!service.verificaUsoDescricao(this.classe.getDescricao())) {
                JsfUtil.addErrorMessage("Já existe um classe com esta descrição!");
                return null;
            }
        }

       

        this.classe = service.salvar(this.classe);

        if (this.classe != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Classe salva com Sucesso."));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return "listaclasses?faces-redirect=true";
        }

        this.classe = new Classe();
        return "";

    }

    public List<Classe> getLista() {
        return lista;
    }

    public void setLista(List<Classe> lista) {
        this.lista = lista;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Integer getIdTrocaPagina() {
        return idTrocaPagina;
    }

    public void setIdTrocaPagina(Integer idTrocaPagina) {
        this.idTrocaPagina = idTrocaPagina;
    }

    
    
}
