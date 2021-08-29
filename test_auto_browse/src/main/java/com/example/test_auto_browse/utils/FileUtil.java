package com.example.test_auto_browse.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.CharBuffer;

public class FileUtil {

    public static String readFileString(String filePath) {
        String fileString = "";
        if (filePath == null || !new File(filePath).exists()) {
            fileString = "";
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(filePath);
                int fileSize = fileInputStream.available();
                byte[] fileContent = new byte[fileSize];
                int readSize = fileInputStream.read(fileContent);
                while (readSize < fileSize) {
                    readSize += fileInputStream.read(fileContent, readSize, fileSize - readSize);
                }

                fileString = new String(fileContent);
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return fileString;
    }

    public static void saveFileString(String filePath, String fileContent) {
        if (filePath == null || fileContent == null) {
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fileContent);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
