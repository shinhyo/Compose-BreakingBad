plugins {
    id("brba.android.library")
    id("brba.android.library.compose")
}

android {
    namespace = "io.github.shinhyo.brba.core.designsystem"
}

dependencies {
    implementation(libs.androidx.activity.compose)

    implementation(project(":core:model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.animation)
//    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.haze)

    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
}
