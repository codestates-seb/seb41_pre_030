package Be_30.Project.file.controller;

import Be_30.Project.auth.userdetails.MemberDetails;
import Be_30.Project.dto.MultiResponseDto;
import Be_30.Project.dto.SingleResponseDto;
import Be_30.Project.file.entity.ImageFile;
import Be_30.Project.file.mapper.FileMapper;
import Be_30.Project.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;

@Validated
@RequiredArgsConstructor
@RequestMapping("/files")
@RestController
public class FileController {

    private final FileService fileService;
    private final FileMapper mapper;

    @GetMapping
    public ResponseEntity<?> getFiles(@Positive @RequestParam(defaultValue = "1") int page,
                                      @Positive @RequestParam(defaultValue = "10") int size) {
        Page<ImageFile> pageFiles = fileService.getFiles(page - 1, size);
        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        mapper.filesToFileResponseDtos(pageFiles.getContent()),
                        pageFiles),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> createFile(MultipartFile file,
                                        @AuthenticationPrincipal MemberDetails member) {
        ImageFile savedFile = fileService.saveFile(file, member.getMemberId(), member.getEmail());
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.fileToFileResponseDto(savedFile)),
                HttpStatus.CREATED
        );
    }
}
