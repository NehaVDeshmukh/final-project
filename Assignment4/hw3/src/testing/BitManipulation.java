package testing;

public class BitManipulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long l = 0xFF;
		System.out.println(Long.toBinaryString(l));
		System.out.println(Long.toBinaryString(l &= ~8));
		System.out.println(Long.toBinaryString(l & ~24));
	}

}
