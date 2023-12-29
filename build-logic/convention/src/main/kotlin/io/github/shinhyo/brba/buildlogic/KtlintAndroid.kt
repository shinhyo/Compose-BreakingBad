package io.github.shinhyo.brba.buildlogic

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureKtlintAndroid() {
    with(pluginManager) {
        apply("org.jlleitschuh.gradle.ktlint")
        apply("org.jlleitschuh.gradle.ktlint-idea")
    }

    // https://github.com/JLLeitschuh/ktlint-gradle#configuration
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
//        version.set("1.0.1")
        debug.set(true)
        verbose.set(true)
        android.set(true)
        outputToConsole.set(true)
        outputColorName.set("RED")
        ignoreFailures.set(false)
        enableExperimentalRules.set(false)
    }
}
