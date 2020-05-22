package com.gsd.sreenidhi.automation.app.stepDefinitions;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;

import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Runner extends CheetahEngine{
	
	@Before
    public void prepareScenario() throws Exception{
		CheetahEngine.generateBase(CheetahEngine.props.getProperty("app.name"));
    }
    
    @After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void processExecution(io.cucumber.java.Scenario scenarioImpl) throws Exception {
    	CheetahEngine.processPostAction(scenarioImpl, CheetahEngine.props.getProperty("app.name"));
    }
}
