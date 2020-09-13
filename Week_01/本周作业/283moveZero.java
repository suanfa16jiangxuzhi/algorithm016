class Solution {
    public void moveZeroes(int[] nums) {
    int j = 0;//用于记录非零元素的位置
        for (int i = 0;i<nums.length;i++) {
            if (nums[i] != 0) {
                nums[j] = nums[i];
                if (i != j) {
                    nums[i] = 0;
                }
                j++;
            }
        }
    }
}