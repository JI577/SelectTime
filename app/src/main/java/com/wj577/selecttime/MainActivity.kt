package com.wj577.selecttime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var timeDialog:TimeDialogFragment?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShow.setOnClickListener {
            if (timeDialog == null) {
                timeDialog = TimeDialogFragment(30)
            } else {
                timeDialog = TimeDialogFragment(30)
            }
            timeDialog!!.show(supportFragmentManager, "")
        }
    }
}
