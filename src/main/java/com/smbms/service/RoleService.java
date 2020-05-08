package com.smbms.service;

import com.smbms.pojo.Role;

import java.util.List;

/**
 * description:
 * Created by Ray on 2020-05-05
 */
public interface RoleService {
    List<Role> getRoleList()throws Exception;
}
