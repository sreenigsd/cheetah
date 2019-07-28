package com.gsd.sreenidhi.automation.app.stepDefinitions;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;

import cucumber.api.java.After;
import cucumber.api.java.Before;



public class Runner extends CheetahEngine{
	
	public static void main(String args[]) {
		
	}
	
	@Before
    public void prepareScenario() throws Exception{
		CheetahEngine.generateBase(CheetahEngine.props.getProperty("app.name"));
    }
    
    @After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void processExecution(cucumber.api.Scenario scenarioImpl) throws Exception {
    	CheetahEngine.processPostAction(scenarioImpl, CheetahEngine.props.getProperty("app.name"));
    }
    


	

}
