/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.controller.geral;

import br.com.projeto.agenda.model.Classe;
import br.com.projeto.agenda.model.Personagem;
import br.com.projeto.agenda.service.ClasseService;
import br.com.projeto.agenda.service.PersonagemService;
import br.com.projeto.agenda.util.jsf.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Usuario
 */
@Named
@ViewScoped
public class DashboardMB implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private PersonagemService service;
    
    @Inject
    private ClasseService classeService;
    
    private List<Personagem> lista;
    private List<Classe> listaClasse;
    
    private Classe classe;
    
    private Personagem personagem;
    
    private String nome;
    
    private Integer idTrocaPagina;
    
    public DashboardMB() {
        personagem = new Personagem();
        
        lista = new ArrayList<>();
        
        listaClasse = new ArrayList<>();
        
        classe = new Classe();
        
        this.nome = "";
    }
    
    public void redirecionaPaginaEdição() {
        
        JsfUtil.redirect("/pages/personagem/cadastropersonagem.xhtml?id=" + personagem.getId());
    }
    
    public void inicializaDashBoard() {
        this.lista = service.buscaTodos();
        this.listaClasse = classeService.buscaTodos();
    }
    
    public void filtro(){
        
       
        this.lista = service.buscaComFiltros(this.nome, this.classe, null);
    }
    
    public List<Personagem> getLista() {
        return lista;
    }
    
    public void setLista(List<Personagem> lista) {
        this.lista = lista;
    }
    
    public Personagem getPersonagem() {
        return personagem;
    }
    
    public void setPersonagem(Personagem personagem) {
        this.personagem = personagem;
    }
    
    public Integer getIdTrocaPagina() {
        return idTrocaPagina;
    }
    
    public void setIdTrocaPagina(Integer idTrocaPagina) {
        this.idTrocaPagina = idTrocaPagina;
    }

    public List<Classe> getListaClasse() {
        return listaClasse;
    }

    public void setListaClasse(List<Classe> listaClasse) {
        this.listaClasse = listaClasse;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
}
