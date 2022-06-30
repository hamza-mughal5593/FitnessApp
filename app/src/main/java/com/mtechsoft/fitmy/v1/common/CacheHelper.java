package com.mtechsoft.fitmy.v1.common;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CacheHelper {
    private String cachePath;
    private File outputDir;
    private final String EXT = ".png";

    public CacheHelper(Context context) {
        outputDir = context.getCacheDir();
        cachePath = outputDir.getPath();
    }

    public String putCache(String suffix, String fileId, byte[] bytes) {
        String output = null;

        try {
            String fileName = String.format("%s-%s", suffix, fileId);
            File outputFile = new File(outputDir, fileName);
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(bytes);
            }

            output = outputFile.getPath();
        } catch (IOException ex) {
            Log.e("putCache", ex.toString());
        }

        return output;
    }

    public byte[] getCache(String suffix, String fileId) {
        byte[] output = null;

        try {
            String fileName = String.format("%s-%s", suffix, fileId);
            File outputFile = new File(outputDir, fileName);

            byte bytes[] = new byte[(int) outputFile.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(outputFile));
            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(bytes);

            output = bytes;
        } catch (IOException ex) {
            Log.e("getCache", ex.toString());
        }

        return output;
    }
}
