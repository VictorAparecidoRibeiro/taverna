/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.service;

import br.com.projeto.agenda.model.Agenda;
import br.com.projeto.agenda.util.app.NegocioException;
import br.com.projeto.agenda.util.app.PadraoDAO;
import br.com.projeto.agenda.util.app.Util;
import br.com.projeto.agenda.util.jpa.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 *
 * @author Victor
 */
public class AgendaService implements Serializable {

    @Inject
    private PadraoDAO padraoDAO;

    public PadraoDAO getPadraoDAO() {
        return padraoDAO;
    }

    public void setPadraoDAO(PadraoDAO padraoDAO) {
        this.padraoDAO = padraoDAO;
    }

    public Boolean verificaDatasIguais(Date dataInicio, Date dataFinal) {

        Query q = this.padraoDAO.getEntityManager().createQuery("SELECT a FROM Agenda a where a.dataInicio = :inicio and a.dataFinal = :final");
        q.setParameter("inicio", dataInicio, TemporalType.TIMESTAMP);
        q.setParameter("final", dataFinal, TemporalType.TIMESTAMP);

        if (q.getResultList().isEmpty()) {

            return true;
        } else {

            return false;
        }

    }

    @Transactional
    public void excluir(Agenda agenda) throws NegocioException {
        try {
            padraoDAO.excluir(agenda);

        } catch (PersistenceException e) {
            throw new NegocioException("Registro não pode ser excluído (Registros vinculados).");
        }
    }

    public Agenda buscaPorId(int id, boolean carregaFilhos) {
        Agenda agenda = (Agenda) padraoDAO.find(Agenda.class, id);
        return agenda;
    }

    public List<Agenda> buscaTodos() {
        List<Agenda> agenda = new ArrayList<>();
        agenda = padraoDAO.findAll(Agenda.class);
        return agenda;
    }

    @Transactional
    public Agenda salvar(Agenda agenda) throws NegocioException {
        return (Agenda) padraoDAO.salvar(agenda);
    }

}
