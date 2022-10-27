package StepDefinition;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features=".\\src\\test\\resources\\Features",
glue={"StepDefinition"},
tags= "@Smoke and @Regression",	
plugin = {"pretty","json:target/cucumber-reports/reports.json",
		"junit:target/cucumber-reports/Cucumber.xml",
		"html:target/cucumber-reports/reports2.html",

		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
	


monochrome = true,
dryRun = false)
public class TestRunner {

}