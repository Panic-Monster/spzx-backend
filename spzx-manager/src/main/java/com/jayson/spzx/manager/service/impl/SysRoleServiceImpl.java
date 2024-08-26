package com.jayson.spzx.manager.service.impl;

import cn.hutool.Hutool;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jayson.spzx.common.exception.BusinessException;
import com.jayson.spzx.manager.mapper.SysRoleMapper;
import com.jayson.spzx.manager.service.SysRoleService;
import com.jayson.spzx.model.dto.system.SysRoleDto;
import com.jayson.spzx.model.entity.system.SysRole;
import com.jayson.spzx.model.vo.common.ResultCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: Jayson_Y
 * @date: 2024/8/26
 * @project: spzx-parent
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 修改角色
     * @param sysRole 修改角色对象
     */
    @Override
    public void updateSysRole(SysRole sysRole) {
        if(sysRole == null) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        boolean result = this.updateById(sysRole);
        if(!result) {
            throw new BusinessException(ResultCodeEnum.OPERATION_ERROR);
        }
    }

    /**
     * 添加角色
     * @param sysRole 角色对象
     */
    @Override
    public void saveSysRole(SysRole sysRole) {
        boolean result = this.save(sysRole);
        if (!result) {
            throw new BusinessException(ResultCodeEnum.OPERATION_ERROR, "添加角色失败");
        }
    }

    /**
     * 分页条件查询
     * @param sysRoleDto 查询条件
     * @param pageNum 当前页数
     * @param pageSize 每页个数
     * @return Page<SysRole>
     */
    @Override
    public Page<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize) {

        Page<SysRole> page = new Page<>(pageNum, pageSize);
        SysRole sysRoleQuery = new SysRole();
        if(sysRoleDto.getRoleName() != null){
            BeanUtils.copyProperties(sysRoleDto, sysRoleQuery);
        }
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>(sysRoleQuery);
        return this.page(page, queryWrapper);
    }
}
