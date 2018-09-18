package cn.sensorsdata.demo.yang

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import cn.sensorsdata.demo.R
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_kotlin.*
@Route(path = "/kotlin/activity")
class KotlinActivity : AppCompatActivity() , View.OnClickListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)


        btn_kotlin_1.setOnClickListener {
            Toast.makeText(this,"点击了1", Toast.LENGTH_SHORT).show();
        }


        btn_kotlin_2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        Toast.makeText(this,"点击了2", Toast.LENGTH_SHORT).show();
    }
}
