package cn_java_controller;

import cn_java_PO.User;
import cn_java_service_impl.PersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@SuppressWarnings("all")
public class PersonalInfoController {

    @Autowired
    private PersonalInfoService personalInfoService;

    /**
     * 根据token得到是否登录的状态信息,如果登录,则返回头像的地址,没有登录则展示没有登录的图标信息
     * @param request
     * @return
     */
    @GetMapping("/avatars")
    public Map<String, Object> getAvatar(HttpServletRequest request){
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        //如果token为null,直接返回没有登录的信息
        if (token == null){
            map.put("loginStatus", false);
            return map;
        }
        //将token带到Service层去redis中校验是否登录,登录则获取头像的地址
        Map<String, Object> avatar = personalInfoService.getAvatar(token);
        //将得到的结果返回给前端
        return avatar;
    }

    /**
     * 实现用户头像的上传
     * @param avatar:前端传过来的裁剪图片的base64格式的数据
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping("/avatars")
    public Map<String, Object> updateAvatar(String avatar, HttpServletRequest request) throws Exception{
        //得到token
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        //如果token为null,直接返回没有登录的信息
        if (token == null){
            map.put("loginStatus", false);
            return map;
        }
        Map<String, Object> resultMap = personalInfoService.uploadAvatar(avatar, token);
        return map;
    }

    //得到用户的全部信息
    @GetMapping
    public Map<String, Object> getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        //如果token为null,直接返回没有登录的信息
        if (token == null){
            map.put("loginStatus", false);
            return map;
        }
        Map<String, Object> allInfo = personalInfoService.getAllInfo(token);
        System.out.println(allInfo);
        return allInfo;
    }

    //更新用户的信息
    @PutMapping
    public Map<String, Object> updateUserInfo(HttpServletRequest request, User user){
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        //如果token为null,直接返回没有登录的信息
        if (token == null){
            map.put("loginStatus", false);
            return map;
        }
        Map<String, Object> allInfo = personalInfoService.updateUserInfo(token, user);
        return allInfo;
    }
}
