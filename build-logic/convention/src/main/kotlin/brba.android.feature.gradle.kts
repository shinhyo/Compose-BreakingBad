import io.github.shinhyo.brba.buildlogic.androidExtension
import io.github.shinhyo.brba.buildlogic.findLibrary

with(pluginManager) {
    apply("brba.android.library.compose")
}

androidExtension.apply {

    dependencies {
        add("implementation", project(":core:model"))
        add("implementation", project(":core:ui"))
        add("implementation", project(":core:designsystem"))
        add("implementation", project(":core:data"))
        add("implementation", project(":core:common"))
        add("implementation", project(":core:domain"))

        add("implementation", findLibrary("coil.kt.compose"))

        add("implementation", findLibrary("androidx.hilt.navigation.compose"))
        add("implementation", findLibrary("androidx.lifecycle.runtimeCompose"))
        add("implementation", findLibrary("androidx.lifecycle.viewModelCompose"))
    }

}