package com.cx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xi.cheng
 */
public class LogbackTest {
    private static final Logger debugLogger = LoggerFactory.getLogger(LogbackTest.class);
    public static void main(String[] args) {
        // TRACE < DEBUG(默认) < INFO < WARN < ERROR
        debugLogger.error("error test");
        debugLogger.warn("warn test");
        debugLogger.info("info test");
        debugLogger.debug("debug test");
        debugLogger.trace("trace test");
    }
}
