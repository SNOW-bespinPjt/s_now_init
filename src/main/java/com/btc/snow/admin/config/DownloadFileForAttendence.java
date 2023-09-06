package com.btc.snow.admin.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Controller
public class DownloadFileForAttendence {

    private static final String DOWNLOAD_DIR = "C:\\snow\\user\\attendence"; // 파일이 저장된 경로

    @GetMapping("/dfileattendence")
    public ResponseEntity<Resource> downloadFile(@RequestParam String file) {
        log.info("[DownloadFileController] downloadFile");
        log.info("download file name :::::::::::: " + file);

        try {
            // 파일 경로 생성
            Path filePath = Paths.get(DOWNLOAD_DIR).resolve(file).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                // 파일이 존재하고 읽을 수 있는 경우 다운로드 응답 생성
                log.info("[DownloadFileControllerForAdmin] 파일 존재");
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; file=\"" + resource.getFile() + "\"")
                        .body(resource);
            } else {
                // 파일이 존재하지 않거나 읽을 수 없는 경우 404 에러 응답
                log.info("[DownloadFileControllerForAdmin] 파일 없음");
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            // 파일 경로 변환 실패 시 400 에러 응답
            log.info("[DownloadFileControllerForAdmin] 파일 경로 변환 실패");
            return ResponseEntity.badRequest().build();

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
}
