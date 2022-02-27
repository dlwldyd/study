package hello.upload.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ItemForm {

    private Long itemId;
    private String itemName;
    //MultipartFile file : 업로드된 파일이 바인딩 된다.
    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;
}
