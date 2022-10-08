buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
//    id("com.diffplug.spotless") version "6.5.1"
}


//apply(from = File("gradle/spotless.gradle"))
//apply(from = File("gradle/projectDependencyGraph.gradle"))
