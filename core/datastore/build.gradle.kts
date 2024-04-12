plugins {
    id("brba.android.library")
}

android {
    namespace = "io.github.shinhyo.brba.core.datastore"
}

dependencies {

    implementation(project(":core:model"))

    implementation(libs.androidx.datastore.preferences)
}
