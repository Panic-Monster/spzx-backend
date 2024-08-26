package com.jayson.spzx.manager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jayson.spzx.model.dto.system.SysRoleDto;
import com.jayson.spzx.model.entity.system.SysRole;

/**
 * @author: Jayson_Y
 * @date: 2024/8/26
 * @project: spzx-parent
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 修改角色
     * @param sysRole 修改角色对象
     */
    void updateSysRole(SysRole sysRole);

    /**
     * 添加角色
     * @param sysRole 角色对象
     */
    void saveSysRole(SysRole sysRole);

    /**
     * 分页条件查询
     * @param sysRoleDto 查询条件
     * @param pageNum 当前页数
     * @param pageSize 每页个数
     * @return Page<SysRole>
     */
    Page<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize);



}
