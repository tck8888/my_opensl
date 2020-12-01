//
// Created by tck on 2020/11/30.
//

#ifndef MY_OPENSL_AUDIORECORDCONTROL_H
#define MY_OPENSL_AUDIORECORDCONTROL_H

#define RECORD_BUFFER_QUEUE_NUM 2
#define RECORDER_FRAMES 2048

#include "cstring"
#include <stdio.h>
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include "AndroidLog.h"

class AudioRecordControl {

private:
    int      mIndex=0;
    short    *mRecordBuffs[2];
    unsigned mRecordBufferSize=RECORDER_FRAMES;

    FILE *mFile = nullptr;
    bool mIsRecording = false;

    SLObjectItf mEngineObj = nullptr;
    SLEngineItf mEngineInterface = nullptr;

    SLObjectItf mRecorderObj = nullptr;
    SLRecordItf mRecorderInterface = nullptr;

    SLAndroidSimpleBufferQueueItf mBufferQueue= nullptr;

public:
    AudioRecordControl();

    ~AudioRecordControl();

public:
    bool start();

    void stop();

    void setDataSource(const char *string);

private:
    bool initEngine();

    bool initRecorder();

    void release();

    static void recorderCallback(SLAndroidSimpleBufferQueueItf queue, void *context) ;

};


#endif //MY_OPENSL_AUDIORECORDCONTROL_H
