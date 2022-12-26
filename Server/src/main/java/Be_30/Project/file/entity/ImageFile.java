package Be_30.Project.file.entity;

import Be_30.Project.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ImageFile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imageId;
    private String fileName;
    private String src;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;
}
