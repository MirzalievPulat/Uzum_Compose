//
// Created by Sherzodbek Muhammadiev on 29/10/24.
//

#ifndef BOOTCAMP8NDK_FUNCTIONS_H
#define BOOTCAMP8NDK_FUNCTIONS_H

#define repeat(a) for(int it = 0; it < a; it++)
#define nativeScope extern "C"
#define fun(TYPE, CLASS, METHOD, ...) JNIEXPORT TYPE JNICALL Java_uz_gita_presentation_pages_home_##CLASS##_##METHOD(JNIEnv *env, jobject, ## __VA_ARGS__)
#define stringToJava(a) env->NewStringUTF(a.c_str())

#endif //BOOTCAMP8NDK_FUNCTIONS_H
