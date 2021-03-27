package io.github.shinhyo.brba.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import io.github.shinhyo.brba.ui.theme.BrBaComposeTheme
import io.github.shinhyo.brba.utils.LocalBackDispatcher

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ComposeApp {
                CompositionLocalProvider(LocalBackDispatcher.provides(onBackPressedDispatcher)) {
                    ProvideWindowInsets {
                        NavGraph()
                    }
                }
            }
        }
    }

}

@Composable
fun ComposeApp(content: @Composable () -> Unit) {
    BrBaComposeTheme {
        content()
    }
}

