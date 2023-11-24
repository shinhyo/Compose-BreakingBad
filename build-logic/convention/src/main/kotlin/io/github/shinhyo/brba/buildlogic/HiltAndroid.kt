package io.github.shinhyo.brba.buildlogic

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureHiltAndroid() {
    with(pluginManager) {
        apply("com.google.devtools.ksp")
        apply("dagger.hilt.android.plugin")
    }

    dependencies {
        add("implementation", findLibrary("hilt.android"))
        add("ksp", findLibrary("hilt.compiler"))
    }
}