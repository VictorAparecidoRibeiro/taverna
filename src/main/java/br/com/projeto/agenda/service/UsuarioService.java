/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.service;

import br.com.projeto.agenda.model.Usuario;
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
public class UsuarioService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PadraoDAO padraoDAO;

    public PadraoDAO getPadraoDAO() {
        return padraoDAO;
    }

    public void setPadraoDAO(PadraoDAO padraoDAO) {
        this.padraoDAO = padraoDAO;
    }

    public Boolean verificaUsoEmail(String email) {

        Query q = this.padraoDAO.getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.email = :email ");
        q.setParameter("email", email);
        if (q.getResultList().isEmpty()) {

            return true;
        } else {

            return false;
        }

    }

    @Transactional
    public void excluir(Usuario usuario) throws NegocioException {
        try {
            padraoDAO.excluir(usuario);

        } catch (PersistenceException e) {
            throw new NegocioException("Registro não pode ser excluído (Registros vinculados).");
        }
    }

    public Usuario buscaPorId(int id, boolean carregaFilhos) {
        Usuario registro = (Usuario) padraoDAO.find(Usuario.class, id);
        return registro;
    }

    public List<Usuario> buscaTodos() {
        List<Usuario> registro = new ArrayList<>();
        registro = padraoDAO.findAll(Usuario.class);
        return registro;
    }

    @Transactional
    public Usuario salvar(Usuario registro) throws NegocioException {
        return (Usuario) padraoDAO.salvar(registro);
    }

}
