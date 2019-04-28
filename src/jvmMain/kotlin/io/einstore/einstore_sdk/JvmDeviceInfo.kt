package io.einstore.einstore_sdk

class JvmDeviceInfo constructor() : DeviceInfo {

    class Factory : DeviceInfo.Factory {

        override fun create(): DeviceInfo {
            return JvmDeviceInfo()
        }

    }

    override fun getDeviceData(): Map<String, String?> {
        return HashMap<String, String?>().apply {

        }
    }
}
