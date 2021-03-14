package io.github.shinhyo.brba.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.shinhyo.brba.data.Character
import io.github.shinhyo.brba.data.CharactersRepository
import io.github.shinhyo.brba.data.local.AppDatabase
import io.github.shinhyo.brba.data.local.CharacterEntity
import io.github.shinhyo.brba.data.local.updateFavorite
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val db: AppDatabase,
    repository: CharactersRepository
) : ViewModel() {


    private val _character = MutableStateFlow(Character())
    val character get() = _character

    init {
        Timber.i("handle: ${handle.keys()}")
        val id = handle.get<Long>("id") ?: throw Exception()
        Timber.i("handle: $id")

        combine(
            repository.getCharacter(id).map { it.first() },
            db.characterDao().getCharacter(id)
        ) { res: Character, entity: CharacterEntity? ->
            res.copy(favorite = entity?.favorite ?: false)
        }
            .onEach {
                Timber.w("DetailScreen id: $id")
                _character.value = it
            }
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)

//        repository.getCharacter(id)
//            .zip(db.characterDao().getCharacter(id)) { list, list2 ->
//            }
//
//
//        repository.getCharacter(id)
//            .filter { it.isNotEmpty() }
//            .map { it[0] }
//            .zip(db.characterDao().getCharacter(id)) { character, characterEntity ->
//                character.copy(favorite = characterEntity?.favorite?:false)
//            }
//
//
//            .onEach {
//                Timber.w("DetailScreen id: $id")
//                _character.value = it
////                _l.value = it
//            }
//            .catch { }
//            .launchIn(viewModelScope)x

    }

    fun upsertFavorite(character: Character) {
        viewModelScope.launch { updateFavorite(db, character) }
    }

}
