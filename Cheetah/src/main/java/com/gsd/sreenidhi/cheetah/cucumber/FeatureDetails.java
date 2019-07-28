package com.gsd.sreenidhi.cheetah.cucumber;

import gherkin.formatter.model.Background; 
import gherkin.formatter.model.Feature; 
import gherkin.formatter.model.Step; 
 
import java.util.ArrayList; 
import java.util.List; 
 
public class FeatureDetails extends TagStatementDetails { 
 
    private final String uri; 
    private Background background; 
    private final List<Step> backgroundSteps = new ArrayList<>(); 
    private final List<ScenarioDetails> scenarios = new ArrayList<>(); 
 
    public FeatureDetails(final String uri) { 
        super(); 
        this.uri = uri; 
    } 
 
    public String getUri() { 
        return uri; 
    } 
 
    public Feature getFeature() { 
        return (Feature) tagStatement; 
    } 
 
    public void setFeature(final Feature feature) { 
        tagStatement = feature; 
    } 
 
    public Background getBackground() { 
        return background; 
    } 
 
    public void setBackground(final Background background) { 
        this.background = background; 
    } 
 
    public List<Step> getBackgroundSteps() { 
        return backgroundSteps; 
    } 
 
    public List<ScenarioDetails> getScenarios() { 
        return scenarios; 
    } 
}