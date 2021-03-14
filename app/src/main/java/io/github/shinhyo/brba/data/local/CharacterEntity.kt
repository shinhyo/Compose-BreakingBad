package io.github.shinhyo.brba.data.local

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import io.github.shinhyo.brba.data.Character
import io.github.shinhyo.brba.data.local.CharacterEntity.Companion.TABLE_NAME
import java.util.*

@Immutable
@Entity(tableName = TABLE_NAME, indices = [(Index(value = ["charId"], unique = true))])
data class CharacterEntity(
    @PrimaryKey
    val charId: Long,
    val name: String,
    val img: String = "",
    val nickname: String,
    val favorite: Boolean = false,
    var ctime: Date = Date(),
) {
    companion object {
        const val TABLE_NAME = "character"
    }
}

suspend fun updateFavorite(appDatabase: AppDatabase, character: Character) {
    appDatabase.characterDao()
        .insert(character.toCharacterEntity().copy(favorite = !character.favorite))
}

private fun Character.toCharacterEntity() = CharacterEntity(
    charId = charId,
    name = name,
    img = img,
    nickname = nickname
)