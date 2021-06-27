package com.cmi.data.local.database.dao

import androidx.room.*
import com.cmi.data.local.database.entity.PictogramEntity

@Dao
interface PictogramDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictogram(pictogramEntity: PictogramEntity)

    @Query("SELECT * FROM pictogram ORDER BY priority DESC")
    suspend fun getPictograms(): List<PictogramEntity>

    @Query("SELECT * FROM pictogram WHERE pictogramId=:pictogramId")
    suspend fun getPictogram(pictogramId: Int): PictogramEntity

    @Query("SELECT * FROM pictogram WHERE categoryId=:categoryId ORDER BY priority DESC")
    suspend fun getPictogramsByCategory(categoryId: Int): List<PictogramEntity>

    @Query("UPDATE pictogram SET priority=:newPriority WHERE pictogramId=:pictogramId")
    suspend fun updatePictogramPriority(pictogramId: Int, newPriority: Int)

    @Update
    suspend fun updatePictogram(pictogramEntity: PictogramEntity)

    @Update
    suspend fun updatePictograms(pictogramsEntities: List<PictogramEntity>)

    @Delete
    suspend fun delete(pictogramEntity: PictogramEntity)
}