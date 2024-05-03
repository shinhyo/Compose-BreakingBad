import com.android.build.api.dsl.ApplicationExtension
import io.github.shinhyo.brba.buildlogic.configureAndroidCompose
import io.github.shinhyo.brba.buildlogic.configureHiltAndroid
import io.github.shinhyo.brba.buildlogic.configureKotlinAndroid
import io.github.shinhyo.brba.buildlogic.findVersion

with(pluginManager) {
    apply("com.android.application")
}

extensions.configure<ApplicationExtension> {
    defaultConfig.targetSdk = findVersion("targetSdkVer").toString().toInt()
}

configureKotlinAndroid()
configureAndroidCompose()
configureHiltAndroid()
