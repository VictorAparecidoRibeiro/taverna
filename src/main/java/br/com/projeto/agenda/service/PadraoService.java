package br.com.projeto.agenda.service;

import br.com.projeto.agenda.util.app.PadraoDAO;


public abstract class PadraoService {

    private PadraoDAO dao;

    public PadraoDAO getDAO() {
        if (this.dao == null) {
            this.dao = new PadraoDAO();
        }
        return dao;
    }
}

