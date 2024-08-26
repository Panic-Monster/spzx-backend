package com.jayson.spzx.manager.controller;

import com.jayson.spzx.manager.service.SysUserService;
import com.jayson.spzx.manager.service.ValidateCodeService;
import com.jayson.spzx.model.dto.system.LoginDto;
import com.jayson.spzx.model.entity.system.SysUser;
import com.jayson.spzx.model.vo.common.Result;
import com.jayson.spzx.model.vo.common.ResultCodeEnum;
import com.jayson.spzx.model.vo.system.LoginVo;
import com.jayson.spzx.model.vo.system.ValidateCodeVo;
import com.jayson.spzx.util.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Jayson_Y
 * @date: 2024/8/24
 * @project: spzx-parent
 */
@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private ValidateCodeService validateCodeService;

    /**
     * 用户登录
     * @param loginDto 登录请求参数
     * @return Result<loginVo>
     */
    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    // 获取当前登录用户信息
//    @Operation(summary = "获取用户信息")
//    @GetMapping("/getUserInfo")
//    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
//        // String token = request.getHeader("token"); HttpServletRequest request
//        SysUser sysUser = sysUserService.getUserInfo(token);
//        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
//    }
    @Operation(summary = "获取用户信息")
    @GetMapping("/getUserInfo")
    public Result<SysUser> getUserInfo() {
        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS);
    }

    // 退出登录
    @Operation(summary = "退出登录")
    @GetMapping("/logout")
    public Result logout(@RequestHeader(name = "token") String token) {
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
