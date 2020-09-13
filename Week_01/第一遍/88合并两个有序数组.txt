//思路：先把nums2添加进nums1的末尾，然后排序，这种方法比较挫
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index = 0;
        for (int i = m ; i < m + n ;i++) {
            nums1[i] = nums2[index];
            index++;
        }
        for (int i = 0;i < m + n; i++){
            boolean flag = false;
            for (int j = 0;j < m + n - i - 1;j++) {
                if (nums1[j] > nums1[j+1]) {
                    int temp = nums1[j];
                    nums1[j] = nums1[j+1];
                    nums1[j+1] = temp;
                    flag = true;
                }
            }
                if (!flag) break;
        }
    }
}
标签：从后向前数组遍历
因为 nums1 的空间都集中在后面，所以从后向前处理排序的数据会更好，节省空间，一边遍历一边将值填充进去
设置指针 len1 和 len2 分别指向 nums1 和 nums2 的有数字尾部，从尾部值开始比较遍历，同时设置指针 len 指向 nums1 的最末尾，每次遍历比较值大小之后，则进行填充
当 len1<0 时遍历结束，此时 nums2 中海油数据未拷贝完全，将其直接拷贝到 nums1 的前面，最后得到结果数组
时间复杂度：O(m+n)

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
      int len1 = m - 1;
      int len2 = n - 1;
      int len = n + m - 1;
      while (len1 >= 0 && len2 >= 0) {
          nums1[len--] = nums1[len1] > nums2[len2] ? nums1[len1--] : nums2[len2--];
      }
      System.arraycopy(nums2, 0, nums1, 0, len2 + 1);
}
}