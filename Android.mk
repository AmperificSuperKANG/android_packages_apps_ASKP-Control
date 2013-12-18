LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_STATIC_JAVA_LIBRARIES := \
        android-common \
        guava \
        android-support-v13 \
        android-support-v4 \

LOCAL_PACKAGE_NAME := ASKPControl

include $(BUILD_PACKAGE)

# additionally, build tests in sub-folders in a separate .apk
include $(call all-makefiles-under,$(LOCAL_PATH))
