package com.btc.snow.user.mypage;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Log4j2
@Service
public class UploadFileServiceForMypage {

    public String upload(MultipartFile file, String id) {
        log.info("[UploadFileService] upload()");

        // 저장 유무
        boolean result = false;

        // File 저장
        String fileOriName = file.getOriginalFilename();
        String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());
        String uploadDir = "c:\\snow/user/img/" + id;
//        String uploadDir = "c:\\snow\\member\\" + user_id; // 아이디별로 받겠다 여기에 키득

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
            log.info("[UploadFileService] FILE UPLOAD SUCCESS!!");

            return uniqueName + fileExtension;

        } else {
            log.info("[UploadFileService] FILE UPLOAD FAIL!!");

            return null;

        }

    }

}