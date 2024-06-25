package io.github.shinhyo.brba.buildlogic

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid() {
    pluginManager.apply("org.jetbrains.kotlin.android")

    androidExtension.apply {
        compileSdk = findVersion("compileSdkVer").toString().toInt()

        defaultConfig {
            minSdk = findVersion("minSdkVer").toString().toInt()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }
    }

    configureKotlin()
    configureDependencies()
}

private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            val warningsAsErrors: String? by project
            allWarningsAsErrors.set(warningsAsErrors.toBoolean())
            freeCompilerArgs.set(
                freeCompilerArgs.get() + listOf(
                    "-Xcontext-receivers",
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                    "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
                    "-opt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
                ),
            )
        }
    }
}

private fun Project.configureDependencies() {
    dependencies {
        add("coreLibraryDesugaring", findLibrary("android.desugarJdkLibs"))
        add("implementation", findLibrary("kotlinx.coroutines.android"))
    }
}
