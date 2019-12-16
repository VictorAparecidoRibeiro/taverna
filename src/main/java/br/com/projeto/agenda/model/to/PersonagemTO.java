/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.model.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class PersonagemTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private ClasseTO classe;

    private byte[] imagem;
    private String nome;

    private Double vida;

    private Double dano;

    private Double defesa;

    private Double velocidadeAtaque;

    private Double velocidadeMovimento;

    private String observacao;

    //Lista de especialidades
    private List<EspecialidadeTO> listaEspecialidadeTO = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClasseTO getClasse() {
        return classe;
    }

    public void setClasse(ClasseTO classe) {
        this.classe = classe;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getVida() {
        return vida;
    }

    public void setVida(Double vida) {
        this.vida = vida;
    }

    public Double getDano() {
        return dano;
    }

    public void setDano(Double dano) {
        this.dano = dano;
    }

    public Double getDefesa() {
        return defesa;
    }

    public void setDefesa(Double defesa) {
        this.defesa = defesa;
    }

    public Double getVelocidadeAtaque() {
        return velocidadeAtaque;
    }

    public void setVelocidadeAtaque(Double velocidadeAtaque) {
        this.velocidadeAtaque = velocidadeAtaque;
    }

    public Double getVelocidadeMovimento() {
        return velocidadeMovimento;
    }

    public void setVelocidadeMovimento(Double velocidadeMovimento) {
        this.velocidadeMovimento = velocidadeMovimento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<EspecialidadeTO> getListaEspecialidadeTO() {
        return listaEspecialidadeTO;
    }

    public void setListaEspecialidadeTO(List<EspecialidadeTO> listaEspecialidadeTO) {
        this.listaEspecialidadeTO = listaEspecialidadeTO;
    }
    
    
}
