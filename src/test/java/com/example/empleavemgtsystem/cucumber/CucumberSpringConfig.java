package com.example.empleavemgtsystem.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Cucumber Spring context configuration.
 *
 * =============================================================
 * FIX for: io.cucumber.core.backend.CucumberBackendException:
 *   "Please annotate a glue class with some context configuration"
 * =============================================================
 * ROOT CAUSE :
 *   The @CucumberContextConfiguration class must reside in a package
 *   that is included in the Cucumber GLUE scan path.
 *
 *   Previously, GLUE was set to:
 *     "com.example.empleavemgtsystem.cucumber.steps"
 *   But this class is in:
 *     "com.example.empleavemgtsystem.cucumber"
 *   So Cucumber could NOT find it and threw CucumberBackendException.
 *
 * SOLUTION:
 *   Changed CucumberTest.java GLUE_PROPERTY_NAME to parent package:
 *     "com.example.empleavemgtsystem.cucumber"
 *   This covers BOTH this config class AND all step definition sub-packages.
 *
 * ALSO FIXED: Removed @AutoConfigureMockMvc (conflicts with RANDOM_PORT mode).
 * =============================================================
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSpringConfig {
    // Spring Boot auto-configures the full application context for testing.
    // No additional configuration needed here.
}
