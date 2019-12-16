package br.com.projeto.agenda.view.converter;


import br.com.projeto.agenda.util.app.Util;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter("CPFConverter")
public class CPFConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String retorno = null;

        if (Util.isNotBlank(value)) {
            retorno = Util.removeMascara(value);
        }

        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            String formatado = Util.formataCPF((String) value);            
            return formatado;                        
        }        
        return "0";        
    }
}

