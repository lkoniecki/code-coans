/**
 * Method dividing two integer numbers using bit operations
 * @version 1.0.0
 * @author lkoniecki
 */
public class BitDivide {
	public int divide(int a, int b) {
		if (a < b) {
			return 0;
		}

		if (a == b) {
			return 1;
		}

		int denom = b;
		int current = 1;
		while (denom <= a) {
			current <<= 1;
			denom <<= 1;
		}

		current >>= 1;
		denom >>= 1;

		int answer = 0;

		while (current != 0) {
			if (a >= denom) {
				a-=denom;
				answer |= current;
			}

			denom >>= 1;
			current >>= 1;
		}

		return answer;
	}

	public static void main(String[] args) {
		BitDivide bitDiv = new BitDivide();
		System.out.println("1/2=" + bitDiv.divide(1, 2));
		System.out.println("2/2=" + bitDiv.divide(2, 2));
		System.out.println("972/5=" + bitDiv.divide(972, 5));
	}
}