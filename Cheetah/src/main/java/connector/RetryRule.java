package connector;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.forms.Constants;

/**
 * @author Gundlupet Sreenidhi
 * 
 * This class is part of the retry functionality. 
 * This class defines the rule of how many times the test needs to be rerun in case of test failures.
 */
public class RetryRule implements TestRule {
	  private int retryCount;
	  
	    public RetryRule (int retryCount) {
	        this.retryCount = retryCount;
	    }
	 
	    public Statement apply(Statement base, Description description) {
	        return statement(base, description);
	    }
	 
	    private Statement statement(final Statement base, final Description description) {
	        return new Statement() {
	            @Override
	            public void evaluate() throws Throwable {
	                Throwable caughtThrowable = null;
	 
	                // implement retry logic here
	                for (int i = 0; i < retryCount; i++) {
	                    try {
	                        base.evaluate();
	                        return;
	                    } catch (Throwable t) {
	                        caughtThrowable = t;
	                        CheetahEngine.logger.logMessage((Exception) t, this.getClass().getName(), 
	                        		description.getDisplayName() + ": run " + (i + 1) + " failed.", Constants.LOG_ERROR);
	                     }
	                }
	                CheetahEngine.logger.logMessage(null, this.getClass().getName(), 
	                		description.getDisplayName() + ": giving up after " + retryCount + " failures.", Constants.LOG_ERROR);
	                throw caughtThrowable;
	            }
	        };
	    }
}
