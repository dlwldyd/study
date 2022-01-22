package hello.upload.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {

    //업로드한 파일이름
    private String uploadFileName;

    //시스템에 저장한 파일이름
    private String storeFileName;
}
