package com.simpleboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @GetMapping("/admin")
    public String goAdmin(Model model) {
        // logs 디렉토리 경로
        Path logsDir = Paths.get("logs");

        // 로그 디렉토리 내 파일 목록 가져오기
        try {
            // 디렉토리가 존재하면, 해당 디렉토리 내 파일 목록을 가져옵니다
            if (Files.exists(logsDir) && Files.isDirectory(logsDir)) {
                List<String> fileNames = Files.list(logsDir)
                        .filter(Files::isRegularFile)  // 파일만 필터링
                        .map(Path::getFileName)         // 파일 이름만 가져오기
                        .map(Path::toString)            // 문자열로 변환
                        .collect(Collectors.toList());  // 리스트로 수집
                model.addAttribute("fileNames", fileNames);  // JSP에서 사용할 파일 이름 목록
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "파일 목록을 가져오는 데 문제가 발생했습니다.");
        }

        return "admin-page";
    }

}
