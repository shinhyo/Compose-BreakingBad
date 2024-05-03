import io.github.shinhyo.brba.buildlogic.androidExtension
import io.github.shinhyo.brba.buildlogic.configureHiltAndroid
import io.github.shinhyo.brba.buildlogic.configureKotlinAndroid
import io.github.shinhyo.brba.buildlogic.findLibrary

with(pluginManager) {
    apply("com.android.library")
}

androidExtension.apply {
    dependencies {
        add("implementation", findLibrary("timber"))
    }
}

configureKotlinAndroid()
configureHiltAndroid()
