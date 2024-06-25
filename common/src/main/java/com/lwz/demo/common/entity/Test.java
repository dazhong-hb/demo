package com.lwz.demo.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @TableName test
 */
@TableName(value = "test")
@Data
public class Test {

    private Integer id;

    private String test;

    private Date createTime;

    private Date updateTime;

}