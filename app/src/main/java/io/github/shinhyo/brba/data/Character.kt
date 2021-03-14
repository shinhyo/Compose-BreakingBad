package io.github.shinhyo.brba.data

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import io.github.shinhyo.brba.data.local.CharacterEntity
import java.util.*


@Immutable
data class CharacterResponse(
    @SerializedName("char_id")
    val charId: Long = 0,
    val name: String = "",
    val birthday: String = "",
    val occupation: List<String> = listOf(),
    val img: String = "",
    val status: String = "",
    val nickname: String = "",
    val appearance: List<Int> = listOf(),
    val portrayed: String = "",
    val category: String = "",
    @SerializedName("better_call_saul_appearance")
    val betterCallSaulAppearance: List<Int> = listOf(),
)

fun CharacterResponse.toCharacter() = Character(
    charId = charId,
    name = name,
    birthday = birthday,
    occupation = occupation,
    img = img,
    status = status,
    nickname = nickname,
    appearance = appearance,
    portrayed = portrayed,
    category = category,
    betterCallSaulAppearance = betterCallSaulAppearance
)


@Immutable
data class Character(
    @SerializedName("char_id")
    val charId: Long = -1,
    val name: String = "",
    val birthday: String = "",
    val occupation: List<String> = listOf(),
    val img: String = "",
    val status: String = "",
    val nickname: String = "",
    val appearance: List<Int> = listOf(),
    val portrayed: String = "",
    val category: String = "",
    @SerializedName("better_call_saul_appearance")
    val betterCallSaulAppearance: List<Int> = listOf(),
    val ratio: Float = 1f,
    val favorite: Boolean = false,
    var ctime: Date? = null,
)


fun CharacterEntity.toCharacter() = Character(
    charId = charId,
    name = name,
    img = img,
    nickname = nickname,
    favorite = favorite,
    ctime = ctime
)


