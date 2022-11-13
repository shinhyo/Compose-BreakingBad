plugins {
    id("brba.android.feature")
    id("brba.android.library.compose")
}

android {
    namespace = "io.github.shinhyo.brba.feature.detail"
}

dependencies {
    implementation(libs.material)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material)

    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.androidx.constraintlayout.compose)

}