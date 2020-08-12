#include <opencv2/core.hpp>
#include <jni.h>
#include <opencv2/imgproc.hpp>


using namespace cv;
using namespace std;

extern "C"
JNIEXPORT void JNICALL Java_com_tsmid_learnopencv_NativeClass_testFunction(JNIEnv *env,jclass type, jlong addRgba){
    Mat &img = *(Mat *) addRgba;
    cvtColor(img,img,COLOR_RGB2GRAY);
}
