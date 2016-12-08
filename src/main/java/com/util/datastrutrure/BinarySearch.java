package com.util.datastrutrure;

import java.util.Arrays;

public class BinarySearch {

    /**
     * 非递归方式
     *
     * @param number
     * @param des
     * @return
     */
    public int search(int[] number, int des) {
        int low = 0;
        int upper = number.length - 1;
        while (low <= upper) {
            int mid = (low + upper) / 2;
            if (number[mid] < des)
                low = mid + 1;
            else if (number[mid] > des)
                upper = mid - 1;
            else
                return mid;
        }
        return -1;
    }

    /**
     * 递归方法实现二分查找法.
     *
     * @param low  数组第一位置
     * @param high 最高
     * @param key  要查找的值.
     * @return 返回值.
     */
    public int BinSearch(int Array[], int low, int high, int key) {
        if (low > high) {
            return -1;
        }
        int mid = (low + high) / 2;
        if (key == Array[mid])
            return mid;
        else if (key < Array[mid])
            //移动low和high
            return BinSearch(Array, low, mid - 1, key);
        else if (key > Array[mid])
            return BinSearch(Array, mid + 1, high, key);
        return -1;
    }

    public void print(int[] array, String warn) {
        System.out.println(warn);
        int length = array.length;
        for (int i = 0; i < length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] array = {1, 4, 2, 6, 7, 3, 5, 9, 8};
        BinarySearch find = new BinarySearch();

        find.print(array, "数组排序前的结果:");

        Arrays.sort(array);// Arrays中的静态方法sort()对数组排序
        find.print(array, "数组排序后的结果:");

        int m = find.search(array, 6);
        int n=find.BinSearch(array,0,array.length-1,6);
        System.out.println("n:"+n);
        if (m != -1)
            System.out.println("\n找到6的数值的索引:" + m);
        else
            System.out.println("\n找不到这个数");
    }
}
