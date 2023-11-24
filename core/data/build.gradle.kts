plugins {
    id("brba.android.library")
}

android {
    namespace = "io.github.shinhyo.brba.core.data"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))

}