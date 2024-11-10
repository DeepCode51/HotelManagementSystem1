package cn.mafangui.hotel.controller.user;

import cn.mafangui.hotel.entity.User;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.MsgType;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api("用户信息控制器")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 更新用户信息
     * @param userId
     * @param name
     * @param gender
     * @param phone
     * @param email
     * @param address
     * @param idcard
     * @return
     */
    @ApiOperation("用户信息更新")
    @RequestMapping(method = RequestMethod.POST,value = "/update")
    public AjaxResult userUpdate(Integer userId, String name, String gender, String phone,
                                 String email, String address, String idcard, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (!session.getAttribute("userId").equals(userId)){
            return ResponseTool.failed(MsgType.PERMISSION_DENIED);
        }
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        user.setGender(gender);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAddress(address);
        user.setIdcard(idcard);
        if(userService.updateUser(user)==1)
            return ResponseTool.success("修改成功");
        return ResponseTool.success("修改失败，请检查或稍后再试");
    }

    /**
     * 更改密码
     * @param username
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @ApiOperation("修改密码")
    @RequestMapping(method = RequestMethod.POST,value = "/updatePassword")
    public AjaxResult updatePassword(String username,String oldPassword,String newPassword){
        User user = userService.selectByUsernameAndPassword(username,oldPassword);
        if (user == null){
            return ResponseTool.failed("密码不对");
        }
        user.setPassword(newPassword);
        if(userService.updateUser(user)==1)
            return ResponseTool.success("修改成功");
        return ResponseTool.failed("修改失败");
    }

    /**
     *  获取个人资料
     * @param request
     * @return
     */
    @ApiOperation("获取当前登录人信息")
    @RequestMapping(value = "/profile")
    public AjaxResult getProfile(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        User user = userService.selectByUsername(username);
        if(user==null) return ResponseTool.failed("未知错误");
        user.setPassword(null);
        StringBuilder sb = new StringBuilder(user.getIdcard());
        user.setIdcard(sb.toString());
        return ResponseTool.success(user);
    }



    /**
     * 注销
     * @param request
     * @return
     */
    @ApiOperation("退出登录")
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public AjaxResult logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("userId");
        session.removeAttribute("username");
        return ResponseTool.success("注销成功");
    }




}
