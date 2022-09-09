package it.tn.spoilers.extras

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

fun DisableLogging() {
    val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
    loggerContext.loggerList.forEach { logger ->
        logger.level = Level.INFO
    }
}