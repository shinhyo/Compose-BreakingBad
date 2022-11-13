plugins {
    id("brba.android.feature")
    id("brba.android.library.compose")
}

android {
    namespace = "io.github.shinhyo.brba.feature.main"
}

dependencies {
    implementation(libs.material)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material)

    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.androidx.constraintlayout.compose)
//    implementation(libs.androidx.hilt.navigation.compose)


//    implementation(libs.timber)


//    implementation(libs.timber)
//    implementation(libs.kotlinx.coroutines.android)
//    implementation(libs.androidx.core.ktx)
//
//    implementation(libs.androidx.compose.material)
//
//
//
//    implementation(libs.androidx.constraintlayout.compose)


}