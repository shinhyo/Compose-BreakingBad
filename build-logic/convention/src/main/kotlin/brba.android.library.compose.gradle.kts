import io.github.shinhyo.brba.buildlogic.configureAndroidCompose

with(pluginManager) {
    apply("brba.android.library")
}

configureAndroidCompose()