package learn.vk.microservices.dto;

import lombok.Data;

import java.util.List;

@Data
public class BranchDto {
    private Long branchId;
    private String name;
    private List<Long> bookIds;

}
