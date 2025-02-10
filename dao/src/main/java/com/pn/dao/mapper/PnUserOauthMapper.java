package com.pn.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.dao.entity.PnUserOauth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface PnUserOauthMapper extends BaseMapper<PnUserOauth> {
    PnUserOauth getByUuidAndSource(@Param("uuid") String uuid, @Param("source") String source);
}