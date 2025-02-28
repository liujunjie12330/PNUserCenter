package com.pn.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 用户头像操作
 */
public interface UserAvatarService extends FileOperationService {
    /**
     *用户头像上传
     */
    String uploadAvatar(MultipartFile file);
}
