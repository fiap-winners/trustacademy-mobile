package com.fiap.trustacademy

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class MainActivity : Activity() {

    private var button2: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun tirarFoto(view: View) {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            val bundle = data.extras
            if (bundle != null) {
                val img = bundle.get("data") as Bitmap?

                val iv = findViewById(R.id.imageView1) as ImageView
                iv.setImageBitmap(img)
            }
        }
    }

    fun Enviar (){
        val intent = Intent(this, DocsList::class.java)
        startActivity(intent)
    }
}
