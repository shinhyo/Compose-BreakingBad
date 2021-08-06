package io.github.shinhyo.brba.domain.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import java.util.Date

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
    val ctime: Date? = null,
)