package com.pn.service.pnservice.integral;

import com.pn.common.base.BaseResponse;
import com.pn.common.base.PageParam;
import com.pn.common.reqParams.Integral.IntegralParams;
import com.pn.common.vos.Integral.IntegralVo;

import java.util.Collection;
import java.util.List;

public interface IntegralService {

    /**
     * 查询用户积分明细
     *
     * @param id 主键
     * @return 用户积分明细
     */
    IntegralVo queryById(Long id);

    /**
     * 分页查询用户积分明细列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户积分明细分页列表
     */
    BaseResponse<IntegralVo> queryPageList(IntegralParams bo, PageParam pageQuery);

    /**
     * 分页查询用户总积分
     *
     * @param bo        查询条件
     * @return 用户积分明细分页列表
     */
    Long sum(IntegralParams bo);

    /**
     * 查询符合条件的用户积分明细列表
     *
     * @param bo 查询条件
     * @return 用户积分明细列表
     */
    List<IntegralVo> queryList(IntegralParams bo);

    /**
     * 新增用户积分明细
     *
     * @param bo 用户积分明细
     * @return 是否新增成功
     */
    Boolean insertByBo(IntegralParams bo);

    /**
     * 修改用户积分明细
     *
     * @param bo 用户积分明细
     * @return 是否修改成功
     */
    Boolean updateByBo(IntegralParams bo);

    /**
     * 校验并批量删除用户积分明细信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
