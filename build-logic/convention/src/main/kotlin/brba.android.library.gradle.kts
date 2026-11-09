import com.android.build.api.dsl.LibraryExtension
import io.github.shinhyo.brba.buildlogic.configureHiltAndroid
import io.github.shinhyo.brba.buildlogic.configureKotlinAndroidLibrary
import io.github.shinhyo.brba.buildlogic.findLibrary

with(pluginManager) {
    apply("com.android.library")
}

extensions.configure<LibraryExtension> {
    dependencies {
        add("implementation", findLibrary("timber"))
    }
}

configureKotlinAndroidLibrary()
configureHiltAndroid()
