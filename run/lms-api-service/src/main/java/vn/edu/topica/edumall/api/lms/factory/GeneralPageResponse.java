package vn.edu.topica.edumall.api.lms.factory;

import lombok.*;
import org.springframework.data.domain.Page;
import vn.edu.topica.edumall.data.model.File;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralPageResponse<T> implements Serializable {

    private List<T> content;

    private long totalElements;

    private boolean last;

    private boolean first;

    private int numberOfElements;

    private int pageSize;

    private int pageNum;

    public GeneralPageResponse toResponse(Page<T> page, int offset) {
        return GeneralPageResponse.<T>builder().content(page.getContent())
                .numberOfElements(page.getNumberOfElements())
                .pageSize(page.getSize())
                .pageNum(page.getNumber())
                .first(page.isFirst())
                .last(page.getTotalElements() <= (offset + (page.getNumber()+ 1) * page.getSize()))
                .totalElements(page.getTotalElements()).build();

    }

}
