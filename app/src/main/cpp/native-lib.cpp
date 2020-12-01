#include <jni.h>
#include <string>
#include "AudioControl.h"
#include "AudioRecordControl.h"


extern "C"
JNIEXPORT void JNICALL
Java_com_tck_jni_opensl_AudioControl_nativePlay(JNIEnv *env, jobject thiz, jstring _url) {

    char const *url = env->GetStringUTFChars(_url, nullptr);
    auto *audioControl = new AudioControl();
    audioControl->setDadaSource(url);
    audioControl->start();
    env->ReleaseStringUTFChars(_url, url);
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_tck_jni_opensl_AudioControl_nativeInit(JNIEnv *env, jobject thiz) {
    auto audioRecordControl = new AudioRecordControl();
    return (jlong) audioRecordControl;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_tck_jni_opensl_AudioControl_nativeStartRecordAudio(JNIEnv *env, jobject thiz,
                                                            jlong native_handle, jstring _url) {
    const char *url = env->GetStringUTFChars(_url, nullptr);
    auto audioRecordControl = reinterpret_cast<AudioRecordControl *>(native_handle);
    audioRecordControl->setDataSource(url);
    audioRecordControl->start();
    env->ReleaseStringUTFChars(_url, url);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_tck_jni_opensl_AudioControl_nativeStopRecordAudio(JNIEnv *env, jobject thiz,
                                                           jlong native_handle) {

    auto audioRecordControl = reinterpret_cast<AudioRecordControl *>(native_handle);
    if (audioRecordControl) {
        audioRecordControl->stop();
    }
}