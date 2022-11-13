plugins {
    id("brba.android.library")
    id("brba.android.hilt")
}

android {
    namespace = "io.github.shinhyo.brba.core.data"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))

    implementation(libs.kotlinx.coroutines.android)

}