plugins {
    id("brba.android.library")
    id("com.google.devtools.ksp")
}

android {
    namespace = "io.github.shinhyo.brba.core.database"
}

dependencies {

    implementation(project(":core:model"))

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}