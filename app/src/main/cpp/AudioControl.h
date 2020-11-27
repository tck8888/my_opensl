//
// Created by tck on 2020/11/27.
//

#ifndef MY_OPENSL_AUDIOCONTROL_H
#define MY_OPENSL_AUDIOCONTROL_H

#include "cstring"
#include <string>
#include "AndroidLog.h"

extern "C"
{
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
}

class AudioControl {

    friend void getPcmData(void **pcm);

public:
    char *url = NULL;
    FILE *pcmFile = NULL;
    // 引擎接口
    SLObjectItf engineObject = NULL;
    SLEngineItf engineEngine = NULL;

    //混音器
    SLObjectItf outputMixObject = NULL;
    SLEnvironmentalReverbItf outputMixEnvironmentalReverb = NULL;
    SLEnvironmentalReverbSettings reverbSettings = SL_I3DL2_ENVIRONMENT_PRESET_STONECORRIDOR;

    //pcm
    SLObjectItf pcmPlayerObject = NULL;
    SLPlayItf pcmPlayerPlay = NULL;
    SLVolumeItf pcmPlayerVolume = NULL;


    //缓冲器队列接口
    SLAndroidSimpleBufferQueueItf pcmBufferQueue;

public:
    AudioControl();

    ~AudioControl();

public:
    void setDadaSource(const char *_url);

    void start();
};


#endif //MY_OPENSL_AUDIOCONTROL_H
