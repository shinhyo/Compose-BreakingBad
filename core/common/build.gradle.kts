plugins {
    id("brba.android.library")
    id("brba.android.hilt")
}

android {
    namespace = "io.github.shinhyo.brba.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}