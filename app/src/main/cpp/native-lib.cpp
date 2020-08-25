#include <opencv2/core.hpp>
#include <jni.h>
#include <opencv2/imgproc.hpp>
#include "opencv2/highgui/highgui.hpp"
#include "CV_Main.h"
#include <iostream>
#include <opencv2/imgproc/types_c.h>


using namespace cv;
using namespace std;

static CV_Main app;

extern "C"
JNIEXPORT void JNICALL Java_com_tsmid_learnopencv_NativeClass_main(JNIEnv *env,jclass type, jlong addRgba){
    Mat &img = *(Mat *) addRgba;



//    app.~CV_Main();
//    app.ReleaseMats();

    app.BarcodeDetect(img);
//    Mat img_rgb = img;
//
//    Mat img_hsv;
//    cvtColor(img_rgb, img_hsv, CV_BGR2HSV);
//    vector<Mat> channels_hsv;
//    split(img_hsv, channels_hsv);
//
//    Mat channel_s = channels_hsv[1];
//    GaussianBlur(channel_s, channel_s, Size(9, 9), 2, 2);
//
//    Mat imf;
//    channel_s.convertTo(imf, CV_32FC1, 0.5f, 0.5f);
//
//    Mat sobx, soby;
//    Sobel(imf, sobx, -1, 1, 0);
//    Sobel(imf, soby, -1, 0, 1);
//
//    sobx = sobx.mul(sobx);
//    soby = soby.mul(soby);
//
//    Mat grad_abs_val_approx;
//    cv::pow(sobx + soby, 0.5, grad_abs_val_approx);
//
//    Mat filtered;
//    GaussianBlur(grad_abs_val_approx, filtered, Size(9, 9), 2, 2);
//
//    Scalar mean, stdev;
//    meanStdDev(filtered, mean, stdev);
//
//    Mat thresholded;
//    cv::threshold(filtered, thresholded, mean.val[0] + stdev.val[0], 1.0, CV_THRESH_TOZERO);
//    Mat thresholded_bin;
//    cv::threshold(filtered, thresholded_bin, mean.val[0] + stdev.val[0], 1.0, CV_THRESH_BINARY);
//
//    Mat converted;
//    thresholded_bin.convertTo(converted, CV_8UC1);
//
//    vector<vector<Point>> contours;
//    findContours(converted, contours, CV_RETR_LIST, CV_CHAIN_APPROX_NONE);
//
//    Mat contour_img = Mat::zeros(converted.size(), CV_8UC1);
//    drawContours(contour_img, contours, -1, 255);

//    cvtColor(img,img,COLOR_RGB2GRAY);

//    Mat reduced_h;
//    img = img > 127;
//    Mat kernel(5, 1, CV_8U, Scalar(1));
//    erode(img, img, kernel,Point(-1,-1),4);
//
//    reduce(img, reduced_h, 0, REDUCE_AVG);
//
//    Mat reduced_h_graph = img.clone();
//    copyMakeBorder(reduced_h_graph, reduced_h_graph, 0, 100, 0, 0,BORDER_CONSTANT,Scalar(0,0,0));
//
//    for (int i = 0; i < img.cols; i++)
//    {
//        if (reduced_h.at<uchar>(0, i) > 150)
//            line(reduced_h_graph, Point(i, reduced_h_graph.rows-101), Point(i, reduced_h_graph.rows), Scalar(255, 255, 255), 1);
//    }
//
//    imshow("reduced_h_graph", reduced_h_graph);
//    waitKey(0);


}

extern "C"
JNIEXPORT void JNICALL Java_com_tsmid_learnopencv_NativeClass_testBar(JNIEnv *env,jclass type){

//
//    Mat gray, reduced_h;
//    cvtColor(gray, gray, COLOR_RGB2GRAY);
//    gray = gray > 127;
//    Mat kernel(5, 1, CV_8U, Scalar(1));
//    erode(gray, gray, kernel,Point(-1,-1),4);
//
//    reduce(gray, reduced_h, 0, REDUCE_AVG);
//
//    Mat reduced_h_graph = img.clone();
//    copyMakeBorder(reduced_h_graph, reduced_h_graph, 0, 100, 0, 0,BORDER_CONSTANT,Scalar(0,0,0));
//
//    for (int i = 0; i < img.cols; i++)
//    {
//        if (reduced_h.at<uchar>(0, i) > 150)
//            line(reduced_h_graph, Point(i, reduced_h_graph.rows-101), Point(i, reduced_h_graph.rows), Scalar(255, 255, 255), 1);
//    }
//
//    imshow("reduced_h_graph", reduced_h_graph);
//    waitKey(0);
//
}
