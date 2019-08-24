package com.fiap.trustacademy.service

import com.fiap.trustacademy.model.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

fun createDocumentsList (): List<Document> {

    val institute = Institute(1001, "Faculdade de Informática e Administração Paulista", "FIAP")
    val department = Department(1001, "FIAP ON")
    var documentType = DocumentType(1001, "Histórico Escolar")
    val course = Course(1001, "Análise e Desenvolvimento de Sistemas")
    var student = Student(1001, "Henrique Lopes")

    var dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val data = dateFormat.parse("05/07/2019")

    var documentArray =
            listOf( Document(1001, student, institute, department, course, documentType, "Conteúdo v1", data, null),
                    Document(1002, student, institute, department, course, documentType, "Conteúdo v2", data,null),
                    Document(1003, student, institute, department, course, documentType, "Conteúdo v3", data,null),
                    Document(1004, student, institute, department, course, documentType, "Conteúdo v4", data,null),
                    Document(1005, student, institute, department, course, documentType, "Conteúdo v5", data,null))
/*
    documentType = DocumentType(1002, "Comprovante de Matrícula")
    documentArray.add(Document(1006, student, institute, department, course, documentType, "Conteúdo v1", data,null))
    documentArray.add(Document(1007, student, institute, department, course, documentType, "Conteúdo v2", data,null))
*/
    return documentArray
}