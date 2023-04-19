java -jar ./systemKey/signapk.jar ./systemKey/platform.x509.pem ./systemKey/platform.pk8 ./app/build/outputs/apk/debug/MemoryManager-debug.apk ./MemoryManager.apk

adb root
adb remount
adb shell mkdir /system/app/MemoryManager

adb push ./MemoryManager.apk /system/app/MemoryManager/