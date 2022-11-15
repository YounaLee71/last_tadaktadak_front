package ds.project.tadaktadakfront.contract.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contract")
data class Contract (
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "number") var number: String,
    @ColumnInfo(name = "address") var address: String ) {
    constructor(): this(null, "", "", "")
}
