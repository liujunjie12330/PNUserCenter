package com.pn.common.exception;


import com.pn.common.enums.StatusCode;

/**
 * 通用模板自定义异常
 *
 * @author: javadadi
 * @Time: 9:59
 * @ClassName: UserCenterException
 */
public class BizException extends RuntimeException {
    /**
     * 错误码
     */
    private final int code;

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(StatusCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BizException(String message) {
        super(message);
        this.code = -1;
    }

    public BizException(StatusCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }

}
