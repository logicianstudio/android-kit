package com.android.kit.ui.recyclerview

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.android.kit.listener.ItemClickListener

/**
 * A generic RecyclerView adapter
 *
 * @param <T> Type of the items in the list
 * @param <V> The type of the ViewBinding
</V></T> */
abstract class DataBoundRecyclerAdapter<T, V : ViewBinding>() :
    RecyclerView.Adapter<DataBoundViewHolder<V>>() {

    private var simpleItemClickListener: ItemClickListener<T>? = null
    open var data: MutableList<T> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun updateItem(position: Int, item: T) {
        if (position > 0 && position < data.size) {
            data[position] = item
            notifyItemChanged(position)
        }
    }

    fun appendData(data: List<T>) {
        this.data.addAll(data)
        notifyItemInserted(data.size - 1)
    }

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    protected var recyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        var binding = createBinding(parent)
        if (binding == null) {
            binding = createBinding(parent, viewType)
        }
        if (binding == null) throw InstantiationException("Please override createBinding(parent: ViewGroup) OR createBinding(parent: ViewGroup, viewType: Int)")
        return DataBoundViewHolder(binding)
    }

    protected open fun createBinding(parent: ViewGroup): V? {
        return null
    }

    protected open fun createBinding(parent: ViewGroup, viewType: Int): V? {
        return null
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        val item = data[position]
        holder.binding.root.setOnClickListener {
            simpleItemClickListener?.invoke(item, position, it)
        }
        bind(holder.binding, item, position)
        (holder.binding as? ViewDataBinding)?.executePendingBindings()
    }

    protected abstract fun bind(binding: V, item: T, position: Int)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }

    fun getItem(position: Int) = data[position]

    fun notifyItemDataChanged(item: T) {
        val index = data.indexOf(item)
        if (index >= 0) {
            notifyItemChanged(index)
        } else {
            notifyDataSetChanged()
        }
    }

    fun notifyItemRemoved(item: T) {
        val index = data.indexOf(item)
        data.remove(item)
        notifyItemRemoved(index)
    }

    fun setOnItemClickListener(event: ItemClickListener<T>) {
        simpleItemClickListener = event
    }

    protected fun smoothScrollToPosition(position: Int, record: Boolean = false) {

//        val p = if (recyclerView is PickerRecyclerView){
//            (recyclerView as PickerRecyclerView).selectedPosition
//        } else{
//            -1
//        }
        recyclerView?.smoothScrollToPosition(position)
//        if (record && p > -1) {
//            ActionManager.record {
//                smoothScrollToPosition(p, record)
//            }
//        }
    }
}