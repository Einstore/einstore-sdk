package io.einstore.einstore_sdk

interface IEinstoreSdk {

    interface Factory {

        fun create(): IEinstoreSdk
    }
}