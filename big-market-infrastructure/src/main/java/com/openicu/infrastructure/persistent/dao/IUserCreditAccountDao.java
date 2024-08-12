package com.openicu.infrastructure.persistent.dao;

import com.myapp.middleware.db.router.annotation.DBRouter;
import com.openicu.infrastructure.persistent.po.UserCreditAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: 云奇迹
 * @date: 2024/8/9
 */
@Mapper
public interface IUserCreditAccountDao {

    /**
     * 更新用户信用账户的金额
     *
     * @param userCreditAccountReq 包含需要更新的用户信用账户信息的对象
     * @return 更新的行数
     */
    int updateAddAmount(UserCreditAccount userCreditAccountReq);

    /**
     * 插入新的用户信用账户记录
     *
     * @param userCreditAccountReq 包含新的用户信用账户信息的对象
     */
    void insert(UserCreditAccount userCreditAccountReq);

    /**
     * 查询用户信用账户信息
     *
     * @param userCreditAccountReq 包含查询条件的用户信用账户信息的对象
     * @return 用户信用账户信息
     */
    UserCreditAccount queryUserCreditAccount(UserCreditAccount userCreditAccountReq);
}
