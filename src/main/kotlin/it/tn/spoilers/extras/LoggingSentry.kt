package it.tn.spoilers.extras

import io.sentry.Sentry

/**
 * Class for logging the errors to Sentry
 *
 * @author Francesco Masala
 */
fun EnableSentry() {
    Sentry.init { options ->
        options.dsn = "https://8768e2b722964bd5a545446d5505c9fb@o1358021.ingest.sentry.io/6644839"
        options.tracesSampleRate = 1.0
        options.isDebug = false
    }
}