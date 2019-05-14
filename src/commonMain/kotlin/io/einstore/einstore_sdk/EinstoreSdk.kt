package io.einstore.einstore_sdk

import com.vhrdina.multiplatform.network.NetworkClient
import com.vhrdina.multiplatform.network.model.Config
import com.vhrdina.multiplatform.network.model.NetworkError
import com.vhrdina.multiplatform.network.model.Request
import com.vhrdina.multiplatform.network.model.RequestConfig
import com.vhrdina.multiplatform.network.util.NetworkSerializer
import io.einstore.einstore_sdk.model.SDKCheckRequest
import io.einstore.einstore_sdk.model.SDKCheckResponse

// TODO read Einstore sdk version from gradle.properties
const val sdkVersion = "0.0.1"

class EinstoreSdk(val deviceInfo: DeviceInfo, val networkClient: NetworkClient) : IEinstoreSdk {

    init {
        NetworkSerializer.apply {
            setMapper(SDKCheckRequest::class, SDKCheckRequest.serializer())
            setMapper(SDKCheckResponse::class, SDKCheckResponse.serializer())
        }

    }

    class Factory(val deviceInfo: DeviceInfo, host: String, val apiKey: String) : IEinstoreSdk.Factory {

        val requestConfig = RequestConfig(
            host = host,
            headers = HashMap<String, String>().apply {
                put("Authorization", apiKey)
            })

        val sdkConfig = Config(requestConfig = requestConfig, debug = true, mock = false)

        override fun create(): EinstoreSdk {

            val networkClient = NetworkClient(config = sdkConfig)

            return EinstoreSdk(deviceInfo, networkClient)
        }
    }

    fun getDeviceData(onSuccess: (SDKCheckResponse) -> Unit, onError: (NetworkError?) -> Unit) {
        val deviceData = deviceInfo.getDeviceData()

        val request = Request(
            method = "POST",
            endpoint = "sdk",
            contentType = "application/json",
            body = SDKCheckRequest("com.nn.nn")
        )

        with(networkClient.execute<SDKCheckResponse>(request)) {
            onReceive = { response ->
                println("Device data successfully received: $response")
                response?.result?.let {
                    onSuccess(it)
                }
                response?.networkError?.let {
                    onError(it)
                }
            }

            this.onError = {
                println("Device data sending error")
                onError(it)
            }
        }

    }
}