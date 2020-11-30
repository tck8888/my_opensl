#include <jni.h>
#include <string>
#include "AudioControl.h"


extern "C"
JNIEXPORT void JNICALL
Java_com_tck_jni_opensl_AudioControl_nativePlay(JNIEnv *env, jobject thiz, jstring _url) {

    char const *url = env->GetStringUTFChars(_url, nullptr);
    auto *audioControl = new AudioControl();
    audioControl->setDadaSource(url);
    audioControl->start();
    env->ReleaseStringUTFChars(_url, url);
}