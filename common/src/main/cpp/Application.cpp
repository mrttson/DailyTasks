//
// Created by sontr on 9/26/2017.
//

#include <jni.h>
#include <string>
#include "ApplicationInfo.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_f2m_common_managers_AppManager_getApplicationEncryptKey(JNIEnv *env, jobject instance) {
    std::string key = ApplicationInfo::getApplicationEncryptKey();
    return env->NewStringUTF(key.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_f2m_common_managers_AppManager_getApplicationFirebaseKey(JNIEnv *env, jobject instance) {
    std::string key = ApplicationInfo::getApplicationFirebaseKey();
    return env->NewStringUTF(key.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_f2m_common_managers_AppManager_getApplicationSQLiteEncryptKey(JNIEnv *env,
                                                                       jobject instance) {
    std::string key = ApplicationInfo::getApplicationSQLiteEncryptKey();
    return env->NewStringUTF(key.c_str());
}