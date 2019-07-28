package com.gsd.sreenidhi.cheetah.runner;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.cheetah.runner.AfterSuite;
import com.gsd.sreenidhi.cheetah.runner.BeforeSuite;
import com.gsd.sreenidhi.forms.Constants;

import cucumber.api.junit.Cucumber;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class ExtendedCucumberRunner extends Runner {

    private Class clazz;
    private Cucumber cucumber;

    /**
     * @param clazzValue Class
     * @throws Exception Exception
     */
    public ExtendedCucumberRunner(Class clazzValue) throws Exception {
        clazz = clazzValue;
        cucumber = new Cucumber(clazzValue);
    }

    @Override
    public Description getDescription() {
        return cucumber.getDescription();
    }

    private void runPredefinedMethods(Class annotation) throws Exception {
        if (!annotation.isAnnotation()) {
            return;
        }
        Method[] methodList = this.clazz.getMethods();
        for (Method method : methodList) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation item : annotations) {
                if (item.annotationType().equals(annotation)) {
                    method.invoke(null);
                    break;
                }
            }
        }
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            runPredefinedMethods(BeforeSuite.class);
        } catch (Exception e) {
            try {
				CheetahEngine.logger.logMessage(e, "ExtendedCucumberRunner", "Exception: "+e.getMessage()+"\n"+CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR, true);
			} catch (CheetahException e1) {
				e1.printStackTrace();
			}
        }
        cucumber.run(notifier);
        try {
            runPredefinedMethods(AfterSuite.class);
        } catch (Exception e) {
        	try {
				CheetahEngine.logger.logMessage(e, "ExtendedCucumberRunner", "Exception: "+e.getMessage()+"\n"+CheetahEngine.getExceptionTrace(e), Constants.LOG_ERROR, true);
			} catch (CheetahException e1) {
				e1.printStackTrace();
			}
        }
    }

}