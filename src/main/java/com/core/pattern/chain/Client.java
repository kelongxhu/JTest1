package com.core.pattern.chain;

/**
 * @author ke.long
 * @since 2019/6/21 14:54
 */
/**
 * 场景模拟类
 */
public class Client {
    public static void main(String[] args) {
        /*
         * 先来一个程序猿 这里给他一个三万以内的随机值表示需要申请的差旅费
         */
        ProgramApe ape = new AndroidApe(30000);

        /*
         * 再来四个老大
         */
        Leader leader = new GroupLeader();
        Leader director = new Director();
        Leader manager = new Manager();
        Leader boss = new Boss();

        /*
         * 设置老大的上一个老大
         */
        leader.setLeader(director);
        director.setLeader(manager);
        manager.setLeader(boss);

        // 处理申请
        leader.handleRequest(ape);
    }
}
