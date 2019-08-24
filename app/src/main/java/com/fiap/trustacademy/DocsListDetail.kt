package com.fiap.trustacademy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fiap.trustacademy.model.Card
import kotlinx.android.synthetic.main.activity_docs_list_detail.view.*

class DocsListItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val docLastDate = itemView.docLastDate
    val docVersionsQty = itemView.docVersionsQty
    val docName = itemView.docName
    val docCourse = itemView.docCourse

    fun bind(doc: Card, clickListener: OnItemClickListener)
    {
        docLastDate.text = doc.lastUpdatedDate
        docVersionsQty.text = doc.documentVersionQty
        docName.text = doc.documentTypeName
        docCourse.text = doc.courseName

        itemView.setOnClickListener {
            clickListener.onItemClicked(doc)
        }
    }
}

class RecyclerAdapter(var docs:MutableList<Card>, val itemClickListener: OnItemClickListener):RecyclerView.Adapter<DocsListItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DocsListItem {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_docs_list_detail,parent, false)
        return DocsListItem(view)
    }

    override fun getItemCount(): Int {
        return docs.size
    }

    override fun onBindViewHolder(myHolder: DocsListItem, position: Int) {
        val doc = docs.get(position)
        myHolder.bind(doc, itemClickListener)
    }
}

interface OnItemClickListener{
    fun onItemClicked(doc: Card)
}