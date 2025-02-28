package com.pn.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文章图片操作
 */
public interface ArticleImageService extends  FileOperationService{

    String uploadImage(MultipartFile file);
}
