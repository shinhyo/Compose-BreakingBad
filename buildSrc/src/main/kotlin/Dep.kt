object Versions {
    const val compileSdk = 32

    const val minSdk = 26
    const val targetSdk = 32
    const val versionCode = 1
    const val versionName = "1.0"
}

object Dep {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.1.3"

    object Google {
        const val material = "com.google.android.material:material:1.5.0"
        const val gson = "com.google.code.gson:gson:2.9.0"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.6.0"
        const val appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val startupRuntime = "androidx.startup:startup-runtime:1.1.1"


        object ConstraintLayout {
            const val layout = "androidx.constraintlayout:constraintlayout:2.1.3"
            const val compose = "androidx.constraintlayout:constraintlayout-compose:1.0.0"
        }

        object Activity {
            private const val version = "1.4.0"
            const val activity = "androidx.activity:activity-ktx:$version"
            const val compose = "androidx.activity:activity-compose:$version"
        }

        object Fragment {
            private const val version = "1.4.1"
            const val fragment = "androidx.fragment:fragment-ktx:1.4.1"
        }


        object Lifecycle {
            private const val lifecycleVersion = "2.4.1"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
            const val viewModelCompose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
            const val livedataRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        }

        object Navigation {
            private const val version = "2.4.2"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
            const val navigationCompose = "androidx.navigation:navigation-compose:$version"
        }

        object Hilt {
            private const val version = "1.0.0"

            const val navigationCompose = "androidx.hilt:hilt-navigation-compose:$version"
            const val navigationFragment = "androidx.hilt:hilt-navigation-fragment:$version"

        }

        object Compose {
            //            const val version = "1.1.1"
            const val version = "1.2.0-alpha08"

            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val foundationLayout = "androidx.compose.foundation:foundation-layout:$version"
            const val m3 = "androidx.compose.material3:material3:1.0.0-alpha10"


            const val ui = "androidx.compose.ui:ui:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"

            const val material = "androidx.compose.material:material:$version"
            const val materialIcons = "androidx.compose.material:material-icons-extended:$version"

            const val animation = "androidx.compose.animation:animation:$version"

//        const val themeAdapter = "com.google.android.material:compose-theme-adapter:$version"
//        const val liveData = "androidx.compose.runtime:runtime-livedata:$version"
        }

        object Room {
            private const val version = "2.4.2"
            const val roomRuntime = "androidx.room:room-runtime:$version"
            const val roomKtx = "androidx.room:room-ktx:$version"
            const val roomCompiler = "androidx.room:room-compiler:$version"
        }
    }

    object Kotlin {
        const val version = "1.6.20"
        const val coroutineVersion = "1.6.1"

        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"
        const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.3.2"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
        const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$version"

        object Test {
            const val coroutineTest =
                "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion"
            const val coroutineCore =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
            const val coroutineAndroid =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"
        }
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object Square {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.1"
        const val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
    }

    object Dagger {
        private const val daggerVersion = "2.41"
        const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$daggerVersion"
        const val hiltAndroid = "com.google.dagger:hilt-android:$daggerVersion"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:$daggerVersion"
    }


    const val javaxInject = "javax.inject:javax.inject:1"
    const val timber = "com.jakewharton.timber:timber:5.0.1"
    const val coil = "io.coil-kt:coil-compose:2.0.0"

}