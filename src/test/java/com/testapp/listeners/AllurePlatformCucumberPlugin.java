package com.testapp.listeners;

import com.testapp.utils.ConfigReader;
import com.testapp.utils.Driver;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseStarted;
import io.qameta.allure.model.Label;
import io.qameta.allure.model.Parameter;

public class AllurePlatformCucumberPlugin implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
    }

    private void onTestCaseStarted(TestCaseStarted event) {
        String platform = Driver.getPlatform();
        if (platform == null || platform.isBlank()) {
            platform = System.getProperty("platform");
        }
        if (platform == null || platform.isBlank()) {
            platform = ConfigReader.getProperty("platform");
        }
        platform = platform.trim().toLowerCase();
        final String finalPlatform = platform;

        final String platformSuffix = " [" + finalPlatform.toUpperCase() + "]";
        final String platformHistorySuffix = ":" + finalPlatform;

        io.qameta.allure.Allure.getLifecycle().getCurrentTestCase().ifPresent(uuid ->
                io.qameta.allure.Allure.getLifecycle().updateTestCase(uuid, testResult -> {
                    String name = testResult.getName();
                    if (name != null && !name.endsWith(platformSuffix)) {
                        testResult.setName(name + platformSuffix);
                    }

                    String fullName = testResult.getFullName();
                    if (fullName == null || fullName.isBlank()) {
                        fullName = (name == null ? "Cucumber Scenario" : name);
                    }
                    if (!fullName.endsWith(platformSuffix)) {
                        testResult.setFullName(fullName + platformSuffix);
                    }

                    String historyId = testResult.getHistoryId();
                    if (historyId == null || historyId.isBlank()) {
                        historyId = testResult.getFullName();
                    }
                    if (historyId != null && !historyId.endsWith(platformHistorySuffix)) {
                        testResult.setHistoryId(historyId + platformHistorySuffix);
                    }

                    // Ensure platform is visible in report (Parameters + Labels)
                    var params = new java.util.ArrayList<>(testResult.getParameters());
                    params.add(new Parameter().setName("platform").setValue(finalPlatform));
                    testResult.setParameters(params);

                    var labels = new java.util.ArrayList<>(testResult.getLabels());
                    labels.add(new Label().setName("platform").setValue(finalPlatform));
                    testResult.setLabels(labels);
                })
        );
    }
}

