package com.hujy.onlylove.service;

import com.hujy.onlylove.entity.Bill;
import com.hujy.onlylove.entity.Reward;

import java.util.List;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-17 11:21
 */
public interface TransactionalService {

    void settlementSave(List<Reward> rewards, List<Bill> bills, String userCode, int money);

    void cash(List<Bill> bills, String userCode, int money);
}
