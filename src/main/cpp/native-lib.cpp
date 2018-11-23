#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_wolfs_fwa_fwa2_10_Login_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
