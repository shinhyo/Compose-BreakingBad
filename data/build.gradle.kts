plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
}

dependencies {
    implementation(project(":domain"))

    implementation(Dep.Kotlin.coroutineCore)
    implementation(Dep.timber)
    implementation(Dep.Google.gson)

    implementation(Dep.Dagger.hiltAndroid)
    kapt(Dep.Dagger.hiltCompiler)

    implementation(Dep.Square.retrofit)
    implementation(Dep.Square.converterGson)
    implementation(Dep.Square.loggingInterceptor)

    implementation(Dep.AndroidX.Room.roomRuntime)
    implementation(Dep.AndroidX.Room.roomKtx)
    kapt(Dep.AndroidX.Room.roomCompiler)

}