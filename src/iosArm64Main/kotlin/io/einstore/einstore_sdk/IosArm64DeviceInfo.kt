package io.einstore.einstore_sdk

import com.vhrdina.multiplatform.util.IosUtil
import com.vhrdina.multiplatform.util.platformName

class IosArm64DeviceInfo constructor() : DeviceInfo {

    class Factory : DeviceInfo.Factory {

        override fun create(): DeviceInfo {
            return IosArm64DeviceInfo()
        }
    }

    override fun getDeviceData(): Map<String, String?> {
        return HashMap<String, String?>().apply {
            put(APP_PACKAGE, IosUtil.bundleIdentifier)
            put(APP_VERSION_CODE, IosUtil.getVersionNumber())
            put(APP_VERSION_NAME, IosUtil.getVersion())
            put(OS, platformName())
            put(EINSTORE_SDK_VERSION, sdkVersion)
            put(LANGUAGE, IosUtil.lang?.toString())
            put(OS_VERSION, IosUtil.osVersion)
            put(APP_LAST_MODIFIED, IosUtil.getInstallDate())
        }
    }
}