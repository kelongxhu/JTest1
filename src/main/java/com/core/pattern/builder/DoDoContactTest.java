package com.core.pattern.builder;

/**
 * @author ke.long
 * @since 2019/3/25 10:31
 */
public class DoDoContactTest {
    public static void main(String[] args) {
        DoDoContact ddc = new DoDoContact.Builder("Ace").age(10)
                .address("beijing").build();
        System.out.println("name=" + ddc.getName() + "age =" + ddc.getAge()
                + "address" + ddc.getAddress());
    }
}
