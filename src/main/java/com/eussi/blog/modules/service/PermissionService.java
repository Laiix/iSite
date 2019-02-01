package com.eussi.blog.modules.service;

import com.eussi.blog.base.modules.Page;
import com.eussi.blog.modules.po.Permission;
import com.eussi.blog.modules.vo.PermissionTree;

import java.util.List;

/**
 * @author - wangxm
 */
public interface PermissionService {
    /**
     * 分页查询权限
     * @param page 分页对象
     * @param name 权限名称，模糊匹配，如果为null则忽略改查询条件
     */
    Page<Permission> paging(Page page, String name);

    /**
     * 列出所有菜单项
     * @return 菜单列表
     */
    List<PermissionTree> tree();

    /**
     * 查询子菜单项
     * @param parentId 根目录ID
     * @return 菜单列表
     */
    List<PermissionTree> tree(int parentId);

    /**
     * 查询所有权限
     * @return 权限列表
     */
    List<Permission> list();

    /**
     * 根据权限项ID获得权限项信息
     * @param id 权限ID
     * @return Permission
     */
    Permission get(long id);

}