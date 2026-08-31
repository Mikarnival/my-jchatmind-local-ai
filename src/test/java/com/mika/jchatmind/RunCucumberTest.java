package com.mika.jchatmind;

import org.junit.platform.suite.api.ConfigurationParametersResource;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("com.mika.jchatmind")
@ConfigurationParametersResource("cucumber.properties")
public class RunCucumberTest {
}