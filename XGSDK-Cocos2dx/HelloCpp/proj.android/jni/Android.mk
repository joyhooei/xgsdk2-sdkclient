LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_LDLIBS :=-llog #添加这行，记得还要修改build_native.sh中的路径（2个路径）
#1.删除了xgsdk-plugin.jar包
#2.删除了旧Demo中的两个org.cocos2dx.plugin和lib包，用xgsdk-common-lib和libcocos2dx替代

LOCAL_MODULE := hellocpp_shared

LOCAL_MODULE_FILENAME := libhellocpp

LOCAL_SRC_FILES := hellocpp/main.cpp \
                   ../../Classes/AppDelegate.cpp \
                   ../../Classes/HelloWorldScene.cpp \
                   hellocpp/ProtocolXGSDK.cpp #添加这行

LOCAL_C_INCLUDES := $(LOCAL_PATH)/../../Classes \
				

LOCAL_WHOLE_STATIC_LIBRARIES := cocos2dx_static
#LOCAL_WHOLE_STATIC_LIBRARIES += PluginProtocolStatic #注释这行
LOCAL_WHOLE_STATIC_LIBRARIES += cocos_extension_static


include $(BUILD_SHARED_LIBRARY)

$(call import-module,cocos2dx)
$(call import-module,extensions)
#$(call import-module,protocols/android) #注释这行

