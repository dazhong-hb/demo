package com.lwz.demo.common.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @Classname TestDTO
 * @Descripyion TOOD
 * @Date 2024/06/18  15:05:11
 * @Created by Administrator
 * @Author liuwanzhong
 * @Email liuwanzhong@finlwz.com
 */
@Data
@Valid
public class TestDTO {

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotBlank(message = "描述不能为空")
    private String description;
}
