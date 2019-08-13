package com.core.datastruct.search;

/**
 * 时间复杂度: O(log n)
 * @author ke.long
 * @since 2019/7/3 9:36
 */
public class BinarySearch {
    //二分查找，递归版本
    int binarySearch2(int a[], int value, int low, int high) {
        if (low > high) {
            return -1;
        }
        int mid = low + (high - low) / 2;
        if (a[mid] == value) {
            return mid;
        }
        if (a[mid] > value) {
            return binarySearch2(a, value, low, mid - 1);
        }
        if (a[mid] < value) {
            return binarySearch2(a, value, mid + 1, high);
        }
        return -1;
    }

    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch();
        int[] a = {1, 3, 4, 5, 7};
        int offset = binarySearch.binarySearch2(a, 5, 0, 5);
        System.out.println(offset);
    }
}
