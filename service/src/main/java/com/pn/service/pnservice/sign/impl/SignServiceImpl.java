package com.pn.service.pnservice.sign.impl;

import com.pn.common.exception.BizException;
import com.pn.service.pnservice.sign.SignService;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2025/2/10 19:18
 * @Descirption xxx
 */
@Service
public class SignServiceImpl implements SignService {

    String USER_SIGN_PREFIX = "dataSignIn:";

    @Resource
    private RedissonClient redissonClient;
    //用户签到
    @Override
    public Boolean userSignIn(Long userId, LocalDate signDate){
        String signKey = USER_SIGN_PREFIX+ signDate.getYear()+":"+userId;
        RBitSet bitSet = redissonClient.getBitSet(signKey);
        int dayOfYear = signDate.getDayOfYear();
        //假如没有签到过
        if(!bitSet.get(dayOfYear)){
            bitSet.set(dayOfYear,true);
            return true;
        }else {
            //重复签到
            return false;
        }
    }

    @Override
    public List<String> getUserSignInfo(Long userId, Integer year){
        LocalDate signDate = LocalDate.now();
        String signKey = USER_SIGN_PREFIX+ signDate.getYear()+":"+userId;
        RBitSet bitSet = redissonClient.getBitSet(signKey);
        //RBitSet每次都是新会话去get
        BitSet signBitSet = bitSet.asBitSet();
        int dayOfYear = Year.of(year).length();

        //存第几天签到的集合[1,2,5]
        List<String> signDays = new ArrayList<>();
        //存签到的日期[2022-01-01,2023-01-01]
        List<String> resultDate = new ArrayList<>();
        for(int i = 1; i<= dayOfYear;i++){
            //某天是否签到
            if(signBitSet.get(i)){
                signDays.add(String.valueOf(i));
                //转换为日期格式
                LocalDate date = LocalDate.ofYearDay(year, i);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String markDate = date.format(formatter);
                resultDate.add(markDate);
            }
        }
        return resultDate;
    }

    @Override
    public Boolean reSign(Long userId, int year, int month, int day){
        //验证输入的日期是否合法
        LocalDate now = LocalDate.now();
        LocalDate inputDate = null;
        try {
            inputDate = LocalDate.of(year, month, day);
        } catch (Exception e) {
            throw new BizException("补卡输入日期非法");
        }
        //补卡未来不合法
        if(now.isBefore(inputDate)){
            throw new BizException("补卡输入日期非法");
        }
        Boolean res = this.userSignIn(userId, inputDate);
        return res;
    }
}
