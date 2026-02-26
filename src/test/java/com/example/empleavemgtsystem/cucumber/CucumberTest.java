package com.example.empleavemgtsystem.cucumber;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Cucumber JUnit 5 Platform Suite Runner.
 *
 * =============================================================
 * KEY FIX: GLUE_PROPERTY_NAME
 * =============================================================
 * BEFORE (broken):
 *   value = "com.example.empleavemgtsystem.cucumber.steps"
 *   -> Cucumber scanned only the steps sub-package.
 *   -> Could NOT find CucumberSpringConfig in the parent package.
 *   -> ERROR: CucumberBackendException: Please annotate a glue class
 *            with some context configuration.
 *
 * AFTER (fixed):
 *   value = "com.example.empleavemgtsystem.cucumber"
 *   -> Cucumber scans the parent package AND all sub-packages.
 *   -> Finds CucumberSpringConfig (@CucumberContextConfiguration) v
 *   -> Finds all step definition classes in .cucumber.steps v
 * =============================================================
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-reports/report.html, json:target/cucumber-reports/report.json")
@ConfigurationParameter(
        key = Constants.GLUE_PROPERTY_NAME,
        value = "com.example.empleavemgtsystem.cucumber")
public class CucumberTest {
    // JUnit Platform Suite runner -- no body needed.
    // Cucumber engine discovers and executes all feature files under "features/"
    // using step definitions found in the glue packages above.
}
