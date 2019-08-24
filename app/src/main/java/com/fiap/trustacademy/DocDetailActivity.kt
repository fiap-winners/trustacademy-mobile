package com.fiap.trustacademy

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.fiap.trustacademy.model.Card
import kotlinx.android.synthetic.main.activity_doc_detail.*
import androidx.constraintlayout.widget.ConstraintSet

class DocDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_doc_detail)

        btnDocuments.setOnClickListener {
            finish()
        }

        btnAccount.setOnClickListener {
            Toast.makeText(this, this.getString(R.string.not_implemented), Toast.LENGTH_SHORT)
            .show()
        }

        btnClose.setOnClickListener {
            finishAffinity()
        }

        val card: Card = intent.getParcelableExtra("SELECTED_CARD")
        val docDate: ArrayList<String> = intent.getStringArrayListExtra("SELECTED_DOCSDATE")
        val docTime: ArrayList<String> = intent.getStringArrayListExtra("SELECTED_DOCSTIME")
        val docContent: ArrayList<String> = intent.getStringArrayListExtra("SELECTED_DOCSCONTENT")

        docLastDate.text = card.lastUpdatedDate
        docVersionsQty.text = card.documentVersionQty
        docName.text = card.documentTypeName
        docCourse.text = card.courseName

        var contentIndex: Int = 0

        for(version in docDate) {

            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.VERTICAL

            val textView1 = TextView(this)
            textView1.text = docDate.get(contentIndex) + " " + this.getString(R.string.at_time) + " " + docTime.get(contentIndex)
            val textView2 = TextView(this)
            textView2.text = docContent.get(contentIndex++)
            textView2.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD)

            val cardView = CardView(this)

            cardView.radius = 10F
            cardView.setContentPadding(8,8,8,8)
            cardView.setCardBackgroundColor(Color.WHITE)
            cardView.cardElevation = 30F
            cardView.maxCardElevation = 30F
            cardView.setContentPadding(15,10,15,10)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // CardView width
                LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
            )
            layoutParams.setMargins(18, 18, 18, 18)
            cardView.layoutParams = layoutParams
            cardView.id = View.generateViewId()

            linearLayout.addView(textView1)
            linearLayout.addView(textView2)
            cardView.addView(linearLayout)
            VersionsList.addView(cardView)

            val constraintSet = ConstraintSet()
            constraintSet.clone(VersionsList)

            if(contentIndex == 1){
                constraintSet.connect(cardView.id, ConstraintSet.TOP, VersionsHeader.id, ConstraintSet.BOTTOM, 18)
            } else {
                constraintSet.connect(cardView.id, ConstraintSet.TOP, VersionsList.getChildAt(contentIndex+1).id, ConstraintSet.BOTTOM, 38)
            }
            constraintSet.connect(cardView.id, ConstraintSet.LEFT, VersionsList.id, ConstraintSet.RIGHT, 20)
            constraintSet.connect(cardView.id, ConstraintSet.RIGHT, VersionsList.id, ConstraintSet.LEFT , 20)

            constraintSet.applyTo(VersionsList)

        }
    }
}