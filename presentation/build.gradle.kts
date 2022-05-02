plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlinx-serialization")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dep.AndroidX.Compose.version
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Dep.Kotlin.coroutine)
    implementation(Dep.AndroidX.core)

    implementation(Dep.AndroidX.Compose.ui)
    implementation(Dep.AndroidX.Compose.material)
    implementation(Dep.AndroidX.Compose.materialIcons)

    implementation(Dep.AndroidX.Lifecycle.viewModelCompose)
    implementation(Dep.AndroidX.Navigation.navigationCompose)
    implementation(Dep.AndroidX.ConstraintLayout.compose)

    implementation(Dep.coil)
    implementation(Dep.AndroidX.Compose.foundation)
    implementation(Dep.AndroidX.Compose.foundationLayout)

    implementation(Dep.Dagger.hiltAndroid)
    kapt(Dep.Dagger.hiltCompiler)

    implementation(Dep.AndroidX.Hilt.navigationFragment)
    implementation(Dep.AndroidX.Hilt.navigationCompose)

    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.junitExt)
    androidTestImplementation(Dep.Test.espresso)
}