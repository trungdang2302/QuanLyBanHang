package QuanLyCuaHangMain.Controller;

import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import QuanLyCuaHangMain.Entity.UploadForm;

@RestController
public class UploadRestController {
	
    private static String UPLOAD_DIR = System.getProperty("user.home") + "/eclipse-workspace/QuanLyCuaHang/src/main/resources/static/images";
  
    @PostMapping("/rest/uploadMultiFiles")
    public ResponseEntity<?> uploadFileMulti(@ModelAttribute UploadForm form) throws Exception { 
        String result = null;
        try {
 
            result = this.saveUploadedFiles(form.getFiles());
 
        }
        
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
 
        return new ResponseEntity<String>("Uploaded to: " + result, HttpStatus.OK);
    }
 
    // Save Files
    private String saveUploadedFiles(MultipartFile[] files) throws IOException {
 
        // check dir
        File uploadDir = new File(UPLOAD_DIR);
        uploadDir.mkdirs();
 
        StringBuilder sb = new StringBuilder();
 
        for (MultipartFile file : files) {
 
            if (file.isEmpty()) {
                continue;
            }
            String uploadFilePath = UPLOAD_DIR + "/" + file.getOriginalFilename();
 
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
 
            sb.append(uploadFilePath).append(", ");
        }
        return sb.toString();
    }
 
    // load files
    @GetMapping("/rest/getAllFiles")
    public List<String> getListFiles() {
        File uploadDir = new File(UPLOAD_DIR);
 
        File[] files = uploadDir.listFiles();
 
        List<String> list = new ArrayList<String>();
        for (File file : files) {
            list.add(file.getName());
        }
        return list;
    }
 
    // @filename: 
    @GetMapping("/rest/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {
        File file = new File(UPLOAD_DIR + "/" + filename);
        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }
        Resource resource = new UrlResource(file.toURI());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
}
