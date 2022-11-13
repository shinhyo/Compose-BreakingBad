plugins {
    id("brba.android.library")
    id("brba.android.library.compose")
}

android {
    namespace = "io.github.shinhyo.brba.core.ui"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons.extended)

    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)

}