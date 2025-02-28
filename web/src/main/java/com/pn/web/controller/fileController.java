package com.pn.web.controller;

import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.utils.ResultUtils;
import com.pn.service.UserAvatarService;
import com.pn.service.impls.image.MinioImageOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author: javadadi
 * @Time: 18:08
 * @ClassName: PictureController
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL + "/file")
public class fileController {
    @Resource(type = MinioImageOperation.class)
    private MinioImageOperation avatarService;

    /**
     * 用户上传头像
     * @param file
     * @return
     */
    @PostMapping("/upload/avatar")
    public BaseResponse<String> uploadAvatar(@RequestParam("avatar")MultipartFile file){
        avatarService.uploadAvatar(file);
        return ResultUtils.success("ok");
    }

    @PostMapping("/upload/articleImage")
    public BaseResponse<String> uploadArticleImage(@Param("image")MultipartFile file){
        return ResultUtils.success(avatarService.uploadImage(file));
    }
}
