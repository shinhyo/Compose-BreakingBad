package io.github.shinhyo.brba.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// AGP 9: CommonExtension 에서 SDK/compileOptions 가 빠짐.
// ApplicationExtension / LibraryExtension 으로 분리.
// kotlin-android plugin 없이도 built-in Kotlin 으로 컴파일되며,
// kotlin {} extension 만 있으면 compilerOptions DSL 사용 가능.

internal fun Project.configureKotlinAndroidApplication() {
    extensions.configure(ApplicationExtension::class.java) {
        compileSdk = findVersion("compileSdkVer").toInt()
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        defaultConfig {
            minSdk = findVersion("minSdkVer").toInt()
        }
    }

    configureJava17()
    configureKotlin()
}

internal fun Project.configureKotlinAndroidLibrary() {
    extensions.configure(LibraryExtension::class.java) {
        compileSdk = findVersion("compileSdkVer").toInt()
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        defaultConfig {
            minSdk = findVersion("minSdkVer").toInt()
        }
    }

    configureJava17()
    configureKotlin()
}

// AGP 9: ApplicationExtension/LibraryExtension 에 compileOptions 가 빠짐.
// java { } 블록 + 태스크 후크 + Kotlin JVM target 모두 17 로 강제.
private fun Project.configureJava17() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

private fun Project.configureKotlin() {
    extensions.configure<KotlinAndroidProjectExtension> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            allWarningsAsErrors.set(project.findProperty("warningsAsErrors")?.toString().toBoolean())
            freeCompilerArgs.addAll(
                listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlin.ExperimentalUnsignedTypes",
                    "-opt-in=kotlinx.coroutines.FlowPreview",
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-opt-in=kotlinx.coroutines.InternalCoroutinesApi",
                    "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                    "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
                    "-opt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
                    // @ApplicationContext 등 qualifier annotation이 value parameter와 field 양쪽에 적용.
                    "-Xannotation-default-target=param-property",
                ),
            )
        }
    }

    dependencies {
        add("coreLibraryDesugaring", findLibrary("android.desugarJdkLibs"))
        add("implementation", findLibrary("kotlinx.coroutines.android"))
    }
}
