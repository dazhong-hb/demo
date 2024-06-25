package com.lwz.demo.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageDTO {

    /**
     * 页码
     */
    private Integer page;

    /**
     * 每页数量
     */
    private Integer pageSize;

}
