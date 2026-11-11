package dev.couchtv.ontv

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

/**
 * An invisible screen that only starts the server, for the couch-tv home to call when this app's
 * server stops answering. On a TCL Google TV, TCL's boot manager (TclAppBoot) refuses
 * startForeground to BridgeService whenever it is started from the background (after an update,
 * or when Android restarts the sticky service), so Android kills the app ten seconds later
 * (ForegroundServiceDidNotStartInTimeException) and nothing brings it back. Started from an
 * activity in front, TCL lets it through. This screen is translucent, has no content, stays for
 * about a second so the service is in the foreground before the app leaves it, then closes. It
 * starts nothing but this app's own service, so it is safe to leave exported.
 */
class WakeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("couchtv", "woken by ${callingPackage ?: referrer ?: "?"}: starting the server")
        App.startService(this)
        Handler(Looper.getMainLooper()).postDelayed({ finish(); overridePendingTransition(0, 0) }, 1200)
    }
}
