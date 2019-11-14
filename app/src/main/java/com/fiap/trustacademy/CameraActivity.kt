package com.fiap.trustacademy

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.io.FileOutputStream

class CameraActivity: Activity() {

    lateinit private var pictureType: String
    lateinit private var img: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        pictureType = intent.getStringExtra("PICTURE TYPE")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            val bundle = data.extras
            if (bundle != null) {
                img = bundle.get("data") as Bitmap

                val iv = findViewById(R.id.imageView1) as ImageView
                iv.setImageBitmap(img)
            }
        }
    }

    fun tirarFoto(view: View) {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        startActivityForResult(intent, 0)
    }

    fun savePicture(view: View) {

        if(::img.isInitialized) {
            try {
                File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), if(pictureType == "acc") "account.png" else "request.png")
                    .writeBitmap(img, Bitmap.CompressFormat.PNG, 85)

                val intentParent = intent
                intentParent.putExtra("RESULT", "OK")
                setResult(RESULT_OK, intentParent)
                finish()

            } catch (e: Exception) {
                Toast.makeText(this, getString(R.string.request_fail), Toast.LENGTH_LONG)
                    .show()

                Log.e("ERROR", e.message ?: "Erro ao capturar imagem")
            }
        }
    }

    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }
}