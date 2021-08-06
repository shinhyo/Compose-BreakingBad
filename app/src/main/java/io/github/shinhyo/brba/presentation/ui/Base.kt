package io.github.shinhyo.brba.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.shinhyo.brba.domain.model.Character
import io.github.shinhyo.brba.domain.usecase.UpdateFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn

open class BaseViewModel : ViewModel()

open class BaseFavoriteViewModel(private val updateFavoriteUseCase: UpdateFavoriteUseCase) :
    BaseViewModel() {

    fun upsertFavorite(character: Character) {
        updateFavoriteUseCase.execute(character)
            .flowOn(Dispatchers.IO)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }
}