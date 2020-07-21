package org.camunda.bpm.extension.keycloak.showcase;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import javax.net.ssl.SSLContext;

/**
 * The Camunda Showcase Spring Boot application.
 */
@SpringBootApplication
@EnableProcessApplication("ventadesagregada")
public class CamundaApplication {

	/** This class' logger. */
	private static final Logger LOG = LoggerFactory.getLogger(CamundaApplication.class);
	
	/**
	 * Post deployment work.
	 * @param event
	 */
	@EventListener
	public void onPostDeploy(PostDeployEvent event) {
		LOG.info("========================================");
		LOG.info("Successfully started Camunda ventadesagregada");
		LOG.info("========================================");
	}
	
	/**
	 * Starts this application.
	 * @param args arguments
	 */
	public static void main(String... args) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLSv1.2");
			ctx.init(null, null, null);
			SSLContext.setDefault(ctx);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		SpringApplication.run(CamundaApplication.class, args);
	}
	
}
