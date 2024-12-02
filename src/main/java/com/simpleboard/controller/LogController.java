package com.simpleboard.controller;

import com.simpleboard.service.log.LogAnalyzerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class LogController {
    @GetMapping("/uploadLogFileForm")
    public String uploadLogFileForm() {
        return "upload-log-file-form";
    }

    @PostMapping("/uploadLogFile")
    public String uploadLogFile(@RequestParam("file") MultipartFile file) {
        // 파일이 비어있는지 체크
        if (file.isEmpty()) {
            return "No file selected.";
        }

        try {
            // 파일을 저장할 경로
            Path path = Paths.get("uploads/" + file.getOriginalFilename());
            Files.createDirectories(path.getParent()); // 경로가 없으면 디렉토리 생성

            // 파일 저장
            file.transferTo(path.toFile());

            return "uploaded-log-file";
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed: " + e.getMessage();
        }
    }

    @GetMapping("/analyzeLog")
    public String analyzeLog() throws IOException {

        LogAnalyzerService logAnalyzer = new LogAnalyzerService("src/main/resources/log/input.txt",
                "src/main/resources/log/output.txt");

        int logAmount = 5;
        while (logAmount > 0) {
            logAnalyzer.readAndParseLog();
            logAmount--;
        }
        logAnalyzer.analyzeLogData();

        logAnalyzer.close();

        return "result-log-analysis";
    }

}
