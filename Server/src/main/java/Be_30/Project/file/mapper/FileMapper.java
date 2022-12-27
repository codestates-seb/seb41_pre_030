package Be_30.Project.file.mapper;

import Be_30.Project.file.dto.FileDto;
import Be_30.Project.file.entity.ImageFile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {

    FileDto.Response fileToFileResponseDto(ImageFile file);

    List<FileDto.Response> filesToFileResponseDtos(List<ImageFile> files);

}
