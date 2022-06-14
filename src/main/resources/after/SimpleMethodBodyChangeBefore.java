package com.random;

public class SimpleMethodBodyChangeBefore {
    public int foo(int x, int y, int z) {
        int res = x + y + z;
        return res;
    }

    public String[] bar(int[] nums, String text) {
        return "hello";
    }

    public void greet() {
        System.out.println("Nice to meet you");
    }
}