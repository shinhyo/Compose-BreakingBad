plugins {
    id("brba.android.library")
    id("brba.android.library.compose")
    id("brba.android.hilt")
}

android {
    namespace = "io.github.shinhyo.brba.presentation"
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.timber)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.androidx.constraintlayout.compose)

    implementation(libs.coil.kt.compose)

    implementation(libs.androidx.hilt.navigation.compose)
}