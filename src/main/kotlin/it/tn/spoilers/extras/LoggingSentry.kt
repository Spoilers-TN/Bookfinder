package it.tn.spoilers.extras

import io.sentry.Sentry

fun EnableSentry() {
    Sentry.init { options ->
        options.dsn = ""
        options.tracesSampleRate = 1.0
        options.isDebug = false
    }
}