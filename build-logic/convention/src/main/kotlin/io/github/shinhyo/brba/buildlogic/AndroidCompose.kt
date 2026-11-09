package io.github.shinhyo.brba.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

// AGP 9: buildFeatures 가 CommonExtension 에서 빠짐. Application/Library extension 으로 분리.

internal fun Project.configureAndroidComposeApplication() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.plugin.compose")
    }

    extensions.configure(ApplicationExtension::class.java) {
        buildFeatures.compose = true
        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
        }
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        includeSourceInformation.set(true)
    }
}

internal fun Project.configureAndroidComposeLibrary() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.plugin.compose")
    }

    extensions.configure(LibraryExtension::class.java) {
        buildFeatures.compose = true
        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
        }
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        includeSourceInformation.set(true)
    }
}
