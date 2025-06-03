plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.mapsplatform.secrets.gradle)
    alias(libs.plugins.ksp)

    alias(libs.plugins.kotlin.serialization)
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

    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:permissions"))
    implementation(project(":core:common"))
    implementation(project(":core:navigation"))
    implementation(project(":core:geofencing:data"))

    implementation(project(":feature:myjastic:lib"))
    implementation(project(":feature:saved-destinations:lib"))
    implementation(project(":feature:map:lib"))
    implementation(project(":feature:settings:presentation")) //not implemented yet

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

    //Serialization
    implementation(libs.kotlinx.serialization.json)

    //Navigation
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.fragment.ktx)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    //LeakCanary
    debugImplementation (libs.leakcanary.android)
}