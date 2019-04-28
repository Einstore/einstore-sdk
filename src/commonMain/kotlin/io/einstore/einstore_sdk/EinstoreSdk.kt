package io.einstore.einstore_sdk

import com.vhrdina.multiplatform.network.NetworkClient
import com.vhrdina.multiplatform.network.model.Config
import com.vhrdina.multiplatform.network.model.RequestConfig

// TODO read Einstore sdk version from gradle.properties
const val sdkVersion = "0.0.1"

class EinstoreSdk(val deviceInfo: DeviceInfo): IEinstoreSdk {

    class Factory(val deviceInfo: DeviceInfo): IEinstoreSdk.Factory {

        override fun create(): EinstoreSdk {
            return EinstoreSdk(deviceInfo)
        }
    }

    private val requestConfig = RequestConfig(host = "")

    private val config = Config(requestConfig = requestConfig, debug = true, mock = false)

    private val networkClient = NetworkClient(config = config)

    fun getDeviceData(): Map<String, String?> {
        return deviceInfo.getDeviceData()
    }
}