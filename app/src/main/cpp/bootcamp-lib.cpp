//
// Created by msi brvo on 05-Nov-24.
//

#include <jni.h>
#include <string>
#include "functions.h"
#include <jni.h>

using namespace std;

string getKey() {
    string info = "Your secret key";
    return info;
}


//nativeScope {
//fun(jstring, Info, getInfo) {
//string info = getKey();
//return stringToJava(info);
//}
//
//fun(jint, Info, sqr, jint x) {
//int a = x;
//repeat(10) {
//
//}
//int c = a * a;
//return
//c;
//}
//
//}
extern "C" JNIEXPORT jstring JNICALL Java_uz_gita_Random_getInfo(JNIEnv* env, jobject /* this */) {
std::string info = "Your secret key";
return env->NewStringUTF(info.c_str());
}