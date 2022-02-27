package hello.upload.controller;

import hello.upload.domain.Item;
import hello.upload.domain.ItemRepository;
import hello.upload.domain.UploadFile;
import hello.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm form) {
        return "item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        //데이터베이스에 저장
        Item item = new Item(form.getItemName(), attachFile, storeImageFiles);
        itemRepository.save(item);

        //컨트롤러에서 리다이렉트시 RedirectAttributes 를 통해 경로변수를 줄 수 있다.
        redirectAttributes.addAttribute("itemId", item.getId());

        return "redirect:/items/{itemId}";
    }

    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id);
        model.addAttribute("item", item);
        return "item-view";
    }

    //이미지 파일을 반환하는 핸들러 메서드, 파일을 반환할 때는 UrlResource("file:파일이 위치한 경로")를 반환해야한다.
    @ResponseBody
    @GetMapping("images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    //파일을 반환하는 메서드(첨부파일 링크가 있고 해당 링크를 클릭하면 파일 다운로드, 클릭시 서버로 /attach/{itemId} url로 요청을 보냄)
    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        Item item = itemRepository.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        //파일이름이 한글이면 깨지기 때문에 utf-8로 인코딩 해줌, UriUtils 는 수많은 인코딩 기능 제공하는 클래스
        String encodedUploadFile = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        //첨부파일임을 나타내는 헤더값
        String contentDisposition = "attachment; filename=\"" + encodedUploadFile + "\"";

        /*
        클라이언트가 파일을 브라우저에서 읽도록 하는게 아니라 다운로드를 하게 만드려면 Content-Disposition 헤더값을
        attachment; filename="(파일의 경로)"로 세팅하고 body 에 UrlResource 를 넣어야 한다.(ResponseEntity 사용)
        UrlResource 를 그대로 반환하면 파일 내용이 브라우저에 그대로 보이게 됨
         */
        return ResponseEntity.ok() // http 상태코드를 200 ok로 생성
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
