package com.pn.web.controller;

import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.utils.ResultUtils;
import com.pn.service.FileOperationService;
import com.pn.service.impls.image.UserAvatarOperation;
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
@RequestMapping(PNUserCenterConstant.BASE_URL + "/picture")
public class fileController {
    @Resource(type = UserAvatarOperation.class)
    private FileOperationService avatarService;

    /**
     * 用户上传头像
     * @param file
     * @return
     */
    @PostMapping("/upload/avatar")
    public BaseResponse<String> uploadAvatar(@RequestParam("avatar")MultipartFile file){
        avatarService.upload(file);
        return ResultUtils.success("ok");
    }
}
