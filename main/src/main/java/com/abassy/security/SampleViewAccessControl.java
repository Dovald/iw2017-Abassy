package com.abassy.security;

import org.springframework.stereotype.Component;

import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.ui.UI;

@Component
public class SampleViewAccessControl implements ViewAccessControl {

    @Override
    public boolean isAccessGranted(UI ui, String beanName) {
    	
    	/*if(SecurityUtils.getUserLogin() != null)
    	System.out.println("COMPROBANDO " + beanName + " PARA USUARIO CON ROL: "+ SecurityUtils.getUserLogin().getTipo());*/

    	if (beanName.equals("welcomeView")) {
            return true;
        }
    	if(SecurityUtils.getUserLogin() != null)
	    	switch(SecurityUtils.getUserLogin().getTipo()){
		    	case "GERENTE":
		    		return true;
		    	case "ENCARGADO":
		    		if(beanName.equals("usuarioCrud") || beanName.equals("localCrud") ||
		    				beanName.equals("productoCrud") || beanName.equals("familiaProductoCrud")) return false;
		    		else return true;
		    	case "CAMARERO":
		    		if(beanName.equals("usuarioCrud") || beanName.equals("localCrud") || beanName.equals("cierreCajaCrud") || 
		    				beanName.equals("productoCrud") || beanName.equals("familiaProductoCrud") || beanName.equals("zonaCrud")
		    				|| beanName.equals("mesaCrud")) return false;
		    		else return true;
		    	default:
		    		return true;
	    	}
    	else return false;
    }
}



