package org.arquillian.wildfly.example.app;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * A REST service for retrieving Customer records
 */
@ManagedBean
@Path("/etiquetas")
public class CustomerResource {

	private List<String> etiquetas;
	
	@PostConstruct
	public void iniciarEtiquetas() {
		this.etiquetas = new ArrayList<>();
		etiquetas.add("Clássica");
		etiquetas.add("Pop");
		etiquetas.add("Rock");
		etiquetas.add("Metal Industrial");
	}
	
    @GET
    public List<String> getAllEtiquetas() {
        return etiquetas;
    }

}
