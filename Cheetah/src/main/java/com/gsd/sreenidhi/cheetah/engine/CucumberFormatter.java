package com.gsd.sreenidhi.cheetah.engine;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import com.aventstack.extentreports.Status;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestStepStarted;

public class CucumberFormatter implements ConcurrentEventListener{

	@Override
	public void setEventPublisher(EventPublisher publisher) {
		 publisher.registerHandlerFor(TestStepStarted.class, runStartedHandler);
	}
	
	private EventHandler<TestStepStarted> runStartedHandler = new EventHandler<TestStepStarted>() {
        @Override
        public void receive(TestStepStarted event) {
            startReport(event);
        }
    };

    private void startReport(TestStepStarted event) {
        if (!(event.getTestStep() instanceof PickleStepTestStep)) {
            return;
        }
        PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();
      //  log("Step: " + testStep.getStepText());
    }
    
    public static void stepRecorder(TestStepStarted event) throws CheetahException { 
    	if (!(event.getTestStep() instanceof PickleStepTestStep)) {
            return;
        }
        PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();
       CheetahEngine.logger.logMessage(null, CucumberFormatter.class.getName(), testStep.getStepText(), Constants.LOG_INFO);
	}
}
