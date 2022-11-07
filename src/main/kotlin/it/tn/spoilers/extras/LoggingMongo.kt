package it.tn.spoilers.extras

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import org.slf4j.LoggerFactory

/**
 * Class for disabling the MongoDB logs
 *
 * @author Francesco Masala
 */
fun DisableMongoLogging() {
    val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
    val rootLogger = loggerContext.getLogger("org.mongodb.driver")
    rootLogger.level = Level.OFF
}