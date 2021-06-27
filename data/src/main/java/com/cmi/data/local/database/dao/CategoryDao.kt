package com.cmi.data.local.database.dao

import androidx.room.*
import com.cmi.data.local.database.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category ORDER BY priority DESC")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("UPDATE category SET priority=:newPriority WHERE categoryId=:categoryId")
    suspend fun updateCategoryPriority(categoryId: Int, newPriority: Int)

    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity)

    @Update
    suspend fun updateCategories(categoriesEntities: List<CategoryEntity>)

    @Delete
    suspend fun delete(categoryEntity: CategoryEntity)
}