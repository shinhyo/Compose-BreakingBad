plugins {
    id("brba.android.library")
    id("brba.android.hilt")
}

android {
    namespace = "io.github.shinhyo.brba.core.network"
}

dependencies {

    implementation(project(":core:model"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging)

}