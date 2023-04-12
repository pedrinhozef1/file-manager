package br.com.project.image.manager.api.rest;

import br.com.project.image.manager.domain.application.FileApplication;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/v1/file")
@AllArgsConstructor
@Slf4j
@CrossOrigin
public class FileController {
    private FileApplication application;

    @PostMapping(value = "/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile fileDto) {
        log.info("File received {} ", fileDto.getOriginalFilename());

        var response = Map.of("id", application.persistImage(fileDto));

        return ResponseEntity.ok(response);
    }
    @PostMapping("/download")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> download(@RequestHeader Long id) {
        log.info("Download " + id);
        var url = Map.of("url", application.downloadFile(id));

        return ResponseEntity.ok(url);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestHeader Long id) {
        log.info("Delete " + id);
        application.delete(id);
    }
}
