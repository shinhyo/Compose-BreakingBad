/*
 * Copyright 2021 shinhyo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.shinhyo.brba.app.inject

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.shinhyo.brba.domain.repository.CharactersRepository
import io.github.shinhyo.brba.domain.usecase.AddFavoriteToListUseCase
import io.github.shinhyo.brba.domain.usecase.GetCharacterListUseCase
import io.github.shinhyo.brba.domain.usecase.GetCharacterUseCase
import io.github.shinhyo.brba.domain.usecase.GetFavoriteListUseCase
import io.github.shinhyo.brba.domain.usecase.UpdateFavoriteUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetCharacterListUseCase(repo: CharactersRepository) = GetCharacterListUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideGetFavoriteListUseCase(repo: CharactersRepository) = GetFavoriteListUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideGetCharacterUseCase(repo: CharactersRepository) = GetCharacterUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideAddFavoriteToListUseCase(repo: CharactersRepository) = AddFavoriteToListUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideUpdateFavoriteUseCase(repo: CharactersRepository) = UpdateFavoriteUseCase(repo)
}
