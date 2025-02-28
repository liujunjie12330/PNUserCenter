package com.pn.service;

import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;

import java.io.InputStream;

/**
 * 通用文件操作类
 */
public interface FileOperationService {

    /**
     * 通用文件上传接口
     */
    default String upload(InputStream input, String fileName, String filetype) {
        throw new BizException(StatusCode.SYSTEM_ERROR);
    }

    /**
     * 通用文件下载接口
     */
    default void download(String filename) {
        throw new BizException(StatusCode.SYSTEM_ERROR);
    }
}
