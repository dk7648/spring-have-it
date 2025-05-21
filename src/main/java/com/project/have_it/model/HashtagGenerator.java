package com.project.have_it.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HashtagGenerator {
    public static String generateHashtags(String title, String content) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "python", "src\\generate_hashtags.py", title, content
            );

            pb.redirectErrorStream(false);  // stderr 분리

            Process process = pb.start();

            BufferedReader stdOut = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)
            );
            BufferedReader stdErr = new BufferedReader(
                    new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8)
            );

            StringBuilder result = new StringBuilder();
            StringBuilder error = new StringBuilder();
            String line;
            while ((line = stdOut.readLine()) != null) result.append(line).append("\n");
            while ((line = stdErr.readLine()) != null) error.append(line).append("\n");

            int exitCode = process.waitFor();

            System.out.println("✅ Python 실행 완료 (exitCode: " + exitCode + ")");
            System.out.println("📤 결과: " + result);
            System.out.println("⚠️ 에러: " + error);

            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "오류 발생";
        }
    }
}