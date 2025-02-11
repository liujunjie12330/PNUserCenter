package com.pn.service;

import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: javadadi
 * @Time: 12:51
 * @ClassName: PictureOperationService
 */
public interface FileOperationService {
    /**
     * 通用文件上传接口
     */
   default void upload(MultipartFile file){
       throw new  BizException(StatusCode.SYSTEM_ERROR);
   }

    /**
     * 通用文件下载接口
     */
   default void download(String filename){
       throw new  BizException(StatusCode.SYSTEM_ERROR);
   }
}
