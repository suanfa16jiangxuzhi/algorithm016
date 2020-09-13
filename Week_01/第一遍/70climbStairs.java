class Solution {
    public int climbStairs(int n) {
    if (n < 3) return n;
    int[] feibonaqie = new int[n+1];
    feibonaqie[1] = 1;
    feibonaqie[2] = 2;
    for (int i = 3;i <= n; i++) {
        feibonaqie[i] = feibonaqie[i-1] + feibonaqie[i-2];
    }
    return feibonaqie[n];
    }
}