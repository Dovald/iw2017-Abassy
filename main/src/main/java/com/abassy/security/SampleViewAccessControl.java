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

    	if (beanName.equals("welcomeView")) {
            return true;
        }
    	if(SecurityUtils.roles() != null)
	    	switch(SecurityUtils.roles().toString()){
		    	case "GERENTE":
		    		if (beanName.equals("cierreCajaCrud")) return false;
		    		else return true;
		    	case "ENCARGADO":
		    		if(beanName.equals("usuarioCrud") || beanName.equals("localCrud") ||
		    				beanName.equals("productoCrud") || beanName.equals("familiaProductoCrud")) return false;
		    		else return true;
		    	case "CAMARERO":
		    		if(beanName.equals("usuarioCrud") || beanName.equals("localCrud") || beanName.equals("cierreCajaCrud") || 
		    				beanName.equals("productoCrud") || beanName.equals("familiaProductoCrud")) return false;
		    		else return true;
		    	default:
		    		return true;
	    	}
    	else return false;
    }
}



