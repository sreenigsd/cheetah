package base;


import java.io.IOException;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;

import io.cucumber.java.*;



public class Runner extends CheetahEngine{
	
	public static void main(String args[]) {
		
	}
	
	@Before
    public void prepareScenario(io.cucumber.java.Scenario scenarioImpl) throws Exception{
		CheetahEngine.generateBase(scenarioImpl, CheetahEngine.props.getProperty("app.name"));
	}
    
    @After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void processExecution(io.cucumber.java.Scenario scenarioImpl) throws Exception {
    	CheetahEngine.processPostAction(scenarioImpl, CheetahEngine.props.getProperty("app.name"));
    }
    
    @AfterStep
    public void processStep(io.cucumber.java.Scenario scenarioImpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, CheetahException, IOException {
    	CheetahEngine.afterStep(scenarioImpl);
    }

	

}
