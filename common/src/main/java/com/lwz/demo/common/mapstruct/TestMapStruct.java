package com.lwz.demo.common.mapstruct;

import com.lwz.demo.common.entity.Test;
import com.lwz.demo.common.vo.TestVO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TestMapStruct {

    TestVO toVO(Test test);

}
