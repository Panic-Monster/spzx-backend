package com.jayson.spzx.manager.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jayson.spzx.common.exception.BusinessException;
import com.jayson.spzx.manager.service.SysRoleService;
import com.jayson.spzx.model.dto.system.SysRoleDto;
import com.jayson.spzx.model.entity.system.SysRole;
import com.jayson.spzx.model.vo.common.Result;
import com.jayson.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Jayson_Y
 * @date: 2024/8/26
 * @project: spzx-parent
 */
@Tag(name = "角色接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteById(@PathVariable(value = "roleId") Long roleId) {
        if (roleId == null) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        boolean result = sysRoleService.removeById(roleId);
        if (!result) {
            throw new BusinessException(ResultCodeEnum.OPERATION_ERROR);
        }
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改角色
     * @param sysRole 角色对象
     * @return 统一返回结果对象
     */
    @PutMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加角色
     * @param sysRole 角色对象
     * @return 统一返回结果对象
     */
    @PostMapping("/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 分页条件查询
     * @param sysRoleDto 查询条件
     * @param pageNum 当前页数
     * @param pageSize 每页个数
     * @return Page<SysRole>
     */
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<Page<SysRole>> findByPage(@RequestBody SysRoleDto sysRoleDto,
                                            @PathVariable(value = "pageNum") Integer pageNum,
                                            @PathVariable(value = "pageSize") Integer pageSize) {
        Page<SysRole> sysRolePage = sysRoleService.findByPage(sysRoleDto, pageNum, pageSize);
        return Result.build(sysRolePage, ResultCodeEnum.SUCCESS);
    }
}
