//
// Created by tck on 2020/11/27.
//

#ifndef MY_OPENSL_ANDROIDLOG_H
#define MY_OPENSL_ANDROIDLOG_H

#define LOG_TAG    "tck6666"
#define LOG_DEBUG true

#ifdef ANDROID
#include "android/log.h"

#define LOGD(FORMAT,...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,FORMAT,##__VA_ARGS__);
#define LOGE(FORMAT,...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,FORMAT,##__VA_ARGS__);
#define LOGI(FORMAT,...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,FORMAT,##__VA_ARGS__);
#else
#define LOGE(format, ...)  printf(LOG_TAG format "\n", ##__VA_ARGS__)
#define LOGI(format, ...)  printf(LOG_TAG format "\n", ##__VA_ARGS__)
#endif

#endif //MY_OPENSL_ANDROIDLOG_H
