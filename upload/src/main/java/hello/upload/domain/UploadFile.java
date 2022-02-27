package hello.upload.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {
    /*
    클라이언트에서 업로드할 때의 파일명을 그대로 쓰면 파일명이 중복될 수 있기 때문에 클라이언트에서 업로드할 때의 파일명과 서버에서
    저장할 때의 파일명을 따로 관리해주어야 한다. 뷰를 렌더링할 때 지정된 파일경로에서 서버측에서 저장한 파일명으로 파일을 찾고,
    클라이언트가 해당 파일을 다운로드 할때 클라이언트에서 업로드했을 때의 파일명으로 파일을 넘긴다.
     */

    //업로드한 파일이름
    private String uploadFileName;

    //시스템에 저장한 파일이름
    private String storeFileName;
}
