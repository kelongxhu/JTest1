package com.core.pattern.chain;

/**
 * @author ke.long
 * @since 2019/6/21 14:54
 */
/**
 * 老总类
 */
public class Boss extends Leader {
    public Boss() {
        super(40000);
    }

    @Override
    protected void reply(ProgramApe ape) {
        System.out.println(ape.getApply()+"->"+ape.getExpenses());
        System.out.println("Boss: Of course Yes!");
    }
}