package com.core.pattern.chain;

/**
 * @author ke.long
 * @since 2019/6/21 14:51
 */
public abstract class Leader {
    /**
     * 领导人抽象类
     */
    private int expenses;// 当前领导能批复的金额
    private Leader mSuperiorLeader;// 上级领导

    /**
     * 含参构造方法
     *
     * @param expenses
     *            当前领导能批复的金额
     */
    public Leader(int expenses) {
        this.expenses = expenses;
    }

    /**
     * 回应程序猿
     *
     * @param ape
     *            具体的程序猿
     */
    protected abstract void reply(ProgramApe ape);

    /**
     * 处理请求
     *
     * @param ape
     *            具体的程序猿
     */
    public void handleRequest(ProgramApe ape) {
        /*
         * 如果说程序猿申请的money在当前领导的批复范围内
         */
        if (ape.getExpenses() <= expenses) {
            // 那么就由当前领导批复即可
            reply(ape);
        } else {
            /*
             * 否则看看当前领导有木有上级
             */
            if (null != mSuperiorLeader) {
                // 有的话简单撒直接扔给上级处理即可
                mSuperiorLeader.handleRequest(ape);
            } else {
                // 没有上级的话就批复不了老……不过在这个场景中总会有领导批复的淡定
                System.out.println("Goodbye my money......");
            }
        }
    }

    /**
     * 为当前领导设置一个上级领导
     *
     * @param superiorLeader
     *            上级领导
     */
    public void setLeader(Leader superiorLeader) {
        this.mSuperiorLeader = superiorLeader;
    }
}
