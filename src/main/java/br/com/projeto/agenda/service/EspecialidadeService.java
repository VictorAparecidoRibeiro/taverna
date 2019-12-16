/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.service;

import br.com.projeto.agenda.model.Classe;
import br.com.projeto.agenda.model.Especialidade;
import br.com.projeto.agenda.util.app.NegocioException;
import br.com.projeto.agenda.util.app.PadraoDAO;
import br.com.projeto.agenda.util.jpa.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
public class EspecialidadeService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PadraoDAO padraoDAO;

    public PadraoDAO getPadraoDAO() {
        return padraoDAO;
    }

    public void setPadraoDAO(PadraoDAO padraoDAO) {
        this.padraoDAO = padraoDAO;
    }

    public Boolean verificaUsoDescricao(String desc) {

        Query q = this.padraoDAO.getEntityManager().createQuery("SELECT e FROM Especialidade e WHERE e.descricao = :descricao");
        q.setParameter("descricao", desc);
        if (q.getResultList().isEmpty()) {

            return true;
        } else {

            return false;
        }

    }

    @Transactional
    public void excluir(Especialidade registro) throws NegocioException {
        try {
            padraoDAO.excluir(registro);

        } catch (PersistenceException e) {
            throw new NegocioException("Registro não pode ser excluído (Registros vinculados).");
        }
    }

    public Especialidade buscaPorId(int id, boolean carregaFilhos) {
        Especialidade registro = (Especialidade) padraoDAO.find(Especialidade.class, id);
        return registro;
    }

    public List<Especialidade> buscaTodos() {
        List<Especialidade> registro = new ArrayList<>();
        registro = padraoDAO.findAll(Especialidade.class);
        return registro;
    }

    @Transactional
    public Especialidade salvar(Especialidade registro) throws NegocioException {
        return (Especialidade) padraoDAO.salvar(registro);
    }
}
