/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.service;

import br.com.projeto.agenda.model.Classe;
import br.com.projeto.agenda.model.Personagem;
import br.com.projeto.agenda.util.app.NegocioException;
import br.com.projeto.agenda.util.app.PadraoDAO;
import br.com.projeto.agenda.util.app.Util;
import br.com.projeto.agenda.util.jpa.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Usuario
 */
public class PersonagemService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PadraoDAO padraoDAO;

    public PadraoDAO getPadraoDAO() {
        return padraoDAO;
    }

    public void setPadraoDAO(PadraoDAO padraoDAO) {
        this.padraoDAO = padraoDAO;
    }

    public List<Personagem> buscaComFiltros(String nome, Classe classe, String classeNome) {

        StringBuilder sFrase = new StringBuilder("SELECT p FROM Personagem p");

        //filtros
        if (Util.isNotBlank(nome)) {
            sFrase.append(Util.colocaWhereOuAnd(sFrase.toString()) + "lower(unaccent(p.nome)) LIKE '%" + Util.removeAcentuacao(nome.toLowerCase()) + "%'");
        }

        if (classe != null) {
            sFrase.append(Util.colocaWhereOuAnd(sFrase.toString()) + "p.classe.id = " + classe.getId());
        }
        
        if(Util.isNotBlank(classeNome)){
        
             sFrase.append(Util.colocaWhereOuAnd(sFrase.toString()) + "lower(unaccent(p.classe.descricao)) LIKE '%" + Util.removeAcentuacao(classeNome.toLowerCase()) + "%'");
        }

        sFrase.append(" ORDER BY p.nome");

        try {
            TypedQuery query = this.padraoDAO.getEntityManager().createQuery(sFrase.toString(), Personagem.class);
            return query.getResultList();
        } catch (NoResultException e) {

            return null;
        }

    }

    public Boolean verificaUsoNome(String nome) {

        Query q = this.padraoDAO.getEntityManager().createQuery("SELECT p FROM Personagem p WHERE p.nome = :descricao");
        q.setParameter("descricao", nome);
        if (q.getResultList().isEmpty()) {

            return true;
        } else {

            return false;
        }

    }

    @Transactional
    public void excluir(Personagem registro) throws NegocioException {
        try {
            padraoDAO.excluir(registro);

        } catch (PersistenceException e) {
            throw new NegocioException("Registro não pode ser excluído (Registros vinculados).");
        }
    }

    public Personagem buscaPorId(int id, boolean carregaFilhos) {
        Personagem registro = (Personagem) padraoDAO.find(Personagem.class, id);
        return registro;
    }

    public List<Personagem> buscaTodos() {
        List<Personagem> registro = new ArrayList<>();
        registro = padraoDAO.findAll(Personagem.class);
        return registro;
    }

    @Transactional
    public Personagem salvar(Personagem registro) throws NegocioException {
        return (Personagem) padraoDAO.salvar(registro);
    }
}
