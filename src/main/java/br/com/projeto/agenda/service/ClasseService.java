/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.service;

import br.com.projeto.agenda.model.Classe;
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
public class ClasseService implements Serializable {
    
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

        Query q = this.padraoDAO.getEntityManager().createQuery("SELECT c FROM Classe c WHERE c.descricao = :descricao");
        q.setParameter("descricao", desc);
        if (q.getResultList().isEmpty()) {

            return true;
        } else {

            return false;
        }

    }

    @Transactional
    public void excluir(Classe registro) throws NegocioException {
        try {
            padraoDAO.excluir(registro);

        } catch (PersistenceException e) {
            throw new NegocioException("Registro não pode ser excluído (Registros vinculados).");
        }
    }

    public Classe buscaPorId(int id, boolean carregaFilhos) {
        Classe registro = (Classe) padraoDAO.find(Classe.class, id);
        return registro;
    }

    public List<Classe> buscaTodos() {
        List<Classe> registro = new ArrayList<>();
        registro = padraoDAO.findAll(Classe.class);
        return registro;
    }

    @Transactional
    public Classe salvar(Classe registro) throws NegocioException {
        return (Classe) padraoDAO.salvar(registro);
    }
}
