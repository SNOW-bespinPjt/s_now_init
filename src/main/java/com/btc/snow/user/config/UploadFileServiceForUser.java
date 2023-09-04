package com.btc.snow.user.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Log4j2
@Service
public class UploadFileServiceForUser {

    public String upload(MultipartFile file, String user_id) {
        log.info("[UploadFileServiceForUser] upload()");

        // 저장 유무
        boolean result = false;

        // File 저장
        String fileOriName = file.getOriginalFilename();
        String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());
        String uploadDir = "c:\\snow\\user\\assignment\\" + user_id; // user_id 별로 받아야지

        UUID uuid = UUID.randomUUID(); // 랜덤으로 유니크한 값을 생성해서 보내주는 메소드 (덮어쓰기 방지)
        String uniqueName = uuid.toString().replace("-", ""); // 값이 000-000-.. 이런형식이라서 걍 "-" 없애기 [선택사항]

        File saveFile = new File(uploadDir + "\\" + uniqueName + fileExtension);

        if (!saveFile.exists())
            saveFile.mkdirs();

        try {
            file.transferTo(saveFile);
            
            result = true;

        } catch (Exception e) {
            e.printStackTrace();

        }

        // 성공시 저장
        if (result == true) {
            log.info("[UploadFileServiceForUser] FILE UPLOAD SUCCESS!!");

            return uniqueName + fileExtension;

        } else {
            log.info("[UploadFileServiceForUser] FILE UPLOAD FAIL!!");

            return null;

        }

    }

}
