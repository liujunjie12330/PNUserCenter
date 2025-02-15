package com.pn.service.pnservice.integral.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pn.common.event.IntegralEvent;
import com.pn.common.reqParams.Integral.IntegralParams;
import com.pn.common.vos.Integral.IntegralVo;
import com.pn.dao.entity.Integral;
import com.pn.dao.mapper.IntegralMapper;
import com.pn.service.pnservice.integral.IntegralService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IntegralServiceImpl implements IntegralService {

    private final IntegralMapper baseMapper;

    /**
     * 查询用户积分明细
     *
     * @param id 主键
     * @return 用户积分明细
     */
    @Override
    public IntegralVo queryById(Long id){
        return baseMapper.selectById(id);
    }



    /**
     * 分页查询用户积分明细列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户积分明细分页列表
     */
//    @Override
//    public <IntegralVo> queryPageList(IntegralParams bo, PageParam pageQuery) {
//        return null;
//    }

    /**
     * 查询符合条件的用户积分明细列表
     *
     * @param bo 查询条件
     * @return 用户积分明细列表
     */
    @Override
    public List<IntegralVo> queryList(IntegralParams bo) {
        return null;
    }

    private LambdaQueryWrapper<Integral> buildQueryWrapper(IntegralParams bo) {
        return null;
    }

    /**
     * 新增用户积分明细
     *
     * @param bo 用户积分明细
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(IntegralParams bo) {
        return null;
    }

    /**
     * 修改用户积分明细
     *
     * @param bo 用户积分明细
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(IntegralParams bo) {
        return null;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Integral entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户积分明细信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        return null;
    }

    /**
     * 增加积分
     *
     * @param integralEvent 操作积分事件
     */
    @Async
    @EventListener
    public void recordOper(IntegralEvent integralEvent) {

    }
}
