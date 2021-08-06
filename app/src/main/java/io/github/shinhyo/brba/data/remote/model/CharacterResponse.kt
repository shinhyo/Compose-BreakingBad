package io.github.shinhyo.brba.data.remote.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

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