public class Main {
    public static void main(String[] args) {
	    Cipher cipher = new Cipher(true);
	    String result = cipher.encryption("hello, world! how are you? i am fine.\n");
        System.out.println(cipher.getFrequencyAnalysis("hello, world! how are you? i am fine.\n"));
    }
}
