import com.android.build.api.dsl.LibraryExtension
import io.github.shinhyo.brba.buildlogic.configureKoverAndroid
import io.github.shinhyo.brba.buildlogic.findLibrary

with(pluginManager) {
    apply("brba.android.library.compose")
    apply("org.jetbrains.kotlin.plugin.serialization")
}

configureKoverAndroid()

extensions.configure<LibraryExtension> {

    dependencies {
        add("implementation", project(":core:model"))
        add("implementation", project(":core:designsystem"))
        add("implementation", project(":core:common"))
        add("implementation", project(":core:domain"))

        add("implementation", findLibrary("kotlinx.serialization.json"))

        add("implementation", findLibrary("coil.kt.compose"))

        add("implementation", findLibrary("androidx.compose.material3"))
        add("implementation", findLibrary("androidx.compose.material.iconsExtended"))
        add("implementation", findLibrary("androidx.compose.animation"))
        add("implementation", findLibrary("haze"))

        add("implementation", findLibrary("androidx.navigation3.runtime"))
        add("implementation", findLibrary("androidx.navigation3.ui"))
        add("implementation", findLibrary("androidx.lifecycle.viewmodel.navigation3"))
        // `hilt-navigation-compose` 는 nav2(`navigation-compose`)를 transitively 끌어온다.
        // nav3 로 완전히 전환하려면 `hilt-lifecycle-viewmodel-compose` 를 쓴다.
        add("implementation", findLibrary("androidx.hilt.lifecycle.viewmodel.compose"))
        add("implementation", findLibrary("androidx.lifecycle.runtimeCompose"))
        add("implementation", findLibrary("androidx.lifecycle.viewModelCompose"))

        add("testImplementation", project(":core:testing"))
    }

}
