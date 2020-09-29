package com.hujy.onlylove.controller;

import com.hujy.onlylove.entity.User;
import com.hujy.onlylove.http.api.HttpApi;
import com.hujy.onlylove.http.api.LocationWeatherApi;
import com.hujy.onlylove.http.resp.LocationWeatherResp;
import com.hujy.onlylove.http.resp.UserInfoResp;
import com.hujy.onlylove.model.param.DecodeUserInfoParam;
import com.hujy.onlylove.model.vo.RespVO;
import com.hujy.onlylove.service.UserService;
import com.hujy.onlylove.util.AesCbcUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-22 12:56
 */
@RestController
@RequestMapping("user")
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    private LocationWeatherApi locationWeatherApi;

    @PostMapping("/userCode")
    public RespVO<String> getUserCode(@RequestBody User user) {

        Integer id = userService.getId(user.getNickName());
        if (id != null) {
            return new RespVO<>(String.valueOf(id));
        }

        userService.save(user);
        return new RespVO<>(String.valueOf(user.getId()));

    }

    @GetMapping("/weather")
    public RespVO<LocationWeatherResp> weather(@RequestParam String lon, @RequestParam String lat) {


        LocationWeatherResp locationWeather = locationWeatherApi.getLocationWeather(lon, lat);

        return new RespVO<>(locationWeather);

    }



  /*  @PostMapping("/decodeUserInfo")
    public RespVO<String> decodeUserInfo(@RequestBody DecodeUserInfoParam param) {

        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = "wxc3fc5ceed9155772";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "fea8c044fc146f99d4791fcd51c87b59";
        //授权（必填）
        String grant_type = "authorization_code";


//        1、向微信服务器 使用登录凭证 code 获取 sessionKey 和 openid
        //请求参数
        String url = "https://api.weixin.qq.com/sns/jscode2session?"+ "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + param.getCode() + "&grant_type=" + grant_type;

        //发送请求
        UserInfoResp resp = httpApi.getForEntity(url, UserInfoResp.class);
        //解析相应内容（转换成json对象）
        //获取会话密钥（sessionKey）
        String sessionKey = resp.getSession_key();
        //用户的唯一标识（openid）
        String openid = resp.getOpenid();

//        2、对encryptedData加密数据进行AES解密
        try {
            String result = AesCbcUtil.decrypt(param.getEncryptedData(), sessionKey, param.getIv(), "UTF-8");
            System.out.println(result);
//            if (null != result && result.length() > 0) {
//                map.put("status", 1);
//                map.put("msg", "解密成功");
//
//                JSONObject userInfoJSON = JSONObject.fromObject(result);
//                Map userInfo = new HashMap();
//                userInfo.put("openId", userInfoJSON.get("openId"));
//                userInfo.put("nickName", userInfoJSON.get("nickName"));
//                userInfo.put("gender", userInfoJSON.get("gender"));
//                userInfo.put("city", userInfoJSON.get("city"));
//                userInfo.put("province", userInfoJSON.get("province"));
//                userInfo.put("country", userInfoJSON.get("country"));
//                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
//                userInfo.put("unionId", userInfoJSON.get("unionId"));
//                map.put("userInfo", userInfo);
//                return map;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RespVO<>();
    }*/
}
