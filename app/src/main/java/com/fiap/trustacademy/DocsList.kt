package com.fiap.trustacademy

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fiap.trustacademy.model.*
import com.fiap.trustacademy.service.RetrofitFactory
import kotlinx.android.synthetic.main.activity_doc_detail.*
import kotlinx.android.synthetic.main.activity_docs_list.*
import kotlinx.android.synthetic.main.activity_docs_list.btnAccount
import kotlinx.android.synthetic.main.activity_docs_list.btnClose
import kotlinx.android.synthetic.main.activity_docs_list.btnDocuments
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class DocsList : AppCompatActivity(), OnItemClickListener{
    private lateinit var adapter: RecyclerAdapter

    val INSTITUTE_ID = 1001L
    val STUDENT_ID = 1001L

    var docs: List<Document>? = null

    override fun onItemClicked(card: Card) {

        val docVersionsDate: ArrayList<String> = ArrayList()
        val docVersionsTime: ArrayList<String> = ArrayList()
        val docVersionsContent: ArrayList<String> = ArrayList()
        val docsSelected: Iterator<Document> = selectDocsFromCard(card).iterator()

        docsSelected.forEach {
            docVersionsDate.add(DateFormat.getDateFormat(this).format(it.modifiedAt))
            docVersionsTime.add(DateFormat.getTimeFormat(this).format(it.modifiedAt))
            docVersionsContent.add(it.content)
        }

        val intentDocDetail = Intent(this, DocDetailActivity::class.java)
        intentDocDetail.putExtra("SELECTED_CARD", card)
        intentDocDetail.putExtra("SELECTED_DOCSDATE", docVersionsDate)
        intentDocDetail.putExtra("SELECTED_DOCSTIME", docVersionsTime)
        intentDocDetail.putExtra("SELECTED_DOCSCONTENT", docVersionsContent)
        startActivity(intentDocDetail)
    }

    private fun selectDocsFromCard(card: Card):  List<Document> {
        return docs!!.filter {it.department.name + " : " + it.course.name == card.courseName && it.type.name == card.documentTypeName}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_docs_list)

        progressBar.visibility = View.VISIBLE

        val call = RetrofitFactory().retrofitService().getDocumentsByStudent(INSTITUTE_ID, STUDENT_ID)

        getDocuments(call)

        btnClose.setOnClickListener {
            finishAffinity()
        }

        btnAccount.setOnClickListener {
            Toast.makeText(this, this.getString(R.string.not_implemented), Toast.LENGTH_SHORT)
                .show()
        }

        btnDocuments.setOnClickListener {
            val call = RetrofitFactory().retrofitService().getDocumentsByInstitute(INSTITUTE_ID)
            getDocuments(call)
        }
    }

    fun getDocuments(call: Call<List<Document>>) {
        call.enqueue(object: Callback<List<Document>> {
            override fun onResponse(call: Call<List<Document>>, response: Response<List<Document>>) {
                response.let {
                    docs = it.body()!!

                    callDocListView(docs!!)
                    progressBar.visibility = View.INVISIBLE
                }
            }

            override fun onFailure(call: Call<List<Document>>, t: Throwable) {
                Log.e("Error", t?.message)
                progressBar.visibility = View.INVISIBLE
            }
        })

    }

    fun callDocListView(documents: List<Document>) {

        var docIterator = documents.sortedWith(compareBy({it.institute.id}, {it.department.id}, {it.course.id}, {it.type.id})).toMutableList().iterator()
        var cards: MutableList<Card> = mutableListOf<Card>()

        var instituteId = 0L
        var departmentId = 0L
        var courseId = 0L
        var typeId = 0L

        var courseName = ""
        var typeName = ""
        var lastUpdatedDate = Date()
        var versionsQty = 1
        var documentStatus = ""

        var document = docIterator.next()

        instituteId = document.institute.id
        departmentId = document.department.id
        courseId = document.course.id
        typeId = document.type.id

        courseName = document.department.name + " : " + document.course.name
        typeName = document.type.name
        lastUpdatedDate = if(document.modifiedAt != null) document.modifiedAt!! else document.createAt
//        documentStatus = document.status
        documentStatus = getString(R.string.document_pending)

        while (docIterator.hasNext()) {

            document = docIterator.next()

            if(document.institute.id != instituteId || document.department.id != departmentId || document.course.id != courseId || document.type.id != typeId) {

                cards.add(
                    Card(
                        this.getString(R.string.last_update) + " " + DateFormat.getDateFormat(this).format(lastUpdatedDate),
                        versionsQty.toString() + " " + if (versionsQty > 1) this.getString(R.string.versions) else this.getString(R.string.version),
                        typeName,
                        courseName,
                        documentStatus
                    )
                )

                instituteId = document.institute.id
                departmentId = document.department.id
                courseId = document.course.id
                typeId = document.type.id

                courseName = document.department.name + " : " + document.course.name
                typeName = document.type.name

                versionsQty = 1

            } else {
                lastUpdatedDate = if(document.modifiedAt != null) document.modifiedAt!! else document.createAt
                versionsQty++
            }
        }
        if(docIterator.hasNext()) {
            document = docIterator.next()
        }

        cards.add(
            Card(
                this.getString(R.string.last_update) + " " + DateFormat.getDateFormat(this).format(lastUpdatedDate),
                versionsQty.toString() + " " + if (versionsQty > 1) this.getString(R.string.versions) else this.getString(R.string.version),
                typeName,
                courseName,
                documentStatus
            )
        )

        val docsList = findViewById<RecyclerView>(R.id.docsList)
        docsList.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerAdapter(cards,this)
        docsList.adapter = adapter
    }

}