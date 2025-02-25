package com.pn.service.impls.article;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.dao.entity.PnColumnInfo;
import com.pn.dao.mapper.PnColumnInfoMapper;
import com.pn.service.ColumnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 专栏接口
 */
@Slf4j
@Service
public class ColumnServiceImpl extends ServiceImpl<PnColumnInfoMapper, PnColumnInfo> implements ColumnService {

}
