package cucumber.runner;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(plugin = {"pretty",
        "json:build/cucumber-report.json",
        "de.monochromata.cucumber.report.PrettyReports:build/cucumber"},
        features = {"classpath:features"}, glue = {"cucumber.steps"})
@RunWith(Cucumber.class)

public abstract class AbstractRunner {
}
