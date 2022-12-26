package Be_30.Project.file.service;

import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import Be_30.Project.file.entity.ImageFile;
import Be_30.Project.file.repository.FileRepository;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.service.MemberService;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RequiredArgsConstructor
@Transactional
@Service
public class FileService {

    @Value("${cloud.aws.s3.bucketName}")
    private String S3BucketName;

    private final AmazonS3Client amazonS3Client;
    private final MemberService memberService;
    private final FileRepository fileRepository;

    public ImageFile saveFile(MultipartFile file, long memberId) {

        String fileType = file.getOriginalFilename().split("\\.")[1]; // 파일 타입 (jpg, png...)
        UUID uuidFileName = UUID.randomUUID(); // 파일 식별할 수 있는 UUID
        String saveFileName = String.format("%s.%s", uuidFileName, fileType); // 저장할 파일 전체 이름 (UUID + "." + jpg)

        ObjectMetadata metadata = new ObjectMetadata(); // 파일 메타데이터 생성
        metadata.setContentType(file.getContentType());

        try {
            // S3 버킷에 이미지 파일 업로드
            amazonS3Client.putObject(
                    new PutObjectRequest(
                            S3BucketName,
                            saveFileName,
                            file.getInputStream(),
                            metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessLogicException(ExceptionCode.FILE_SAVE_FAILED); // 파일 저장 실패
        }
        
        String imagePath = amazonS3Client.getUrl(S3BucketName, saveFileName).toString(); // 업로드한 파일 요청 경로
        Member member = memberService.findMember(memberId); // 사진 업로드한 회원 정보
        ImageFile imageFile = ImageFile.builder() // 파일 엔티티 생성 및 저장
                .member(member)
                .fileName(saveFileName)
                .src(imagePath).build();
        member.setProfileImage(imageFile); // 유저 프로필사진 등록
        return fileRepository.save(imageFile);

    }

    public Page<ImageFile> getFiles(int page, int size) {
        return fileRepository.findAll(PageRequest.of(page, size));
    }
}
