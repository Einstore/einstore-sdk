package io.einstore.einstore_sdk.model

import kotlinx.serialization.Serializable

@Serializable
data class SDKCheckRequest(val identifier: String)

/**
"identifier": "com.ford.app",
"version": "2.0",
"build": "12345",
"created": "2019-05-01T11:50:42Z",
"sdk": "1.0",
"language": "en-gb",
"os": "android",
"os_version": "7.2"
 **/