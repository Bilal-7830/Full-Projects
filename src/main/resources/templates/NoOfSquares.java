public class NoOfSquares {
    public static int getNoOfSquares(int n) {
        if (n <= 1)
            return n;
        int noOfSquares = 0;
        for (int i = 1; i <= n; i++) {
            noOfSquares += i * i;
        }
        return noOfSquares;
    }

    public static void main(String[] args) {
        int[] chessboardRows = { 1, 2, 3, 5, 6 };
        for (int chessboardRow : chessboardRows) {
            int result = getNoOfSquares(chessboardRow);
            System.out.println(result);
        }
    }

    // @Test
    // public void testFindMax() {
    // assertEquals(4, Calculation.findMax(new int[] { 1, 3, 4, 2 }));
    // assertEquals(-1, Calculation.findMax(new int[] { -12, -1, -3, -4, -2 }));
    // }

    // @Test
    // public void test1() {
    // assertEquals(1, NoOfSquares.getNoOfSquares(1));
    // }

    // @Test
    // public void test2() {
    // assertEquals(5, NoOfSquares.getNoOfSquares(2));
    // }

    // @Test
    // public void test3() {
    // assertEquals(14, NoOfSquares.getNoOfSquares(3));
    // }

    // @Test
    // public void test4() {
    // assertEquals(5, NoOfSquares.getNoOfSquares(2));
    // }

}