package com.abassy.security;

import org.springframework.stereotype.Component;

import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.ui.UI;

import com.abassy.views.*;

@Component
public class SampleViewAccessControl implements ViewAccessControl {

    @Override
    public boolean isAccessGranted(UI ui, String beanName) {
    	
    	System.out.println("COMPROBANDO " + beanName + " PARA USUARIO CON ROLES: "+SecurityUtils.roles());

    	if(beanName.equals(PedidoCrud.VIEW_NAME)) {
    		return true;
    	} else if(SecurityUtils.hasRole("GERENTE")){
    		return true;
    	} else if (beanName.equals(ClienteCrud.VIEW_NAME)) {
    		return SecurityUtils.hasRole("CAMARERO") || SecurityUtils.hasRole("ENCARGADO");
        } else if (beanName.equals(UsuarioCrud.VIEW_NAME)) {
        	return SecurityUtils.hasRole("ENCARGADO");
        } else if (beanName.equals(LocalCrud.VIEW_NAME)) {
        	return SecurityUtils.hasRole("ENCARGADO");
        } else if (beanName.equals("welcomeView")) {
            return true;
        } else {
        	return false;
        }
    }
}



