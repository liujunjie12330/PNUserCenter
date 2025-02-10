package com.pn.service.pnservice.sign;

import java.time.LocalDate;
import java.util.List;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2025/2/10 19:17
 * @Descirption xxx
 */
public interface SignService {

    //用户签到
    Boolean userSignIn(Long userId, LocalDate signDate);

    List<String> getUserSignInfo(Long userId, Integer year);

    Boolean reSign(Long userId, int year, int month, int day);
}
