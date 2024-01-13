package com.gsd.sreenidhi.automation.app.stepDefinitions;

import org.openqa.selenium.By;

import com.gsd.sreenidhi.cheetah.actions.selenium.SeleniumActions;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class Sample {
	@Given("I launch the browser URL {string}")
	public void i_launch_the_browser_URL(String string) throws CheetahException {
		SeleniumActions.navigate_to("https://www.google.com");
	}

	@Then("I enter the query as {string}")
	public void i_enter_the_query_as(String string) throws CheetahException {
		SeleniumActions.click(By.name("q"));
		SeleniumActions.enter_text(By.name("q"), "automation");
		SeleniumActions.enter();
	}
}
