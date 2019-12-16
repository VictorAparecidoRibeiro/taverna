/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.view.converter;

import br.com.projeto.agenda.model.Classe;
import br.com.projeto.agenda.service.ClasseService;
import br.com.projeto.agenda.util.app.Util;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Usuario
 */
@FacesConverter(forClass = Classe.class)
public class ClasseConverter implements Converter {
    
    @Inject
    private ClasseService service;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Classe retorno = null;
        
       if (Util.isNotBlank(value) && Util.isInteger(value)) {
            retorno = this.service.buscaPorId(new Integer(value), false);
        }

        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
         if (value != null) {
            Classe objeto = (Classe) value;
            return objeto.getId() == null ? null : objeto.getId().toString();                        
        }
        return "";
    }
}
