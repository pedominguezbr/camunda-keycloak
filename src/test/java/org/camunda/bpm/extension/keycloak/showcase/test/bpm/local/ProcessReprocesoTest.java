package org.camunda.bpm.extension.keycloak.showcase.test.bpm.local;

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.execute;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.job;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
import static org.mockito.Mockito.reset;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.runtime.VariableInstanceQuery;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.keycloak.showcase.ProcessConstants.Variable;
import org.camunda.bpm.extension.keycloak.showcase.task.LoggerDelegate;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.spsa.bpm.ventadesagregada.clase.ListaLocalesReprocesoReq;
import com.spsa.bpm.ventadesagregada.clase.ListaLocalesReprocesoRes;
import com.spsa.bpm.ventadesagregada.clase.LocalesReproceso;
import com.spsa.bpm.ventadesagregada.rest.ReprocesarRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;
import com.spsa.bpm.ventadesagregada.util.FuncionesGenerales;

public class ProcessReprocesoTest {
	/** BPMN 2 file to the process under test. */
	private static final String PROCESS_RESOURCE = "processes/process_reproceso.bpmn";
	
	/** The process definition key of the process under test. */
	private static final String PROCESS_DEFINITION_KEY = "reprocesoventadesagregada";

	/**
	 * Access to the process engine.
	 */
	@Rule
	public ProcessEngineRule rule = new ProcessEngineRule("camunda.local.cfg.xml", true);
	
	/**
	 * Mock for the sample service task.
	 */
	@Mock
	private LoggerDelegate loggerTask;
	
	/**
	 * Setup the test case.
	 */
	@Before
	public void setup() {
		// Initialize and register mocks
		MockitoAnnotations.initMocks(this);
		Mocks.register("logger", loggerTask);

		// Initialize BPM Assert
		init(rule.getProcessEngine());
	}

	/**
	 * Tear down test case.
	 */
	@After
	public void tearDown() {
		// Reset mocks
		reset(loggerTask);
	}

	// ---------------------------------------------------------------------------
	// Tests
	// ---------------------------------------------------------------------------

	/**
	 * Just tests if the process definition is deployable.
	 */
	@Test
	@Deployment(resources = PROCESS_RESOURCE)
	public void testParsingAndDeployment() {
		// nothing is done here, as we just want to check for exceptions during
		// deployment
	}

	/**
	 * Test the happy (approved) path.
	 */
	@Test
	@Deployment(resources = PROCESS_RESOURCE)
	public void testApprovedPath() throws Exception {
		// start process
		ProcessInstance pi = runtimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY,
				withVariables(Variable.NAME, "Demo"));
		assertThat(pi).isStarted();

		
		
		//System.out.println("+++++++++++++++++++++++++++++++Inicio processInstancesList+++++++++++++++++++++++++++++++");
		ReprocesarRest clienteprocesarRest = new ReprocesarRest();
		String REST_URIObtieneProcesoLocal = Constantes.URL_OBTIENE_LOCAL_REPRO_API;
		String dproceso = Constantes.EMPTY_STRING;

		Integer numlocal = 99999;
		String estado = "T";
		
		ListaLocalesReprocesoReq listaLocalesReprocesoReq = new ListaLocalesReprocesoReq();
		listaLocalesReprocesoReq.setDproceso(dproceso);
		listaLocalesReprocesoReq.setNumlocal(numlocal);
		listaLocalesReprocesoReq.setEstado(estado);
		listaLocalesReprocesoReq.setReproceso(1);
		Response response = clienteprocesarRest.ObtenerLocalReproceso(listaLocalesReprocesoReq, REST_URIObtieneProcesoLocal);
		
		System.out.println("NotificaReprocesoDelegate.execute: " + String.valueOf(response.getStatus()));
	
		//Map<String, String> variables = new HashMap<String, String>();
		ListaLocalesReprocesoRes listaLocalesReprocesoRes = response.readEntity(ListaLocalesReprocesoRes.class);
		//ArrayList<LocalesReproceso> listaLocalesInstanceOriginal=null;
		
		ArrayList<LocalesReproceso> listaLocalesInstance = FuncionesGenerales.ObtenerLocalesReprocesoSinDuplicar(listaLocalesReprocesoRes.getResult(),runtimeService());
		
		/*
		List<ProcessInstance> processInstancesList = runtimeService().createProcessInstanceQuery().active().processDefinitionKey("reprocesoventadesagregada").list();
			
		for (ProcessInstance pInstance : processInstancesList) {			
			System.out.println("++++++++++getProcessInstanceId: " + pInstance.getId());
			VariableInstanceQuery listvarInstance = runtimeService().createVariableInstanceQuery().activityInstanceIdIn(pInstance.getId()).variableName("elements");
			
			for (VariableInstance variableinstance : listvarInstance.list()) {
				System.out.println(variableinstance);
				
				ArrayList<LocalesReproceso> listaLocalesInstance = (ArrayList<LocalesReproceso>) variableinstance.getValue();
				
				for (LocalesReproceso p : listaLocalesInstance) {
					System.out.println("=======================Proceso TEST====================");
					System.out.println(p.getCodlocal());
					System.out.println(p.getSociedad());								
					System.out.println(p.getFecproceso().substring(0, 10));
				}
			}			
		}
		
		System.out.println("+++++++++++++++++++++++++++++++Fin processInstancesList+++++++++++++++++++++++++++++++");
		*/
		// check user task and approve user
		/*
		assertThat(pi).isWaitingAt("ApproveUser");
		Task task = task();
		assertNotNull("User task expected", task);
		complete(task, withVariables("approved", Boolean.TRUE));
		 */
		// check service task (asynchronous continuation)
		//execute(job());
		
		//assertThat(pi).hasPassed("Activity_09a3egn");

		// check corresponding process end
		
		//assertThat(pi).hasPassed("EndEventProcessEnded");
		//assertThat(pi).isEnded();

		// verify mocks
		//verify(loggerTask, times(1)).execute(any(DelegateExecution.class));
	}

	
	
}
