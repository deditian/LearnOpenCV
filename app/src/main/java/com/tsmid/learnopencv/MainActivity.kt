package com.tsmid.learnopencv

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc


class MainActivity : AppCompatActivity()
    , CameraBridgeViewBase.CvCameraViewListener2
{
    private val TAG = "MainActivity"
    private lateinit var mOpenCvCameraView: CameraBridgeViewBase
    var mRgba: Mat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        if (OpenCVLoader.initDebug()){
            Log.d(TAG, "onCreate: OpenCV Successfully")
        }else{
            Log.d(TAG, "onCreate: OpenCV FAILED")
        }
        mOpenCvCameraView = findViewById(R.id.tutorial1_activity_java_surface_view)
        mOpenCvCameraView.setCvCameraViewListener(this)



    }

    private var mLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                LoaderCallbackInterface.SUCCESS -> {
                    Log.i(TAG, "OpenCV loaded successfully")
                    System.loadLibrary("native-lib")
                    mOpenCvCameraView.enableView()
                    Toast.makeText(mAppContext, "Success",
                        Toast.LENGTH_SHORT).show()

                }
                else -> {
                    Toast.makeText(mAppContext, "Failure",
                        Toast.LENGTH_SHORT).show()
                    super.onManagerConnected(status)
                }
            }
        }
    }




    override fun onResume() {
        super.onResume()
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback)
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!")
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
            mOpenCvCameraView.disableView()
    }

    override fun onPause() {
        super.onPause()
            mOpenCvCameraView.disableView()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        mRgba = Mat(height, width, CvType.CV_8UC4)

    }

    override fun onCameraViewStopped() {
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        mRgba = inputFrame!!.rgba()
        NativeClass.testFunction(mRgba!!.nativeObjAddr)
        return this.mRgba!!
    }
}
