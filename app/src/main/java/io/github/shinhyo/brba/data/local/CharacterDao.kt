package io.github.shinhyo.brba.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CharacterEntity)

    @Query("SELECT * FROM ${CharacterEntity.TABLE_NAME}")
    fun getAll(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM ${CharacterEntity.TABLE_NAME} t WHERE t.charId=:charId ORDER BY t.ctime")
    fun getCharacter(charId: Long): Flow<CharacterEntity?>

    @Query(
        "SELECT * FROM ${CharacterEntity.TABLE_NAME} t WHERE t.favorite = 1 ORDER BY " +
                "CASE WHEN :isAsc = 1 THEN t.ctime END ASC," +
                "CASE WHEN :isAsc = 0 THEN t.ctime END DESC"
    )
    fun getFavorite(isAsc: Boolean = true): Flow<List<CharacterEntity>>

    @Delete
    suspend fun delete(entity: CharacterEntity): Int
}