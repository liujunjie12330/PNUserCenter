package com.pn.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.dao.dto.login.UsernamePasswordDTO;
import com.pn.dao.entity.PnUser;
import org.apache.ibatis.annotations.Param;

public interface PnUserMapper extends BaseMapper<PnUser> {
    void addUser(PnUser user);
    UsernamePasswordDTO getByUsername(@Param("username")String username);
    PnUser getUserByUsername(@Param("username")String username);
    void deletionUser(PnUser user);
}