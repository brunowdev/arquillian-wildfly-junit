package org.arquillian.wildfly.example;

import java.net.URL;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SaudadorIT extends Arquillian {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class, "minha-aplicacao-it.jar").addClass(Saudador.class)
				.addClass(Math.class).addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("test-persistence.xml", "persistence.xml");
	}

	@Inject
	private Saudador saudador;

	@ArquillianResource
	URL deploymentUrl;

	@SuppressWarnings("all")
	@Test
	public void shouldBeAbleToDeprec() throws Exception {

		final String urlMontada = deploymentUrl.toString() + "recurso" + "/customer/1";

		ClientRequest request = new ClientRequest("http://localhost:8080/RESTfulExample/rest/message/rest");
		request.header("Accept", MediaType.APPLICATION_XML);

		// we're expecting a String back
		ClientResponse<String> responseObj = request.get(String.class);

		Assert.assertEquals(200, responseObj.getStatus());
		System.out.println("GET /customer/1 HTTP/1.1\n\n" + responseObj.getEntity());

		String response = responseObj.getEntity().replaceAll("<\\?xml.*\\?>", "").trim();
		Assert.assertEquals("Restful example : rest", response);

		Assert.assertEquals("Olá, Júlia!", saudador.criarSaudacao("Júlia"));
	}

	static final String ACCEPTED_HEADERS = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";

}
