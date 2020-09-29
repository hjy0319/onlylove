package com.hujy.onlylove.service.impl;

import com.hujy.onlylove.entity.Bill;
import com.hujy.onlylove.entity.Reward;
import com.hujy.onlylove.mapper.BillMapper;
import com.hujy.onlylove.mapper.RewardMapper;
import com.hujy.onlylove.mapper.WalletMapper;
import com.hujy.onlylove.service.TransactionalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-17 11:22
 */
@Service
public class TransactionalServiceImpl implements TransactionalService {

    @Resource
    private RewardMapper rewardMapper;

    @Resource
    private BillMapper billMapper;

    @Resource
    private WalletMapper walletMapper;

    @Transactional
    @Override
    public void settlementSave(List<Reward> rewards, List<Bill> bills, String userCode, int money) {
        if (!CollectionUtils.isEmpty(rewards)) {
            rewardMapper.batchInsert(rewards);
        }

        if (!CollectionUtils.isEmpty(bills)) {
            billMapper.batchInsert(bills);
        }

        if (money != 0) {
            walletMapper.updateMoney(userCode, money);
        }
    }

    @Transactional
    @Override
    public void cash(List<Bill> bills, String userCode, int money) {
        billMapper.batchInsert(bills);
        walletMapper.updateMoney(userCode, money);
    }
}
