package ds.project.tadaktadakfront.contract.model.dao

import androidx.room.*
import ds.project.tadaktadakfront.contract.model.entity.Contract
import kotlinx.coroutines.flow.Flow

@Dao
interface ContractDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contract: Contract)

    @Delete
    suspend fun delete(contract: Contract)

    @Query("DELETE FROM contract")
    suspend fun deleteAll()

    @Query("SELECT * FROM contract ORDER BY name ASC")
    fun getAll(): Flow<List<Contract>>

}