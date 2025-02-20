plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlin.android)
    //alias(libs.plugins.kotlin.compose) USE WHEN KOTLIN VERSION 2.0.0+
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.mapsplatform.secrets.gradle)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization") version "2.0.21"
}

secrets {
    // Optionally specify a different file name containing your secrets.
    // The plugin defaults to "local.properties"
    propertiesFileName = "secrets.properties"

    // A properties file containing default secret values. This file can be
    // checked in version control.
    defaultPropertiesFileName = "local.defaults.properties"

    // Configure which keys should be ignored by the plugin by providing regular expressions.
    // "sdk.dir" is ignored by default.
    ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
    ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
}

android {
    namespace = "com.rafaelduransaez.jastic"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.rafaelduransaez.jastic"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {

    implementation(project(mapOf("path" to ":core:navigation")))
    implementation(project(mapOf("path" to ":core:ui")))
    implementation(project(mapOf("path" to ":feature:myjastic:presentation")))
    implementation(project(mapOf("path" to ":feature:myjastic:di")))
    implementation(project(mapOf("path" to ":feature:settings:presentation")))

    //Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //implementation(libs.androidx.material3.windows.sizeclass)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.play.services.location)

    //Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material3.adaptive.navigation.suite)
    implementation(libs.androidx.material3.windows.sizeclass)

    //Permissions
    implementation(libs.accompanist.permissions)

    //Serialization
    implementation(libs.kotlinx.serialization.json)

    //Navigation
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.fragment.ktx)

    //Maps
    implementation(libs.play.services.maps)
    implementation(libs.maps.compose)
    implementation(libs.maps.compose.utils) //Utils for Clustering, Street View metadata checks, etc
    implementation(libs.maps.compose.widgets) //Widgets library for ScaleBar, etc
    implementation(libs.maps.ktx) // KTX for the Maps SDK for Android library
    implementation(libs.maps.utils.ktx) // KTX for the Maps SDK for Android Utility Library

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
}