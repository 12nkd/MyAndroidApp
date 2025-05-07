package com.example.week10

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var btnCalculate:Button
    private lateinit var edHeight:EditText
    private lateinit var edWeight:EditText
    private lateinit var edAge:EditText
    private lateinit var tvWeightResult: TextView
    private lateinit var tvFatResult: TextView
    private lateinit var tvBmiResult:TextView
    private lateinit var tvProgress:TextView
    private  lateinit var progressBar: ProgressBar
    private lateinit var  llProgressBar: LinearLayout
    private lateinit var btnBoy:RadioButton
    private lateinit var btnGirl:RadioButton

    private fun showToast(msg:String)=Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    private fun runThread(){
        tvWeightResult.text="标准体重\n无"
        tvFatResult.text="体脂肪\n无"
        tvBmiResult.text="BMI\n无"
        progressBar.progress=0
        tvProgress.text="0%"
        llProgressBar.visibility= View.VISIBLE
        Thread{
            var progress=0
            while(progress<100){
                try{
                    Thread.sleep(50)
                }catch(ignored:InterruptedException){}
                progress++
                runOnUiThread{
                    progressBar.progress=progress
                    tvProgress.text="$progress%"
                }
            }
            val height=edHeight.text.toString().toDouble()
            val weight=edWeight.text.toString().toDouble()
            val age=edAge.text.toString().toDouble()
            val bmi=weight/((height/100).pow(2))
            val(standWeight,bodyFat)=if(btnBoy.isChecked){
                Pair((height-80)*0.7,1.39*bmi+0.16*age-19.34)
            }else{
                Pair((height-70)*0.6,1.39*bmi+0.16*age-9)
            }
            runOnUiThread {
                llProgressBar.visibility=View.GONE
                tvWeightResult.text="标准体重\n${String.format("%.2f",standWeight)}"
                tvFatResult.text="体脂肪\n${String.format("%.2f",bmi)}"
                tvBmiResult.text="BMI\n${String.format("%.2f",bmi)}"
            }
        }.start()
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
        btnCalculate=findViewById(R.id.btnCalculate)
        edHeight=findViewById(R.id.edHeight)
        edWeight=findViewById(R.id.edWeight)
        edAge=findViewById(R.id.edAge)
        tvFatResult=findViewById(R.id.tvFatResult)
        tvBmiResult=findViewById(R.id.tvBmiResult)
        tvWeightResult=findViewById(R.id.tvWeightResult)
        tvProgress=findViewById(R.id.tvProgress)
        llProgressBar=findViewById(R.id.progressBar)
        progressBar=findViewById(R.id.tvProgress)
        btnBoy=findViewById(R.id.btnBoy)
        btnGirl=findViewById(R.id.btnGirl)


        btnCalculate.setOnClickListener {
            when{
                edHeight.text.isEmpty()->showToast("请输入身高")
                edWeight.text.isEmpty()->showToast("请输入体重")
                edAge.text.isEmpty()->showToast("请输入年龄")
                else->runThread()
            }
        }
    }
}