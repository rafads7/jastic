plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:base"))
    implementation(project(":core:contacts:domain"))

    implementation(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.core)
}

