package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.TbUser;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<TbUser> {

    @MapKey("name")
    Map<String, Map<String, Object>> queryAllScore();

    void updateScore(@Param("id") Long id, @Param("step") int step);
}
