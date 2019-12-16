/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "personagem")
@NamedQueries({
    @NamedQuery(name = "Personagem.findAll", query = "SELECT p FROM Personagem p"),
    @NamedQuery(name = "Personagem.findById", query = "SELECT p FROM Personagem p WHERE p.id = :id")})
public class Personagem implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String SEQUENCE_NAME = "personagem_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = Personagem.SEQUENCE_NAME, sequenceName = Personagem.SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "classe_id", referencedColumnName = "id", nullable = false)
    private Classe classe;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "imagem", length = 100, nullable = false, columnDefinition = "bytea")
    private byte[] imagem;

    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "vida", precision = 10, scale = 2, nullable = false)
    private Double vida;

    @Column(name = "dano", precision = 10, scale = 2, nullable = false)
    private Double dano;

    @Column(name = "defesa", precision = 10, scale = 2)
    private Double defesa;

    @Column(name = "vel_ataque", precision = 10, scale = 2)
    private Double velocidadeAtaque;

    @Column(name = "vel_movimento", precision = 10, scale = 2)
    private Double velocidadeMovimento;

    @Column(columnDefinition = "text", name = "observacao")
    private String observacao;

    //Lista de especialidades
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personagem", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PersonagemEspecialidade> listaEspecialidade = new ArrayList<>();

    public String getEspecialidades() {

        StringBuilder retorno = new StringBuilder("");

        if (!this.listaEspecialidade.isEmpty()) {
            for (PersonagemEspecialidade e : listaEspecialidade) {
                retorno.append(" - "+e.getEspecialidade().getDescricao());
            }
        }
        
        return retorno.toString();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Personagem other = (Personagem) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public List<PersonagemEspecialidade> getListaEspecialidade() {
        return listaEspecialidade;
    }

    public void setListaEspecialidade(List<PersonagemEspecialidade> listaEspecialidade) {
        this.listaEspecialidade = listaEspecialidade;
    }

    public Double getDano() {
        return dano;
    }

    public void setDano(Double dano) {
        this.dano = dano;
    }

}
