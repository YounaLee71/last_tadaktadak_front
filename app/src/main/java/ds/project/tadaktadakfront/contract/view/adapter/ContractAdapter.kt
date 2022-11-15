package ds.project.tadaktadakfront.contract.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ds.project.tadaktadakfront.R
import ds.project.tadaktadakfront.contract.model.entity.Contract

class ContractAdapter : ListAdapter<Contract, ContractAdapter.ContractViewHolder>(ContractComparator()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContractViewHolder {
        return ContractViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ContractViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ContractViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val nameTextView: TextView = itemView.findViewById(R.id.item_tv_name)
        private val numberTextView: TextView = itemView.findViewById(R.id.item_tv_number)
        private val addressTextView: TextView = itemView.findViewById(R.id.item_tv_address)

        fun bind(contract: Contract){
            nameTextView.text =contract.name
            numberTextView.text=contract.number
            addressTextView.text=contract.address
        }

        companion object{
            fun create(parent: ViewGroup): ContractViewHolder {
                val view: View =LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ContractViewHolder(view)
            }
        }
    }

    class ContractComparator : DiffUtil.ItemCallback<Contract>(){
        override fun areItemsTheSame(oldItem: Contract, newItem: Contract): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Contract, newItem: Contract): Boolean {
            return oldItem.number == newItem.number
        }
    }
}