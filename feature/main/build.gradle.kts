plugins {
    id("brba.android.feature")
}

android {
    namespace = "io.github.shinhyo.brba.feature.main"
}

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))

    implementation(project(":feature:bottombar"))
    implementation(project(":feature:detail"))

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.activity.compose)

//    implementation(libs.androidx.navigation.compose)
//    implementation(libs.androidx.hilt.navigation.compose)
}
