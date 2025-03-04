package com.pn.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.dao.entity.PnAlipayUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PnAlipayUserInfoMapper extends BaseMapper<PnAlipayUserInfo> {
    Boolean exist(@Param("uuid")String uuid);
    PnAlipayUserInfo getByUserId(@Param("userId") Long userId);
}