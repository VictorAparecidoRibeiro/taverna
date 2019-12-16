/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.controller.geral;

import br.com.projeto.agenda.model.Classe;
import br.com.projeto.agenda.model.Especialidade;
import br.com.projeto.agenda.model.Personagem;
import br.com.projeto.agenda.model.PersonagemEspecialidade;
import br.com.projeto.agenda.service.ClasseService;
import br.com.projeto.agenda.service.EspecialidadeService;
import br.com.projeto.agenda.service.PersonagemService;
import br.com.projeto.agenda.util.app.NegocioException;
import br.com.projeto.agenda.util.app.Util;
import br.com.projeto.agenda.util.jsf.JsfUtil;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import org.imgscalr.Scalr;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Usuario
 */
@Named
@ViewScoped
public class PersonagemMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PersonagemService service;

    @Inject
    private ClasseService classeService;

    @Inject
    private EspecialidadeService especialidadeService;

    private List<Personagem> lista;

    private Personagem personagem;

    private List<Classe> listaClasse;

    private List<Especialidade> listaEspecialidade;

    private Integer idTrocaPagina;

    private PersonagemEspecialidade personagemEspecialidade;

    private Especialidade especialidade;

    public PersonagemMB() {

        lista = new ArrayList<>();

        listaClasse = new ArrayList<>();

        listaEspecialidade = new ArrayList<>();

        personagem = new Personagem();

        especialidade = new Especialidade();

        personagemEspecialidade = new PersonagemEspecialidade();
    }

    //Método para pegar o icone da atividade e redimensionar o tamanho 
    public void handleFileUpload(FileUploadEvent event) throws ParseException, IOException, NegocioException {

        try {

            InputStream is = event.getFile().getInputstream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            BufferedImage img = ImageIO.read(is);
            BufferedImage scaledImg;
            if (img.getWidth() >= img.getHeight()) {
                scaledImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 32, 32);
            } else {
                scaledImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, 32, 32);
            }
            ImageIO.write(scaledImg, "png", baos);

            File arquivoTemp = File.createTempFile("imagem", ".png");
            FileOutputStream fos = new FileOutputStream(arquivoTemp);
            fos.write(baos.toByteArray());

            this.personagem.setImagem(baos.toByteArray());

        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex.getLocalizedMessage());
        }

    }

    public void inicializaTela() {

        this.lista = service.buscaTodos();
    }

    public void inicializaEdicaoInclusao() {

        if (this.idTrocaPagina == null) {
            this.personagem = new Personagem();
            this.personagem.setListaEspecialidade(new ArrayList<>());
        } else {
            this.personagem = service.buscaPorId(idTrocaPagina, false);
        }

        this.listaClasse = classeService.buscaTodos();

        this.listaEspecialidade = especialidadeService.buscaTodos();

    }

    public void redirecionaCadastroEdicao() {

        JsfUtil.redirect("/pages/personagem/cadastropersonagem.xhtml");
    }

    public void excluir() throws NegocioException {

        service.excluir(this.personagem);

        JsfUtil.addInfoMessage("Personagem excluído com sucesso!");

        this.inicializaTela();

        this.personagem = new Personagem();
    }

    public void removeEspecialidade() {
        this.personagem.getListaEspecialidade().remove(this.personagemEspecialidade);

        for (PersonagemEspecialidade obj : this.personagem.getListaEspecialidade()) {

            if (obj.equals(this.personagemEspecialidade)) {

                this.personagem.getListaEspecialidade().remove(obj);
            }

        }
    }

    public void adicionaEspecialidade() {

        this.personagemEspecialidade = new PersonagemEspecialidade();
        this.personagemEspecialidade.setId(0);
        this.personagemEspecialidade.setPersonagem(this.personagem);
        this.personagemEspecialidade.setEspecialidade(this.especialidade);

        this.personagem.getListaEspecialidade().add(this.personagemEspecialidade);

    }

    public String salvar() throws NegocioException {

        if (this.personagem.getId() == null) {
            this.personagem.setId(0);
        }

        if (Util.isBlank(this.personagem.getNome())) {
            JsfUtil.addErrorMessage("Nome não pode ser nulo!");
            return null;
        }

        if (this.personagem.getVida() == null) {
            JsfUtil.addErrorMessage("Vida não pode ser nula!");
            return null;
        }

        if (this.personagem.getId() == 0) {
            if (!service.verificaUsoNome(this.personagem.getNome())) {
                JsfUtil.addErrorMessage("Já existe um personagem com este nome!");
                return null;
            }
        }

        this.personagem = service.salvar(this.personagem);

        if (this.personagem != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Personagem salvo com Sucesso."));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return "listapersonagem?faces-redirect=true";
        }

        this.personagem = new Personagem();
        return "";

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

    public List<Especialidade> getListaEspecialidade() {
        return listaEspecialidade;
    }

    public void setListaEspecialidade(List<Especialidade> listaEspecialidade) {
        this.listaEspecialidade = listaEspecialidade;
    }

    public PersonagemEspecialidade getPersonagemEspecialidade() {
        return personagemEspecialidade;
    }

    public void setPersonagemEspecialidade(PersonagemEspecialidade personagemEspecialidade) {
        this.personagemEspecialidade = personagemEspecialidade;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

}
