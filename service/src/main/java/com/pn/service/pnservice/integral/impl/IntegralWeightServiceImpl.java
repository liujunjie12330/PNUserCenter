package com.pn.service.pnservice.integral.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.base.PageParam;
import com.pn.common.reqParams.Integral.IntegralWeightParams;
import com.pn.common.vos.Integral.IntegralWeightVo;
import com.pn.dao.dto.IntegralDTO;
import com.pn.dao.entity.IntegralWeight;
import com.pn.dao.mapper.IntegralWeightMapper;
import com.pn.service.pnservice.integral.IntegralService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class IntegralWeightServiceImpl implements IntegralService {


    private final IntegralWeightMapper baseMapper;

    /**
     * 查询积分计算权重
     *
     * @param id 主键
     * @return 积分计算权重
     */
    @Override
    public IntegralWeightVo queryById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 分页查询积分计算权重列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分计算权重分页列表
     */
//    @Override
//    public <IntegralWeightVo> queryPageList(IntegralWeightParams bo, PageParam pageQuery) {
//
//    }

    /**
     * 查询符合条件的积分计算权重列表
     *
     * @param bo 查询条件
     * @return 积分计算权重列表
     */
    @Override
    public List<IntegralWeightVo> queryList(IntegralWeightParams bo) {
        return null;
    }

    private LambdaQueryWrapper<IntegralWeight> buildQueryWrapper(IntegralWeightParams bo) {
        return null;
    }

    /**
     * 新增积分计算权重
     *
     * @param bo 积分计算权重
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(IntegralWeightParams bo) {
        return null;
    }

    /**
     * 修改积分计算权重
     *
     * @param bo 积分计算权重
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(IntegralWeightParams bo) {
        return null;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IntegralWeight entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除积分计算权重信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        return null;
    }

    @Override
    public IntegralDTO getIntegral(String key) {
        return null;
    }
}
