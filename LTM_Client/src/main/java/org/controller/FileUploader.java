package org.controller;

import java.io.*;

public class FileUploader {
    public static String uploadFile(String url, File file) {
        try {
            String command = "curl -F \"file=@" + file.getAbsolutePath() + "\" " + url;
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/c", command);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                return line;
            }
            process.waitFor();
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
