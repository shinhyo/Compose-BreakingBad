import io.github.shinhyo.brba.buildlogic.androidExtension
import io.github.shinhyo.brba.buildlogic.findLibrary

with(pluginManager) {
    apply("brba.android.library.compose")
}

androidExtension.apply {

    dependencies {
        add("implementation", project(":core:model"))
        add("implementation", project(":core:designsystem"))
        add("implementation", project(":core:common"))
        add("implementation", project(":core:domain"))

        add("implementation", findLibrary("coil.kt.compose"))

        add("implementation", findLibrary("androidx.constraintlayout.compose"))
        add("implementation", findLibrary("androidx.compose.material3"))
        add("implementation", findLibrary("androidx.compose.material.iconsExtended"))
        add("implementation", findLibrary("haze"))

        add("implementation", findLibrary("androidx.navigation.compose"))
        add("implementation", findLibrary("androidx.hilt.navigation.compose"))
        add("implementation", findLibrary("androidx.lifecycle.runtimeCompose"))
        add("implementation", findLibrary("androidx.lifecycle.viewModelCompose"))
    }

}