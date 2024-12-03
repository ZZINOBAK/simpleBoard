package com.simpleboard.controller;

import com.simpleboard.service.log.LogAnalyzerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class LogController {
    @GetMapping("/uploadLogFileForm")
    public String uploadLogFileForm() {
        return "log/upload-log-file-form";
    }

    @PostMapping("/uploadLogFile")
    public String uploadLogFile(@RequestParam("file") MultipartFile file, Model model) {
        // 파일이 비어있거나 .txt 파일이 아닌 경우 처리
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".txt")) {
            // 메시지 설정
            model.addAttribute("message", file.isEmpty() ? "파일 없음." : "txt파일 아님.");
            return "log/uploaded-log-file";  // 업로드 페이지로 이동
        }

        try {
            // 원본 파일명 가져오기
            String fileName = file.getOriginalFilename();

            // 한글 포함 파일명을 안전하게 처리 (영문 및 숫자만 사용)
            String sanitizedFileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

            // 파일을 저장할 경로 설정 (logs 디렉토리 하위)
            Path path = Paths.get("logs/" + sanitizedFileName);

            // 로그 디렉토리가 없으면 생성
            Files.createDirectories(path.getParent()); // 디렉토리 없으면 생성

            // 파일 저장
            file.transferTo(path);

            model.addAttribute("message", "파일 업로드 성공: " + sanitizedFileName);
        } catch (IOException e) {
            model.addAttribute("message", "파일 업로드 실패: " + e.getMessage());
        }

        return "log/uploaded-log-file";  // 업로드 결과 페이지로 이동
    }

    @GetMapping("/analyzeLog")
    public String analyzeLog(@RequestParam("fileName") String fileName, Model model) throws IOException {
        // 'logs' 디렉토리 경로 + 선택된 파일명
        String inputFilePath = "logs/" + fileName;
        String outputFilePath = "logs/output/output_" + fileName; // 분석 결과를 저장할 파일 경로
        LogAnalyzerService logAnalyzer = new LogAnalyzerService(inputFilePath, outputFilePath);

//        LogAnalyzerService logAnalyzer = new LogAnalyzerService("src/main/resources/log/input.txt",
//                "src/main/resources/log/output.txt");


        int logAmount = 5;

        while (logAmount > 0) {
            logAnalyzer.readAndParseLog();
            logAmount--;
        }
        logAnalyzer.analyzeLogData();

        logAnalyzer.close();


        // 분석 결과 파일을 모델에 추가 (파일 읽어서)
        String analysisResult = new String(Files.readAllBytes(Paths.get(outputFilePath)), StandardCharsets.UTF_8);

        // 모델에 분석 결과를 추가
        model.addAttribute("analysisResult", analysisResult.toString());  // 분석 결과를 화면에 출력
        model.addAttribute("fileName", fileName); // 파일 이름도 함께 전달

        return "log/result-log-analysis";
    }

}
