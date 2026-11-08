import com.android.build.api.dsl.ApplicationExtension
import io.github.shinhyo.brba.buildlogic.configureAndroidComposeApplication
import io.github.shinhyo.brba.buildlogic.configureHiltAndroid
import io.github.shinhyo.brba.buildlogic.configureKotlinAndroidApplication
import io.github.shinhyo.brba.buildlogic.findLibrary
import io.github.shinhyo.brba.buildlogic.findVersion

with(pluginManager) {
    apply("com.android.application")
}

extensions.configure<ApplicationExtension> {
    namespace = "io.github.shinhyo.brba"

    defaultConfig.applicationId = "io.github.shinhyo.brba"
    defaultConfig.targetSdk = findVersion("targetSdkVer").toInt()
    defaultConfig.versionCode = findVersion("versionCode").toInt()
    defaultConfig.versionName = findVersion("versionName")
    defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    defaultConfig.vectorDrawables.useSupportLibrary = true

    buildFeatures.buildConfig = true

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
        }

        getByName("release") {
            isDebuggable = false
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    configureKotlinAndroidApplication()
    configureAndroidComposeApplication()
    configureHiltAndroid()

    dependencies {
        add("implementation", project(":feature:main"))

        add("implementation", findLibrary("androidx.startup"))
        add("implementation", findLibrary("timber"))
        add("implementation", findLibrary("coil.kt"))
        // coil3 는 네트워크 페처가 기본 제공되지 않는다 — okhttp 페처를 붙여야 원격 이미지가 로드된다.
        add("implementation", findLibrary("coil.kt.network.okhttp"))
    }
}
