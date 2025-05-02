package com.example.week89

import android.app.Activity
import android.app.Instrumentation
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var myAdapter: MyAdapter
    private val context=ArrayList<Context>()

    private val startForResult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->if(result.resultCode==Activity.RESULT_OK){
            //取得回传
            val intent=result.data
        val course=intent?.getStringExtra("course")?:""
        //新增
        context.add(Context(course))
        myAdapter.notifyDataSetChanged()
    }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}