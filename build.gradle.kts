// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.1.3" apply false
    id("com.android.library") version "7.1.3" apply false
    id("org.jetbrains.kotlin.android") version Dep.Kotlin.version apply false
    id("org.jetbrains.kotlin.jvm") version Dep.Kotlin.version apply false
    id("com.diffplug.spotless") version "6.5.1"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dep.androidGradlePlugin)
        classpath(Dep.Kotlin.gradlePlugin)
        classpath(Dep.Dagger.hiltGradlePlugin)
        classpath(Dep.Kotlin.serializationPlugin)
    }
}

subprojects {
    afterEvaluate {
        project.apply("$rootDir/gradle/common.gradle")
    }
}
apply(from = File("gradle/spotless.gradle"))
apply(from = File("gradle/projectDependencyGraph.gradle"))
