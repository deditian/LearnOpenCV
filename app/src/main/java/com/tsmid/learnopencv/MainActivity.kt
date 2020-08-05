package com.tsmid.learnopencv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.opencv.android.OpenCVLoader

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (OpenCVLoader.initDebug()){
            Log.d(TAG, "onCreate: OpenCV Successfully")
        }else{
            Log.d(TAG, "onCreate: OpenCV FAILED")
        }
    }
}
