plugins {
    id("brba.android.library.compose")
}

android {
    namespace = "io.github.shinhyo.brba.core.designsystem"
}

dependencies {
    implementation(libs.androidx.compose.material3)
}
