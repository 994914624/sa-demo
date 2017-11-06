#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_cn_sensorsdata_demo_bean_TestNDK_myMethodString(JNIEnv *env, jclass type) {

    // TODO


    return env->NewStringUTF("yangzk");
}


