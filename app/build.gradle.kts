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

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":core:designsystem"))

    implementation(project(":feature:main"))
    implementation(project(":feature:detail"))

    implementation(libs.material)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.timber)
}
