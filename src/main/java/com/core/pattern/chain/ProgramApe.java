package com.core.pattern.chain;

/**
 * @author ke.long
 * @since 2019/6/21 14:48
 */
public abstract class ProgramApe {
    /**
     * 获取程序员具体的差旅费用
     *
     * @return 要多少钱
     */
    public abstract int getExpenses();

    /**
     * 获取差旅费申请
     *
     * @return Just a request
     */
    public abstract String getApply();
}
