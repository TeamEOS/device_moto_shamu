/*
 * Copyright (C) 2014 The CyanogenMod Project
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

package org.cyanogenmod.hardware;

import org.cyanogenmod.hardware.util.FileUtils;
import java.io.*;

/*
 * Dual support TapToWake
 * 
 * If Flar2 implementation (Eos ships with it) is present, give priority
 * Otherwise, fall back to stock implementation
 * 
 */

public class TapToWake {

    private static String FLAR2_PATH = "/sys/android_touch/doubletap2wake";
    private static String NATIVE_PATH = "/sys/bus/i2c/devices/1-004a/tsp";

    public static boolean isSupported() {
        boolean hasFlar2 = new File(FLAR2_PATH).exists();
        boolean hasNative = new File(NATIVE_PATH).exists();
        return hasFlar2 || hasNative;
    }

    public static boolean isEnabled() {
        if (new File(FLAR2_PATH).exists()) {
            return "1".equals(FileUtils.readOneLine(FLAR2_PATH));
        } else if (new File(NATIVE_PATH).exists()) {
            return "AUTO".equals(FileUtils.readOneLine(NATIVE_PATH));
        }
        return false;
    }

    public static boolean setEnabled(boolean state) {
        if (new File(FLAR2_PATH).exists()) {
            return FileUtils.writeLine(FLAR2_PATH, (state ? "1" : "0"));
        } else if (new File(NATIVE_PATH).exists()) {
            return FileUtils.writeLine(NATIVE_PATH, (state ? "AUTO" : "OFF"));
        }
        return false;
    }
}
