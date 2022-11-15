package ds.project.tadaktadakfront.contract.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ds.project.tadaktadakfront.contract.model.entity.Contract
import ds.project.tadaktadakfront.contract.model.dao.ContractDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Contract::class], version = 1)
abstract class ContractDatabase: RoomDatabase() {
    abstract fun contractDao(): ContractDAO

    private class ContractDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var contractDao = database.contractDao()
                    contractDao.deleteAll()
                }
            }
        }
    }

    companion object {
        private var INSTANCE: ContractDatabase? = null

        // 여러 스레드가 접근하지 못하도록 synchronized로 설정
        fun getDatabase(context: Context, scope: CoroutineScope): ContractDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContractDatabase::class.java,
                    "contract"
                ).addCallback(ContractDatabaseCallback(scope)) // build 전 콜백 추가
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}