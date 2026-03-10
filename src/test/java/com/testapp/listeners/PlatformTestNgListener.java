package com.testapp.listeners;

import com.testapp.utils.Driver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * Sets thread-local platform based on TestNG XML <parameter name="platform" .../>
 * This is the safest way to ensure parallel <test> blocks use correct platform.
 */
public class PlatformTestNgListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        String platform = null;
        try {
            platform = testResult.getTestContext()
                    .getCurrentXmlTest()
                    .getParameter("platform");
        } catch (Exception ignored) {
            // ignore
        }

        if (platform != null && !platform.isBlank()) {
            System.out.println("[PlatformTestNgListener] thread=" + Thread.currentThread().getName()
                    + " xmlTest=" + testResult.getTestContext().getName()
                    + " platform=" + platform);
            Driver.setPlatform(platform);
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        // keep platform for Cucumber hooks within the same thread; cleanup happens at driver close
    }
}

