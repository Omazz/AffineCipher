import java.util.Scanner;

public class Cipher {
    private final int ALPHABET_POWER = 26;
    private final char[] ALPHABET = new char[ALPHABET_POWER];
    private final int KEY_A;  
    private final int KEY_B;

    public Cipher(boolean keyGeneration) {
        for (int i = 0; i < ALPHABET_POWER; ++i) {
            ALPHABET[i] = (char) ((int) 'a' +  i);
        }
        if (!keyGeneration) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please, enter keyGeneration a");
            KEY_A = scanner.nextInt();
            System.out.println("Please, enter keyGeneration b");
            KEY_B = scanner.nextInt();
        } else {
            int tmp = 13;
            while (!isMutuallySimple(tmp, ALPHABET_POWER)) {
                tmp = 1 + (int)(Math.random() * (((ALPHABET_POWER - 1) - 1) + 1));
            }
            KEY_A = tmp;
            KEY_B = 1 + (int)(Math.random() * (((ALPHABET_POWER - 1) - 1) + 1));
        }
    }

    public String encryption(String string) {
        System.out.println("Each letter is encrypted as follows: letter = (KEY_A * x + KEY_B) mod ALPHABET_POWER");
        if (isMutuallySimple(KEY_A, ALPHABET_POWER)) {
            char[] array = string.toCharArray();
            char[] lettersArray = new char[numberLetters(array)];
            for(int i = 0, j = 0; i < lettersArray.length; j++) {
                if(array[j] >= 'a' && array[i] <= 'z') {
                    lettersArray[i] = array[j];
                    i++;
                }
            }
            char[] encryptedArray = new char[lettersArray.length];
            for (int i = 0; i < lettersArray.length; ++i) {
                encryptedArray[i] = encryptionLetter(lettersArray[i] - 'a');
            }
            return new String(encryptedArray);
        } else {
            throw new RuntimeException("Key a and ALPHABET_POWER are not mutually simple numbers.");
        }
    }

    public String decryption(String string) {
        System.out.println("Each letter is decrypted as follows: letter = (KEY_A^(-1) * (x - KEY_B)) * mod ALPHABET_POWER");
        if (isMutuallySimple(KEY_A, ALPHABET_POWER)) {
            char[] array = string.toCharArray();

            char[] decryptedArray = new char[string.length()];
            for (int i = 0; i < string.length(); ++i) {

                if (array[i] >= 'a' && array[i] <= 'z') {
                    decryptedArray[i] = decryptionLetter(array[i] - 'a');
                } else {
                    decryptedArray[i] = array[i];
                }

            }
            return new String(decryptedArray);
        } else {
            throw new RuntimeException("Key a and ALPHABET_POWER are not mutually simple numbers.");
        }
    }

    private int numberLetters(char[] text) {
        int counter = 0;
        for (char c : text) {
            if (c >= 'a' && c <= 'z') {
                counter++;
            }
        }
        return counter;
    }

    public char encryptionLetter(int letter) {
        int result = (KEY_A * letter + KEY_B);
        if (result >= ALPHABET_POWER) {
            result %= ALPHABET_POWER;
        }
        return ALPHABET[result];
    }

    public char decryptionLetter(int letter) {
        int result = (findInverseNumber() * (letter - KEY_B));

        if (result < 0) {
            result += ALPHABET_POWER;
        }

        if(result >= ALPHABET_POWER) {
            result %= ALPHABET_POWER;
        }

        return ALPHABET[result];
    }

    public String getFrequencyAnalysis(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        double[] letterFrequency = new double[ALPHABET_POWER];
        int numberOfLetters = 0;
        char[] array = text.toCharArray();
        for (int i = 0; i < text.length(); ++i) {
            if (array[i] >= 'a' && array[i] <= 'z') {
                numberOfLetters++;
            }
        }
        for (int i = 0; i < ALPHABET_POWER; ++i) {
            letterFrequency[i] = ((double) (countFrequencyLetter(text, (char) (i + 'a'))) / numberOfLetters ) * 100;
        }
        stringBuilder.append("Frequency analysis:\n");
        double result = 0;
        for (int i = 0; i < ALPHABET_POWER; ++i) {
            char c = (char) ('a' + i);
            if (letterFrequency[i] > 0) {
                stringBuilder.append(c).append(" -> ").append(String.format("%.2f", letterFrequency[i])).append("%\n");
                result += letterFrequency[i];
            }
        }
        stringBuilder.append("total percent: ").append(String.format("%.2f", result)).append("%");
        return stringBuilder.toString();
    }

    private int countFrequencyLetter(String text, char letter) {
        char[] array = text.toCharArray();
        int counter = 0;
        for (char c : array) {
            if (c == letter) {
                counter++;
            }
        }
        return counter;
    }

    private int findInverseNumber() {
        int result;
        for (result = 0; result < ALPHABET_POWER; ++result) {
            int tmp  = result * KEY_A;
            while (tmp >= ALPHABET_POWER) {
                tmp %= ALPHABET_POWER;
            }
            if (tmp == 1) {
                return result;
            }
        }
        return -1;
    }

    public boolean isMutuallySimple(int firstNumber, int secondNumber) {
        if (firstNumber <= 0 || secondNumber <= 0) {
            return false;
        }
        while (secondNumber != 0) {
            int temp = firstNumber % secondNumber;
            firstNumber = secondNumber;
            secondNumber = temp;
        }
        if (firstNumber == 1) {
            return true;
        }
        return false;
    }
    
}
