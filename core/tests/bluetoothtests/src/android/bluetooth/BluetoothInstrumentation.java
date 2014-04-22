/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.bluetooth;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;

public class BluetoothInstrumentation extends Instrumentation {

    private BluetoothTestUtils mUtils = null;
    private BluetoothAdapter mAdapter = null;
    private Bundle mArgs = null;

    private BluetoothTestUtils getBluetoothTestUtils() {
        if (mUtils == null) {
            mUtils = new BluetoothTestUtils(getContext(),
                    BluetoothInstrumentation.class.getSimpleName());
        }
        return mUtils;
    }

    private BluetoothAdapter getBluetoothAdapter() {
        if (mAdapter == null) {
            mAdapter = ((BluetoothManager)getContext().getSystemService(
                    Context.BLUETOOTH_SERVICE)).getAdapter();
        }
        return mAdapter;
    }

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        mArgs = arguments;
        start();
    }

    @Override
    public void onStart() {
        String command = mArgs.getString("command");
        if ("enable".equals(command)) {
            enable();
        } else if ("disable".equals(command)) {
            disable();
        } else if ("unpairAll".equals(command)) {
            unpairAll();
        } else if ("getName".equals(command)) {
            getName();
        } else {
            finish(null);
        }
    }

    public void enable() {
        getBluetoothTestUtils().enable(getBluetoothAdapter());
        finish(null);
    }

    public void disable() {
        getBluetoothTestUtils().disable(getBluetoothAdapter());
        finish(null);
    }

    public void unpairAll() {
        getBluetoothTestUtils().unpairAll(getBluetoothAdapter());
        finish(null);
    }

    public void getName() {
        String name = getBluetoothAdapter().getName();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        finish(bundle);
    }

    public void finish(Bundle result) {
        if (result == null) {
            result = new Bundle();
        }
        finish(Activity.RESULT_OK, result);
    }
}
