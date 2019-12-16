/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "personagem_especialidade")
@NamedQueries({
    @NamedQuery(name = "PersonagemEspecialidade.findAll", query = "SELECT p FROM PersonagemEspecialidade p"),
    @NamedQuery(name = "PersonagemEspecialidade.findById", query = "SELECT p FROM PersonagemEspecialidade p WHERE p.id = :id")})
public class PersonagemEspecialidade implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String SEQUENCE_NAME = "personagem_especialidade_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = PersonagemEspecialidade.SEQUENCE_NAME, sequenceName = PersonagemEspecialidade.SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "personagem_id", referencedColumnName = "id", nullable = false)
    private Personagem personagem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "especialidade_id", referencedColumnName = "id", nullable = false)
    private Especialidade especialidade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public void setPersonagem(Personagem personagem) {
        this.personagem = personagem;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final PersonagemEspecialidade other = (PersonagemEspecialidade) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
