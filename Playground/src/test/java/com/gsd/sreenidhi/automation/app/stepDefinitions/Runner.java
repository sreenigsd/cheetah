package com.gsd.sreenidhi.automation.app.stepDefinitions;

import java.io.IOException;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.engine.CucumberFormatter;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;

import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.TestStepStarted;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;

public class Runner extends CheetahEngine{
	
	@Before
    public void prepareScenario(io.cucumber.java.Scenario scenarioImpl) throws Exception{
		CheetahEngine.generateBase(scenarioImpl, "ABC");
    }
    
    @After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void processExecution(io.cucumber.java.Scenario scenarioImpl) throws Exception {
    	CheetahEngine.processPostAction(scenarioImpl, "ABC");
    }
    
    @AfterStep
    public void processStep(io.cucumber.java.Scenario scenarioImpl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, CheetahException, IOException {
    	CheetahEngine.afterStep(scenarioImpl);
    }
    
    @BeforeStep
    public void beforeStep() throws CheetahException {
    	
    }
}
