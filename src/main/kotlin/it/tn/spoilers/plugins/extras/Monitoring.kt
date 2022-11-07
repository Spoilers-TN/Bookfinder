package it.tn.spoilers.plugins.extras

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.metrics.micrometer.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.slf4j.event.Level

/**
 * Class for configuring the monitoring with sentry and other stuff
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.8.18
 */
fun Application.configureMonitoring() {
    log.info("[!] Starting Plugin - Monitoring.kt")
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    val appMicrometerRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)

    install(MicrometerMetrics) {
        registry = appMicrometerRegistry
    }

    routing {
        get("/health") {
            call.respondText(text = "All fine here", status = HttpStatusCode.OK)
        }
        get("/metrics-micrometer") {
            call.respond(appMicrometerRegistry.scrape())
        }
    }
    log.info("[✓] Started Plugin - Monitoring.kt")
}
