package com.example;

import java.io.FileWriter;
import java.io.IOException;

public class Metrics {
    public static void log(String filename, String algorithm, int n, long timeNs, int recursionDepth) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.append(String.format("%s,%d,%d,%d\n", algorithm, n, timeNs, recursionDepth));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}