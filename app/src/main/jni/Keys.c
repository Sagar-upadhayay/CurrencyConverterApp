#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_currencyconverter_AppModule_AppModule_GetUrl(JNIEnv *env,jobject thiz){
    return (*env)->NewStringUTF(env,"https://v6.exchangerate-api.com/v6/4a4397d4fdc5c9eb868e2506/latest/");
}
