package com.core.pattern.chain;

/**
 * @author ke.long
 * @since 2019/6/21 14:53
 */
/**
 * 小组长类
 */
public class GroupLeader extends Leader {

    public GroupLeader() {
        super(1000);
    }

    @Override
    protected void reply(ProgramApe ape) {
        System.out.println(ape.getApply());
        System.out.println("GroupLeader: Of course Yes!");
    }
}