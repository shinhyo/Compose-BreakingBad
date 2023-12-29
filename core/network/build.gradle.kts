plugins {
    id("brba.android.library")
}

android {
    namespace = "io.github.shinhyo.brba.core.network"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":core:model"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging)
}
