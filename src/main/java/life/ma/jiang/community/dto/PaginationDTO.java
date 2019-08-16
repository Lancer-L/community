package life.ma.jiang.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private Integer totalPage;
    private Integer currentPage;
    private Integer totalCount;
}
