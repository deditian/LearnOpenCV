package com.tsmid.learnopencv

import android.R.attr.src
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.datamatrix.DataMatrixReader
import com.google.zxing.multi.qrcode.QRCodeMultiReader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.show_photo.view.*
import org.opencv.android.*
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc


class MainActivity : AppCompatActivity()
    , CameraBridgeViewBase.CvCameraViewListener2
{
    private val TAG = "MainActivity"
    private lateinit var mOpenCvCameraView: CameraBridgeViewBase
    var mRgba: Mat? = null

    val mCamera : android.hardware.Camera? = null



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
        mRgba = inputFrame!!.gray()

//        var ker =  Mat.zeros(3,3,CvType.CV_8SC1)
//        Log.i(TAG, "onCameraFrame: A ${ker.dump()}")
//        ker.rowRange(1, 2).setTo(Scalar(-1.0))
//        ker.colRange(1, 2).setTo(Scalar(-1.0))
//        ker.put(1 ,1,5.0)

        val ker = Mat(3, 3, CvType.CV_16SC1)
        ker.put(0, 0, 0.0, -1.0, 0.0, -1.0, 5.0, -1.0, 0.0, -1.0, 0.0)


//        Log.i(TAG, "onCameraFrame: B  ${kernel.dump()}")
        Imgproc.filter2D(mRgba,mRgba, mRgba!!.depth(),ker)


//        Imgproc.cvtColor(mRgba, mRgba, Imgproc.COLOR_BGR2GRAY)
//        Imgproc.GaussianBlur(mRgba, mRgba, Size(3.0, 3.0), 0.0)
//        Imgproc.threshold(mRgba, mRgba, 0.0, 255.0, Imgproc.THRESH_OTSU)
//        val kernelDilate =
//            Imgproc.getStructuringElement(Imgproc.MORPH_RECT, Size(3.0, 3.0))
//        Imgproc.dilate(mRgba, mRgba, kernelDilate)

//        val kernelErode = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, Size(0.0, 0.0))
//        Imgproc.erode(mRgba, mRgba, kernelErode)
//        NativeClass.main(mRgba!!.nativeObjAddr)




//        Convert bitmap to bytearray
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
//        Log.i(TAG, "onCameraFrame: byteArray $byteArray")

        btnCapturePicture.setOnClickListener {
            val image = Bitmap.createBitmap(mRgba!!.cols(), mRgba!!.rows(), Bitmap.Config.RGB_565)

//        Convert map to bitmap
            Utils.matToBitmap(mRgba, image)
            var bitmap: Bitmap? = image as Bitmap
            bitmap = Bitmap.createScaledBitmap(bitmap!!, 600, 450, false)
            val builder = AlertDialog.Builder(this)
            builder.setNegativeButton("OK") { _, _ ->
            }
            val dialog: AlertDialog = builder.create()
            val inflater = layoutInflater
            val dialogLayout: View = inflater.inflate(R.layout.show_photo, null)
            var resultBitmap = RotateBitmap(bitmap, 180f)
            println("bitmapPhoto?.rotationDegrees ${resultBitmap?.byteCount}")

            dialogLayout.img_showphoto.setImageBitmap(resultBitmap)
            dialog.setView(dialogLayout)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.show()

            try {
                zxingg()
            } catch (e: ChecksumException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                Toast.makeText(this,"ChecksumException = ${e.localizedMessage}",Toast.LENGTH_LONG).show()
            } catch (e: FormatException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                Toast.makeText(this,"FormatException = ${e.localizedMessage}",Toast.LENGTH_LONG).show()
            }
        }

//        val f = File(Environment.getExternalStorageDirectory().absolutePath + File.separator +"bar.png")
//        Log.i(TAG, "onManagerConnected: ${f.exists()} $f")
//        if (f.exists()) {
//            NativeClass.testBar()
//        }
//        Log.i(TAG, "onManagerConnected: ${NativeClass.testBar()}")
        return this.mRgba!!
    }

    private fun RotateBitmap(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source,
            0,
            0,
            source.width,
            source.height,
            matrix,
            true
        )
    }

    fun zxingg() {
        val bMap: Bitmap = Bitmap.createBitmap(mRgba!!.width(), mRgba!!.height(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(mRgba, bMap)
        val intArray = IntArray(bMap.width * bMap.height)
        //copy pixel data from the Bitmap into the 'intArray' array
        bMap.getPixels(intArray, 0, bMap.width, 0, 0, bMap.width, bMap.height)
        val source: LuminanceSource =
            RGBLuminanceSource(bMap.width, bMap.height, intArray)
        val bitmap = BinaryBitmap(HybridBinarizer(source))
        val reader: Reader = MultiFormatReader()
        var sResult = ""
        try {
            val result = reader.decode(bitmap)
            sResult = result.text
            Log.d(TAG ,"Code deditian scan $sResult")
            Toast.makeText(this,"$sResult",Toast.LENGTH_LONG).show()
        } catch (e: NotFoundException) {
            Log.d(TAG, "Code deditian Not Found")
            Toast.makeText(this,"Not Found",Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

}
