package io.einstore.einstore_sdk

internal const val OS_VERSION = "os_version"
internal const val BUILD_ID = "build_id"
internal const val MODEL = "model"
internal const val OEM = "oem"
internal const val APP_PACKAGE = "identifier"
internal const val APP_VERSION_CODE = "version"
internal const val APP_VERSION_CODE_LONG = "app_version_code_long"
internal const val APP_VERSION_NAME = "app_version_name"
internal const val ABI = "abi"
internal const val LANGUAGE = "language"
internal const val APP_LAST_MODIFIED = "created"
internal const val DISPLAY_ID = "display_id"
internal const val PRODUCT = "product"
internal const val BOARD = "board"
internal const val BOOTLOADER = "bootloader"
internal const val HARDWARE = "hardware"
internal const val SDK = "sdk"
internal const val CODENAME = "codename"
internal const val IS_EMULATOR = "is_emulator"
internal const val FIRST_INSTALL_TIME = "first_install_time"
internal const val LAST_UPDATE_TIME = "last_update_time"
internal const val OS = "os"
internal const val EINSTORE_SDK_VERSION = "einstore_sdk_version"

interface DeviceInfo {

   interface Factory {

      fun create(): DeviceInfo
   }

   fun getDeviceData(): Map<String, String?>

}