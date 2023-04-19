package com.autoai.memory.manager;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellUtils {

    private static final String TAG = "ShellUtilsTag";

    /**
     * 执行shell 命令， 命令中不必再带 adb shell
     * @param cmd
     * @return Sting  命令执行在控制台输出的结果
     */

    public static String execByRuntime(String cmd) {
        Process process = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            inputStreamReader = new InputStreamReader(process.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            int read;
            char[] buffer = new char[4096];
            StringBuilder output = new StringBuilder();
            while ((read = bufferedReader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            Log.d(TAG, "result：" + output.toString());
            return output.toString();
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        } finally {
            if (null != inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (Throwable t) {
                    Log.e(TAG, Log.getStackTraceString(t));
                }
            }
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (Throwable t) {
                    Log.e(TAG, Log.getStackTraceString(t));
                }
            }
            if (null != process) {
                try {
                    process.destroy();
                } catch (Throwable t) {
                    Log.e(TAG, Log.getStackTraceString(t));
                }
            }
        }
    }
}
