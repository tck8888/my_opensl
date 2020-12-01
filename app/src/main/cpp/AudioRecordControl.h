//
// Created by tck on 2020/11/30.
//

#ifndef MY_OPENSL_AUDIORECORDCONTROL_H
#define MY_OPENSL_AUDIORECORDCONTROL_H


#include <stdio.h>
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include "AndroidLog.h"
#include "RecordBuffer.h"


class AudioRecordControl {

private:
    SLObjectItf engineObject = nullptr;//引擎对象
    SLEngineItf engineEngine = nullptr; //引擎接口

    SLObjectItf recorderObject = nullptr;//录制对象，这个对象我们从里面获取了2个接口

public:
    SLRecordItf recorderRecorder = nullptr;                        //录制接口
    RecordBuffer *recordBuffer;
    FILE *pcmFile;
    bool finished;

    void startRecord(const char *pcmPath, int channels = 2);

    void stopRecord();
};


#endif //MY_OPENSL_AUDIORECORDCONTROL_H
