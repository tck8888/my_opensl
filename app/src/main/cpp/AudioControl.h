//
// Created by tck on 2020/11/27.
//

#ifndef MY_OPENSL_AUDIOCONTROL_H
#define MY_OPENSL_AUDIOCONTROL_H

#include "cstring"
#include <string>
#include "AndroidLog.h"

#define OUT_BUFFER_SIZE 44100 * 2 * 2
extern "C"
{
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
}

class AudioControl {


public:
    char *url = nullptr;
    FILE *pcmFile = nullptr;

    uint8_t *out_buffer = nullptr;
    // 引擎接口
    SLObjectItf engineObject = nullptr;
    SLEngineItf engineEngine = nullptr;

    //混音器
    SLObjectItf outputMixObject = nullptr;

    //pcm
    SLObjectItf pcmPlayerObject = nullptr;
    SLPlayItf pcmPlayerPlay = nullptr;

    //缓冲器队列接口
    SLAndroidSimpleBufferQueueItf pcmBufferQueue;

public:

    ~AudioControl();

public:
    void setDadaSource(const char *_url);

    void start();

    void getPcmData(void **pcm);

};


#endif //MY_OPENSL_AUDIOCONTROL_H
