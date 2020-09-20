class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
		
		int index1 = m - 1;
		int index2 = n - 1;
		int len = m + n - 1;
		while( index1 >= 0 && index2 >= 0) {
			
			nums1[len--] = nums1[index1] > nums2[index2] ? nums1[index1--] : nums2[index2--];
		}
		System.arraycopy(nums2,0,nums1,0,index2+1);
		
	}
}