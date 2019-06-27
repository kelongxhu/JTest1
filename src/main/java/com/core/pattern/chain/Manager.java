package com.core.pattern.chain;

/**
 * @author ke.long
 * @since 2019/6/21 14:54
 */
/**
 * 部门经理类
 */
public class Manager extends Leader {
    public Manager() {
        super(10000);
    }

    @Override
    protected void reply(ProgramApe ape) {
        System.out.println(ape.getApply());
        System.out.println("Manager: Of course Yes!");
    }
}
