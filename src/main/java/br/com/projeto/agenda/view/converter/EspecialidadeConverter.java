/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.view.converter;

import br.com.projeto.agenda.model.Especialidade;
import br.com.projeto.agenda.service.EspecialidadeService;
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
@FacesConverter(forClass = Especialidade.class)
public class EspecialidadeConverter implements Converter {
    
    @Inject
    private EspecialidadeService service;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Especialidade retorno = null;
        
       if (Util.isNotBlank(value) && Util.isInteger(value)) {
            retorno = this.service.buscaPorId(new Integer(value), false);
        }

        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
         if (value != null) {
            Especialidade objeto = (Especialidade) value;
            return objeto.getId() == null ? null : objeto.getId().toString();                        
        }
        return "";
    }
}
