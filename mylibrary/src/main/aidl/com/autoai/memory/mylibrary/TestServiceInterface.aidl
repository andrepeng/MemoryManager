// TestServiceInterface.aidl
package com.autoai.memory.mylibrary;

// Declare any non-default types here with import statements

interface TestServiceInterface {
    int func();

    oneway void onewayFunc();

    void asyncFunc();
}