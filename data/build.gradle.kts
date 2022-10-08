plugins {
    id("brba.android.library")
    id("brba.android.hilt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "io.github.shinhyo.brba.data"
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.timber)
    implementation(libs.gson)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

}