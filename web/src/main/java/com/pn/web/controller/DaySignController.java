package com.pn.web.controller;

import com.pn.common.base.BaseResponse;
import com.pn.common.utils.ResultUtils;
import com.pn.service.pnservice.sign.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;


/**
 * @version 1.0
 * @Author：alex
 * @Date：2025/2/10 13:05
 * @Descirption xxx
 */
@RestController
@RequestMapping("sign")
@Slf4j
public class DaySignController {
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private SignService signService;
    //当日签到
    @GetMapping("now")
    public BaseResponse<String> daySignIn(@RequestParam Long userId){
        LocalDate now = LocalDate.now();
        Boolean res = signService.userSignIn(userId,now);
        if(!res){
            log.info("user: "+ userId+" 重复签到");
            return ResultUtils.error(5000,"重复签到");
        }else{
            return ResultUtils.success("签到成功");
        }
    }

    //补签
    @GetMapping("reSign")
    public BaseResponse<String> BackdatedSign(@RequestParam Long userId,
                                              @RequestParam int year,
                                              @RequestParam int month,
                                              @RequestParam int day) {

        Boolean res = signService.reSign(userId, year, month, day);
        if(res){
            return ResultUtils.success("补签成功");
        }else{
            log.info("user: "+ userId+" 重复补签");
            return ResultUtils.error(5000,"重复补签");
        }
    }


    //查询用户签到情况表
    @GetMapping("queryUserSignIn")
    public BaseResponse<List<String>> queryUserSignIn(@RequestParam Long userId,
                                                       @RequestParam int year) {
        List<String> userSignDate = signService.getUserSignInfo(userId, year);
        return ResultUtils.success(userSignDate);
    }



}
