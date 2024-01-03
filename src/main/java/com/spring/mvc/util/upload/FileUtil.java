package com.spring.mvc.util.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class FileUtil {
    /**
     * 사용자가 클라이언트에서 파일 전송 했을 때 중복이 없는 파일명을 새로 생성하여
     *             해당 파일명으로 날짜별 폴더로 업로드하는 메서드
     *
     * @param file - 사용자가 업로드한 파일의 정보객체
     * @param rootPath - 서버에 업로드할 루트 디렉터리
     *                  ex) c:/springImg/upload
     * @return - 업로드 완료시 업로드된 파일 위치의 경로
     */
    public static String uploadFIle(MultipartFile file, String rootPath){
//        원본 파일명을 중복이 없는 랜덤 이름으로 변경
        String newFileName = UUID.randomUUID()+ "_"+ file.getOriginalFilename();
        //        이 파일을 날짜별로 관리하기 위해 날짜별 폴더를 생성
        String newUploadPath = makeDateFormatDirectory(rootPath);

        String fullPath = newUploadPath + "/" + newFileName;

//        파일 업로드 수행
        try {
            file.transferTo(new File(newUploadPath,newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // C:/sfsdfo/dsf.jpg 에서 루트경로 제거
        return fullPath.substring(rootPath.length());
    }

    /**
     *  루트 경로 받아서 일자별 폴더로 생성 한 후 루트 경로 + 날짜 폴더 경로로 반환
     * @param rootPath - 파일 업로드 루트 경로
     * @return - 날짜 경로가 포함된 새로운 업로드 경로
     *          ex) C:/springImg/upload/2023/12/29
     */
    private static String makeDateFormatDirectory(String rootPath) {
//        오늘 날짜 정보 추출
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        String[] dateInfo = { year+"", len2(month),len2(day) };

        String directoryPath = rootPath;
        for (String s : dateInfo) {
            directoryPath += "/"+ s;
            File f = new File(directoryPath);
            if (!f.exists()) f.mkdirs();
        }
        return directoryPath;
    }

    /**
     * 한글자 월과 한글자 일 자를 두 글자로 변환해주는 메서드
     * ex ) 2024-1-1 ->2024-01-01
     * @param n  - 원본 일, 월
     * @return - 0이 붙은 일, 월
     */
    private static String len2(int n){
        return new DecimalFormat("00").format(n);
    }

}
