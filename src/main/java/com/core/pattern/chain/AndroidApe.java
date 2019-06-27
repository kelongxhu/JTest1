package com.core.pattern.chain;

/**
 * @author ke.long
 * @since 2019/6/21 14:49
 */
public class AndroidApe extends ProgramApe {

    private int expenses;// 声明整型成员变量表示出差费用
    private String apply = "爹要点钱出差";// 声明字符串型成员变量表示差旅申请

    /*
     * 含参构造方法
     */
    public AndroidApe(int expenses) {
        this.expenses = expenses;
    }

    @Override
    public int getExpenses() {
        return expenses;
    }

    @Override
    public String getApply() {
        return apply;
    }
}
