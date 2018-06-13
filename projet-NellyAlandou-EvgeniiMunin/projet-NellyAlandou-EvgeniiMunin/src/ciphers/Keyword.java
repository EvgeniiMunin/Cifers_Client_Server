package ciphers;

/**
 * 
 * @author Nelly Alandou
 * @author Evgenii Munin
 *
 */
public class Keyword extends Cipher implements DecryptEncrypt {

	private String key;
	private String alphabet_up = new String();
	private String alphabet_low = new String();

	
	/**
	 * @param key : a key of Keyword cipher 
	 */
	public Keyword(String key) {
		this.key = key;

		// changing of alphabet
		int index_low = 97;
		int index_up = 65;
		int flag = 0;

		String alphabet_up1 = this.key.toUpperCase();
		String alphabet_low1 = this.key.toLowerCase();

		// convert alphabet low
		for (int i = 0; i <= 26 - this.key.length(); i++) {
			// presence of key letter in alphabet
			for (int j = 0; j < this.key.length(); j++) {
				if (index_low == this.key.toLowerCase().codePointAt(j)) {
					flag = 1;
				}
			}
			if (flag != 0) {
				index_low = index_low + 1;
				flag = 0;
				i = i - 1;
			} else {
				char char_low = (char) index_low;
				alphabet_low1 = alphabet_low1 + char_low;
				index_low = index_low + 1;
				flag = 0;
			}
		}
		//delete non alphabetic symbols
		for (int i = 0; i < alphabet_low1.length(); i++) {
			if (alphabet_low1.charAt(i) >= 'a' && alphabet_low1.charAt(i) <= 'z') {
				alphabet_low = alphabet_low + alphabet_low1.charAt(i);
			}
		}

		// convert alphabet up
		for (int i = 0; i <= 26 - this.key.length(); i++) {
			// presence of key letter in alphabet
			for (int j = 0; j < key.length(); j++) {
				if (index_up == this.key.toUpperCase().codePointAt(j)) {
					flag = 1;
				}
			}
			if (flag != 0) {
				index_up = index_up + 1;
				flag = 0;
				i = i - 1;
			} else {
				char char_up = (char) index_up;
				alphabet_up1 = alphabet_up1 + char_up;
				index_up = index_up + 1;
				flag = 0;
			}
		}
		// delete non alphabetic symbols
		for (int i = 0; i < alphabet_up1.length(); i++) {
			if (alphabet_up1.charAt(i) >= 'A' && alphabet_up1.charAt(i) <= 'Z') {
				alphabet_up = alphabet_up + alphabet_up1.charAt(i);
			}
		}
	}
	
	
	/**
	 * @param clear : a clear string 
	 * @return the clear string encrypted with Keyword
	 */
	public String encrypt(String clear) {
		String str_encrypted;
		char[] charact;
		char[] charact_enc = new char[clear.length()];
		int code_char_n, code_char;
		char char_temp;
		int index;

		// From string to char. Initial string
		clear = super.normaliser(clear);
		charact = clear.toCharArray();

		// Encrypting
		for (int i = 0; i < clear.length(); i++) {
			// Calcul of Unicode number of char encode
			if (charact[i] >= 'a' && charact[i] <= 'z') {

				code_char = (int) charact[i];
				index = code_char - 97;
				code_char_n = alphabet_low.codePointAt(index);

				// Letter from Unicode number to Char
				char_temp = (char) code_char_n;
				charact_enc[i] = char_temp;
			} else {
				if (charact[i] >= 'A' && charact[i] <= 'Z') {

					code_char = (int) charact[i];
					index = code_char - 65;
					code_char_n = alphabet_up.codePointAt(index);

					// Encoded letter from Unicode number to Char
					char_temp = (char) code_char_n;
					charact_enc[i] = char_temp;
				} else {
					charact_enc[i] = charact[i];
				}
			}
		}
		// Creating of string from char array
		str_encrypted = new String(charact_enc);
		return str_encrypted;
	}

	
	/**
	 * @param ciphered : a string encrypted with Keyword cipher
	 * @return decrypted string
	 */
	public String decrypt(String ciphered) {
		String decrypted;
		char[] charact;
		char[] charact_dec = new char[ciphered.length()];
		int code_char_n;
		char char_temp;
		int index;

		// Normalisation of string
		ciphered = super.normaliser(ciphered);
		// From initial string to char array
		charact = ciphered.toCharArray();

		// Decrypting
		for (int i = 0; i < ciphered.length(); i++) {
			// calcul of Unicode number of letter

			if (charact[i] >= 'a' && charact[i] <= 'z') {
				index = alphabet_low.indexOf(charact[i]);
				code_char_n = 97 + index;

				// letter from Unicode number to Char
				char_temp = (char) code_char_n;
				charact_dec[i] = char_temp;
			} else {
				if (charact[i] >= 'A' && charact[i] <= 'Z') {

					index = alphabet_up.indexOf(charact[i]);
					code_char_n = 65 + index;

					//Decoded letter from Unicode number to Char
					char_temp = (char) code_char_n;
					charact_dec[i] = char_temp;
				} else {

					charact_dec[i] = charact[i];
				}
			}
		}

		// Creating of string from char array
		decrypted = new String(charact_dec);
		return decrypted;
	}

}
