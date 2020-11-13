package br.com.dw_separa_mercadoria.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.dw_separa_mercadoria.entidade.Modulo;


@FacesConverter(forClass = Modulo.class,value="conversorModulo")
public class ModuloConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Modulo) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Modulo) {
        	Modulo entity= (Modulo) value;
            if (entity != null && entity instanceof Modulo && entity.getIdmodulo() != null) {
                uiComponent.getAttributes().put( entity.getIdmodulo().toString(), entity);
                return entity.getIdmodulo().toString();
            }
        }
        return "";
    }
}