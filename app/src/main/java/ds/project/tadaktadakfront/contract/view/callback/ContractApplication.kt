package ds.project.tadaktadakfront.contract.view.callback

import android.app.Application
import ds.project.tadaktadakfront.contract.model.ContractDatabase
import ds.project.tadaktadakfront.contract.repository.ContractRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ContractApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ContractDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ContractRepository(database!!.contractDao()) }
}