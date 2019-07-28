package com.gsd.sreenidhi.cheetah.cucumber;

import gherkin.formatter.model.Examples; 
import gherkin.formatter.model.Step; 
import gherkin.formatter.model.TagStatement; 
 
import java.util.ArrayList; 
import java.util.List; 
 
public class ScenarioDetails extends TagStatementDetails { 
 
    private final List<Step> steps = new ArrayList<>(); 
    private ProjectionValues projectionValues; 
 
    public ScenarioDetails(final TagStatement scenario) { 
        super(scenario); 
    } 
 
    public TagStatement getScenario() { 
        return tagStatement; 
    } 
 
    public List<Step> getSteps() { 
        return steps; 
    } 
 
    public boolean hasProjectionValues() { 
        return projectionValues != null && projectionValues.canDoProjections(); 
    } 
 
    public ProjectionValues getProjectionValues() { 
        return projectionValues; 
    } 
 
    public void setExamples(final Examples examples) { 
        projectionValues = new ProjectionValues(examples); 
    } 
}
