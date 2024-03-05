package com.coding.blog.service.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coding.blog.service.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coding.blog.service.vo.UserDetailVo;
import com.coding.blog.service.vo.UserQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author blockCloth
 * @since 2023-12-04
 */
public interface UsersMapper extends BaseMapper<Users> {

    UserDetailVo getUserDetail(Integer userId);

    IPage<Users> findByPage(IPage<Users> page, @Param(Constants.WRAPPER) Wrapper<UserQueryVo> wrapper);

}
