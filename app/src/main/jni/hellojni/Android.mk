LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := hellojni
LOCAL_SRC_FILES := hellojni.cpp
LOCAL_SHARED_LIBRARIES := libhello
LOCAL_LDLIBS    := -lm -llog

include $(BUILD_SHARED_LIBRARY)
