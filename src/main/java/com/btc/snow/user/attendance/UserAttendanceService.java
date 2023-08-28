package com.btc.snow.user.attendance;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class UserAttendanceService implements  IUserAttendanceService{

    @Autowired
    UserAttendanceMapper userAttendanceMapper;


    public void qrCreate() throws WriterException {
        log.info("Service qrCreate() called");


        int width=200;
        int height=200;


        // do update confirm?u_m_no=1
        String url="http://localhost:8090/qrtest/confirm";

        BitMatrix encode=new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE,width,height);
        Map<String,Object> map = new HashMap<>();


        try {

            ByteArrayOutputStream out=new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(encode,"PNG",out);

            ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(out.toByteArray());


        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Object qrChackConfirm(int u_m_no){

        log.info("Service qrChackConfirm() called");

        int result= userAttendanceMapper.qrCheckConfrim(u_m_no);




        return  result;

    }

}
