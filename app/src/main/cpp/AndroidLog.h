//
// Created by tck on 2020/11/27.
//

#ifndef MY_OPENSL_ANDROIDLOG_H
#define MY_OPENSL_ANDROIDLOG_H

#include "android/log.h"

#define LOG_DEBUG true
#define LOGD(FORMAT,...) __android_log_print(ANDROID_LOG_DEBUG,"tck6666",FORMAT,##__VA_ARGS__);
#define LOGE(FORMAT,...) __android_log_print(ANDROID_LOG_ERROR,"tck6666",FORMAT,##__VA_ARGS__);
#define LOGI(FORMAT,...) __android_log_print(ANDROID_LOG_INFO,"tck6666",FORMAT,##__VA_ARGS__);

#endif //MY_OPENSL_ANDROIDLOG_H
