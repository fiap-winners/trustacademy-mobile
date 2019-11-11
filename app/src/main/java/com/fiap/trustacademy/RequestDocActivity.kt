package com.fiap.trustacademy

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fiap.trustacademy.model.*
import com.fiap.trustacademy.service.RetrofitFactory
import kotlinx.android.synthetic.main.activity_request_doc.*
import kotlinx.android.synthetic.main.activity_request_doc.btnClose
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception

class RequestDocActivity : AppCompatActivity() {

    val INSTITUTE_ID = 1001L
    val STUDENT_ID = 1001L

    lateinit var deps: List<Department>
    lateinit var crs: List<Course>
    lateinit var doctype: List<DocumentType>

    lateinit var documentType: DocumentType
    lateinit var department: Department
    lateinit var course: Course

    var isLoadDone: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_request_doc)

        progressBar2.visibility = View.VISIBLE

        val docTypeCall = RetrofitFactory().retrofitService().getDocumentTypeByInstitute(INSTITUTE_ID)
        getDocumentTypes(docTypeCall)

        val depCall = RetrofitFactory().retrofitService().getDepartmentsByInstitute(INSTITUTE_ID)
        getDepartments(depCall)

        btnReqDoc.setOnClickListener {

            progressBar2.visibility = View.VISIBLE

            try {
                /* Chamada selfie */
//                val intentSelfie = Intent(this, DocDetailActivity::class.java)
//                startActivity(intentSelfie)

                val fileRef = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "beacon_logo.png")
                val fileCheck = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "beacon_logo.png")
//                val fileCheck = File("/storage/sdcard/Download/", "beacon_logo.png")

                if(fileRef.exists() && fileCheck.exists()) {

                    val document = DocumentToSave(null, null, null, null, null,
                        null, "Request pending", null, null)

                    val requestFileRef = RequestBody.create(MediaType.parse("multipart/form-data"), fileRef)
                    val bodyRef = MultipartBody.Part.createFormData("reference_image", fileRef.name, requestFileRef)

                    val requestFileCheck = RequestBody.create(MediaType.parse("multipart/form-data"), fileCheck)
                    val bodyCheck = MultipartBody.Part.createFormData("reference_image", fileCheck.name, requestFileCheck)

                    val docCall = RetrofitFactory().retrofitService()
                        .setDocument(INSTITUTE_ID, department.id, course.id, STUDENT_ID, documentType.id, document, bodyRef, bodyCheck)
                    setDocument(docCall)

                } else {
                    Toast.makeText(this, getString(R.string.request_fail), Toast.LENGTH_LONG)
                        .show()
                }
            }
            catch (e: Exception) {
                Toast.makeText(this, getString(R.string.request_fail), Toast.LENGTH_LONG)
                .show()

                Log.e("ERROR", e.message ?: "Ocorreu um erro!")
            }
            finally {
                progressBar2.visibility = View.INVISIBLE
            }
        }

        btnRequestDocuments.setOnClickListener{
            finish()
        }

        btnClose.setOnClickListener {
            finishAffinity()
        }
    }

    private fun setDocument(call: Call<Document>) {
        call.enqueue(object: Callback<Document> {
            override fun onResponse(call: Call<Document>, response: Response<Document>) {
                if(response.isSuccessful()) {
                    Toast.makeText(parent, parent.getString(R.string.request_success), Toast.LENGTH_LONG)
                        .show()
                } else {
                    Log.e("ERROR", response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<Document>, t: Throwable) {
                Log.e("Error", t?.message)
                Toast.makeText(parent, getString(R.string.request_fail), Toast.LENGTH_LONG)
                    .show()
                progressBar2.visibility = View.INVISIBLE
            }
        })
    }

    fun getDocumentTypes(call: Call<List<DocumentType>>) {
        call.enqueue(object: Callback<List<DocumentType>> {
            override fun onResponse(call: Call<List<DocumentType>>, response: Response<List<DocumentType>>) {
                response.let {
                    doctype = it.body()!!

                    callDDocumentTypeList(doctype)
                    if (isLoadDone) {
                        progressBar2.visibility = View.INVISIBLE
                    }
                    isLoadDone = true
                }
            }

            override fun onFailure(call: Call<List<DocumentType>>, t: Throwable) {
                Log.e("Error", t?.message)
                progressBar2.visibility = View.INVISIBLE
            }
        })
    }

    fun getDepartments(call: Call<List<Department>>) {
        call.enqueue(object: Callback<List<Department>> {
            override fun onResponse(call: Call<List<Department>>, response: Response<List<Department>>) {
                response.let {
                    deps = it.body()!!

                    callDepartmentsList(deps)
                    if (isLoadDone) {
                        progressBar2.visibility = View.INVISIBLE
                    }
                    isLoadDone = true
                }
            }

            override fun onFailure(call: Call<List<Department>>, t: Throwable) {
                Log.e("Error", t?.message)
                progressBar2.visibility = View.INVISIBLE
            }
        })
    }

    fun getCourses(call: Call<List<Course>>) {
        call.enqueue(object: Callback<List<Course>> {
            override fun onResponse(call: Call<List<Course>>, response: Response<List<Course>>) {
                response.let {
                    crs = it.body()!!

                    callCoursesList(crs)
                    progressBar2.visibility = View.INVISIBLE
                }
            }

            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                Log.e("Error", t?.message)
                progressBar2.visibility = View.INVISIBLE
            }
        })
    }

    private fun callDDocumentTypeList(doctype: List<DocumentType>) {

        val adapter = ArrayAdapter<DocumentType>(this, android.R.layout.simple_dropdown_item_1line, doctype)
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spnDocType.adapter = adapter
        spnDocType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){

                documentType = parent.getItemAtPosition(position) as DocumentType

            }

            override fun onNothingSelected(parent: AdapterView<*>){
            }
        }
    }

    private fun callDepartmentsList(deps: List<Department>) {

        val adapter = ArrayAdapter<Department>(this, android.R.layout.simple_dropdown_item_1line, deps)
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spnDepartments.adapter = adapter
        spnDepartments.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){

                department = parent.getItemAtPosition(position) as Department

                progressBar2.visibility = View.VISIBLE
                val call = RetrofitFactory().retrofitService().getCourseByDepartment(INSTITUTE_ID, department.id)

                getCourses(call)
            }

                override fun onNothingSelected(parent: AdapterView<*>){
            }
        }
    }

    private fun callCoursesList(crs: List<Course>) {

        val adapter = ArrayAdapter<Course>(this, android.R.layout.simple_dropdown_item_1line, crs)
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spnCourses.adapter = adapter
        spnCourses.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){

                course = parent.getItemAtPosition(position) as Course
                btnReqDoc.isEnabled = true
            }

            override fun onNothingSelected(parent: AdapterView<*>){
            }
        }
    }
}