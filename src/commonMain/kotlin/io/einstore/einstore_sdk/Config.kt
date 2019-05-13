package io.einstore.einstore_sdk

import kotlinx.serialization.Serializable

@Serializable
class Config(val host: String, val apiKey: String)