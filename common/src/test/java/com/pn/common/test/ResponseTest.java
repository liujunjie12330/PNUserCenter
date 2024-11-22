package com.pn.common.test;

import com.google.gson.Gson;
import com.pn.common.base.BaseResponse;
import com.pn.common.utils.ResultUtils;

/**
 * @author: javadadi
 * @Time: 13:20
 * @ClassName: ResponBonseTest
 */
public class ResponseTest {
    public static void main(String[] args) {
        BaseResponse<String> ol = ResultUtils.success("ok");
        Gson gson = new Gson();

        System.out.println(gson.toJson(ol));
    }
}
