package com.github.piasy.template.ui.search

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.github.piasy.template.R
import kotlinx.android.synthetic.activity_demo_kotlin.mTvMessage;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/24.
 */
public class DemoKotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_kotlin)
        mTvMessage.setText("Hello Kotlin!")
    }
}