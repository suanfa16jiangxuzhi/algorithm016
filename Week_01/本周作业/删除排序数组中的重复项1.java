class Solution {
  public int removeDuplicates(int[] nums) {
    int j = 0;
        int len = nums.length;
        for (int i = 0; i < len ; i++) {
            if (nums[j] != nums[i]) {
                nums[++j] = nums[i];
            }
        }
        return j+1;
    }
}