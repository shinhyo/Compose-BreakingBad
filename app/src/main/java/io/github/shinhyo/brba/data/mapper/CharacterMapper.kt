package io.github.shinhyo.brba.data.mapper

import io.github.shinhyo.brba.data.local.model.CharacterEntity
import io.github.shinhyo.brba.data.remote.model.CharacterResponse
import io.github.shinhyo.brba.domain.model.Character

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

fun CharacterEntity.toCharacter() = Character(
    charId = charId,
    name = name,
    img = img,
    nickname = nickname,
    favorite = favorite,
    ctime = ctime
)

fun Character.toCharacterEntity() = CharacterEntity(
    charId = charId,
    name = name,
    img = img,
    nickname = nickname
)