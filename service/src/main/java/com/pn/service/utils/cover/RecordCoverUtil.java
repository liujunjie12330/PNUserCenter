package com.pn.service.utils.cover;

import com.pn.common.enums.PayTypeEnum;
import com.pn.common.enums.TransactionStatus;
import com.pn.dao.entity.PnArticlePayRecord;
import com.pn.dao.entity.PnOrder;
import com.pn.dao.entity.PnTransactions;
import com.pn.service.impls.pay.dto.PayBaseDto;
import com.pn.service.utils.id.IdUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 支付回调转换工具
 */
public class RecordCoverUtil {

    public static PnTransactions parmaCoverToPT(Map<String, String[]> parameterMap, Long pnUserId) {
        PnTransactions transaction = new PnTransactions();

        // 支付用户ID
        transaction.setUserId(pnUserId);

        // 交易 ID（支付宝交易号）
        transaction.setTransactionId(getFirstValue(parameterMap, "trade_no"));

        // 订单号（商户订单号）
        transaction.setOrderId(getFirstValue(parameterMap, "out_trade_no"));

        // 买家 ID
        transaction.setBuyerId(getFirstValue(parameterMap, "buyer_id"));

        // 支付金额
        String paymentAmountStr = getFirstValue(parameterMap, "total_amount");
        transaction.setPaymentAmount(paymentAmountStr != null ? new BigDecimal(paymentAmountStr) : BigDecimal.ZERO);

        // 交易状态
        transaction.setTradeStatus(getFirstValue(parameterMap, "trade_status"));

        // 支付时间
        transaction.setPaymentTime(parseDate(getFirstValue(parameterMap, "gmt_payment")));

        // 通知时间
        transaction.setNotifyTime(parseDate(getFirstValue(parameterMap, "notify_time")));

        // 记录创建时间
        transaction.setCreateTime(new Date());

        // 记录更新时间
        transaction.setUpdateTime(new Date());

        return transaction;
    }

    public static PnArticlePayRecord paramCoverToPA(Map<String, String[]> parameterMap,
                                                    String payUuid,
                                                    Long payPnUserId,
                                                    String receiveUuid,
                                                    Long receivePnUserId,
                                                    Long articleId)
    {
        PnArticlePayRecord payRecord = new PnArticlePayRecord();
        payRecord.setPayUserId(payUuid);
        payRecord.setPayPnUserId(payPnUserId);
        payRecord.setReceiveUserId(receiveUuid);
        payRecord.setReceivePnUserId(receivePnUserId);
        payRecord.setArticleId(articleId);
        String tradeStatus = getFirstValue(parameterMap, "trade_status");
        payRecord.setPayStatus(StringUtils.equalsIgnoreCase(tradeStatus, TransactionStatus.TRADE_SUCCESS.getName())?0:1);
        String paymentAmountStr = getFirstValue(parameterMap, "total_amount");
        payRecord.setPayAmount(paymentAmountStr);
        payRecord.setPayWay("alipay");
        payRecord.setOutBizNo(getFirstValue(parameterMap, "out_trade_no"));
        payRecord.setThirdTransCode(getFirstValue(parameterMap, "trade_no"));
        payRecord.setPayCallbackTime(new Date());
        payRecord.setCreateBy(payPnUserId);
        payRecord.setUpdateBy(payPnUserId);
        return payRecord;
    }

    public static PnOrder coverToPnOrder(PayBaseDto dto, PayTypeEnum typeEnum,Long userId)
    {
        PnOrder pnOrder = new PnOrder();
        pnOrder.setPayUserId(userId);
        Long articleId = IdUtil.parseIdFromPayCode(dto.getOutBizNo());
        pnOrder.setArticleId(articleId);
        pnOrder.setOutBizNo(dto.getOutBizNo());
        pnOrder.setTransAmount(dto.getTransAmount());
        pnOrder.setTitle(dto.getTitle());
        pnOrder.setRemark(dto.getRemark());
        pnOrder.setType(typeEnum.getType());
        pnOrder.setStatus(1);
        pnOrder.setMsg("");
        pnOrder.setBody("");
        pnOrder.setCreateBy(userId);
        pnOrder.setUpdateBy(userId);
        return pnOrder;
    }

    /**
     * 获取 Map<String, String[]> 参数中的第一个值
     */
    private static String getFirstValue(Map<String, String[]> parameterMap, String key) {
        String[] values = parameterMap.get(key);
        return (values != null && values.length > 0) ? values[0] : null;
    }

    /**
     * 解析字符串时间为 Date
     */
    private static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
}
