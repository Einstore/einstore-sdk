package io.einstore.einstore_sdk

import com.vhrdina.multiplatform.util.Util
import com.vhrdina.multiplatform.util.platformName

class IosDeviceInfo constructor() : DeviceInfo {

    class Factory : DeviceInfo.Factory {

        override fun create(): DeviceInfo {
            return IosDeviceInfo()
        }
    }

    override fun getDeviceData(): Map<String, String?> {
        return HashMap<String, String?>().apply {
            put(APP_PACKAGE, Util.bundleIdentifier)
            put(APP_VERSION_CODE, Util.getVersionNumber())
            put(APP_VERSION_NAME, Util.getVersion())
            put(OS, platformName())
            put(EINSTORE_SDK_VERSION, sdkVersion)
            put(LANGUAGE, Util.lang?.toString())
            put(OS_VERSION, Util.osVersion)
            put(APP_LAST_MODIFIED, Util.getBuildDate())
        }
    }
}