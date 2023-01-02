package Be_30.Project.file.repository;

import Be_30.Project.file.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<ImageFile, Long> {

    List<ImageFile> findByDefaultImageIsTrue();
}
