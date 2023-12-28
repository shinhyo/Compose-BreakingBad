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

    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))

    implementation(project(":feature:list"))
    implementation(project(":feature:favorite"))
    implementation(project(":feature:detail"))

    implementation(libs.androidx.startup)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.compose.material3)

    implementation(libs.timber)
}
