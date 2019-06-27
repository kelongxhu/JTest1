package com.core.pattern.chain;

/**
 * @author ke.long
 * @since 2019/6/21 14:53
 */
/**
 * 项目主管类
 */
public class Director extends Leader{
    public Director() {
        super(5000);
    }

    @Override
    protected void reply(ProgramApe ape) {
        System.out.println(ape.getApply());
        System.out.println("Director: Of course Yes!");
    }
}
