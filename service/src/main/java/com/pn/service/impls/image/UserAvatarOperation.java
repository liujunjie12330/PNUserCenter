package com.pn.service.impls.image;

import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.utils.UrlUtils;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.entity.PnUser;
import com.pn.dao.mapper.PnUserMapper;
import com.pn.service.FileOperationService;
import com.pn.service.utils.MinioProperties;
import com.pn.service.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author: javadadi
 * @Time: 12:51
 * @ClassName: AvatarPictureImpl
 */
@Service("userAvatarService")
@Slf4j
public class UserAvatarOperation implements FileOperationService {
    @Resource
    private MinioUtil minioUtil;

    @Resource
    private MinioProperties minioProperties;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Resource
    private PnUserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upload(MultipartFile file) {
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        if (Objects.isNull(currentUser)) {
            throw new BizException(StatusCode.USER_NO_LOGIN);
        }
        String avatarName = currentUser.getUsername() + currentUser.getId();
        try {
            minioUtil.uploadFile(bucketName, file, avatarName, "png");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PnUser pnUser = userMapper.selectById(currentUser.getId());
        if (Objects.isNull(pnUser)){
            throw new BizException(StatusCode.SYSTEM_ERROR);
        }
        String avatarUrl = UrlUtils.coverToUrlByIp("http",minioProperties.getEndPoint(),minioProperties.getPort(),minioProperties.getBucketName(),avatarName);
        log.info("avatarUrl==>{}",avatarUrl);
        pnUser.setAvatar(avatarUrl);
        userMapper.updateById(pnUser);
    }
}
