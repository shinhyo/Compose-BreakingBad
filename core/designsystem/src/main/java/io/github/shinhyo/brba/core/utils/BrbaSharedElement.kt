package io.github.shinhyo.brba.core.utils

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.ui.Modifier

context(SharedTransitionScope)
fun Modifier.brbaSharedElement(
    isLocalInspectionMode: Boolean,
    animatedVisibilityScope: AnimatedVisibilityScope,
    vararg state: SharedTransitionScope.SharedContentState,
): Modifier {
    if (isLocalInspectionMode) return this
    return state.fold(this) { modifier, contentState ->
        modifier.sharedElement(contentState, animatedVisibilityScope)
    }
}