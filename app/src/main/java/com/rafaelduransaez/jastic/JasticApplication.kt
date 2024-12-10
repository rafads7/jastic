package com.rafaelduransaez.jastic

import com.google.android.gms.maps.MapsInitializer.Renderer
import android.app.Application
import android.util.Log
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JasticApplication : Application(), OnMapsSdkInitializedCallback {
    override fun onCreate() {
        super.onCreate()
        MapsInitializer.initialize(applicationContext, Renderer.LEGACY, this)
    }

    override fun onMapsSdkInitialized(renderer: Renderer) {
        Log.d("MapsDemo", "The latest version of the renderer is used. Renderer: $renderer")
    }
}