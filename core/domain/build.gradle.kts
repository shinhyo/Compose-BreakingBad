plugins {
    id("brba.android.library")
}

android {
    namespace = "io.github.shinhyo.brba.core.domain"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
}
