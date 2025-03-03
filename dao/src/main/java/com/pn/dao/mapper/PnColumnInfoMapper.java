package com.pn.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pn.dao.entity.PnColumnInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PnColumnInfoMapper extends BaseMapper<PnColumnInfo> {

    PnColumnInfo getByArticleId(@Param("articleId")Long id);
}