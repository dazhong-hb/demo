package com.lwz.demo.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwz.demo.common.entity.Test;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 * @description 针对表【test(测试表)】的数据库操作Mapper
 * @createDate 2023-01-05 16:40:09
 * @Entity Test
 */
@Mapper
public interface TestMapper extends BaseMapper<Test> {

}




