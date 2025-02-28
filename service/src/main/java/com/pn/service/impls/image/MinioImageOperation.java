package com.pn.service.impls.image;

import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.utils.UrlUtils;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.entity.PnUser;
import com.pn.dao.mapper.PnUserMapper;
import com.pn.service.ArticleImageService;
import com.pn.service.FileOperationService;
import com.pn.service.UserAvatarService;
import com.pn.service.utils.MinioProperties;
import com.pn.service.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * mini文件上传-->项目默认的文件上传路径
 */
@Service("minioImageOperation")
@Slf4j
public class MinioImageOperation implements FileOperationService, UserAvatarService, ArticleImageService {
    @Resource
    private MinioUtil minioUtil;

    @Resource
    private MinioProperties minioProperties;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${file.access}")
    private List<String> fileAccess;

    @Resource
    private PnUserMapper userMapper;

    /**
     * 用户上传头像
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadAvatar(MultipartFile file) {
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        String avatarName = currentUser.getUsername() + currentUser.getId();
        try {
            minioUtil.uploadFile(bucketName, file, avatarName, "png");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PnUser pnUser = userMapper.selectById(currentUser.getId());
        if (Objects.isNull(pnUser)) {
            throw new BizException(StatusCode.SYSTEM_ERROR);
        }
        String avatarUrl = UrlUtils.coverToUrlByIp("http", minioProperties.getEndPoint(), minioProperties.getPort(), minioProperties.getBucketName(), avatarName);
        log.info("avatarUrl==>{}", avatarUrl);
        pnUser.setAvatar(avatarUrl);
        userMapper.updateById(pnUser);
        return avatarUrl;
    }

    /**
     * 文章图片上传
     */
    @Override
    public String uploadImage(MultipartFile file) {
        //这里先吧图片放到缓存里面去，等整个文章上传完毕，在统一进行上传
        String name = file.getOriginalFilename();
        String contentType = file.getContentType();
        log.info("文件上传,filename===>{},filetype===>{}", name, contentType);
        return UrlUtils.coverToUrlByIp("http", "118.31.2.9", "3306", "pictures");
    }

    @Override
    public void download(String filename) {

    }

    @Override
    public String upload(InputStream input, String fileName, String filetype) {
        return null;
    }
}
