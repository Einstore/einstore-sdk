package io.einstore.einstore_sdk

import android.content.Context
import com.vhrdina.multiplatform.util.Util
import com.vhrdina.multiplatform.util.platformName

class AndroidDeviceInfo constructor(private val context: Context): DeviceInfo {

    class Factory(private val context: Context): DeviceInfo.Factory {

        override fun create(): DeviceInfo {
            return AndroidDeviceInfo(context)
        }
    }

    override fun getDeviceData(): Map<String, String?> {
        return HashMap<String, String?>().apply {
            put(OS_VERSION, Util.osVersion)
            put(BUILD_ID, Util.buildId)
            put(MODEL, Util.model)
            put(OEM, Util.manufacturer)
            put(APP_PACKAGE, Util.getPackageName(context))
            put(APP_VERSION_CODE, Util.getVersionCode(context)?.toString())
            put(APP_VERSION_CODE_LONG, Util.getLongVersionCode(context)?.toString())
            put(APP_VERSION_NAME, Util.getVersionName(context))
            put(ABI, Util.supportedAbis.joinToString())
            put(LANGUAGE, Util.language)
            put(APP_LAST_MODIFIED, Util.getLastModified(context).toString())
            put(DISPLAY_ID, Util.displayId)
            put(PRODUCT, Util.product)
            put(BOARD, Util.board)
            put(BOOTLOADER, Util.bootloader)
            put(HARDWARE, Util.hardware)
            put(SDK, Util.sdk.toString())
            put(CODENAME, Util.codename)
            put(IS_EMULATOR, Util.isEmulator.toString())
            put(FIRST_INSTALL_TIME, Util.getFirstInstallTime(context)?.toString())
            put(LAST_UPDATE_TIME, Util.getLastUpdateTime(context)?.toString())
            put(OS, platformName())
            put(EINSTORE_SDK_VERSION, sdkVersion)
        }
    }
}