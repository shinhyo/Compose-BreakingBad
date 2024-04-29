plugins {
    id("brba.android.application")
}

android {
    namespace = "io.github.shinhyo.brba"

    defaultConfig {
        applicationId = "io.github.shinhyo.brba"
        versionCode = 1
        versionName = "1.0"
    }

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
            isDebuggable = false
        }

        getByName("release") {
            isDebuggable = false
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":feature:main"))

    implementation(libs.androidx.startup)
    implementation(libs.androidx.compose.material3)
    implementation(libs.timber)
    implementation(libs.coil.kt)
}
