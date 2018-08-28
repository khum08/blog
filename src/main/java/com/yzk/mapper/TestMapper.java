package com.yzk.mapper;

import com.yzk.model.response.Test;
<<<<<<< HEAD

=======
>>>>>>> 7becfc58bc36f94548444a70c1aeb87dbeb3e5f0
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface TestMapper {

    @Select("select * from test")
    Test selectTest();
}
