package com.pn.service.pnservice.integral;

import com.pn.common.base.BaseResponse;
import com.pn.common.base.PageParam;
import com.pn.common.reqParams.Integral.IntegralWeightParams;
import com.pn.common.vos.Integral.IntegralWeightVo;

import java.util.Collection;
import java.util.List;

public interface IntegralWeightService {

    /**
     * 查询积分计算权重
     *
     * @param id 主键
     * @return 积分计算权重
     */
    IntegralWeightVo queryById(Long id);

    /**
     * 分页查询积分计算权重列表
     *
     * @param bo        查询条件
     * @return 积分计算权重分页列表
     */
    BaseResponse<IntegralWeightVo> queryPageList(IntegralWeightParams bo, PageParam pageParam);

    /**
     * 查询符合条件的积分计算权重列表
     *
     * @param bo 查询条件
     * @return 积分计算权重列表
     */
    List<IntegralWeightVo> queryList(IntegralWeightParams bo);

    /**
     * 新增积分计算权重
     *
     * @param bo 积分计算权重
     * @return 是否新增成功
     */
    Boolean insertByBo(IntegralWeightParams bo);

    /**
     * 修改积分计算权重
     *
     * @param bo 积分计算权重
     * @return 是否修改成功
     */
    Boolean updateByBo(IntegralWeightParams bo);

    /**
     * 校验并批量删除积分计算权重信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
