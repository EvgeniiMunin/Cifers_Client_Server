package ciphers;
/**
 * 
 * @author Nelly Alandou
 * @author Evgenii Munin
 * 
 */
public class Atbash extends Cipher implements DecryptEncrypt {

	public Atbash() {
	}

	/**
	 * @param clear : a clear string 
	 * @return the clear string encrypted with Atbash
	 */
	public String encrypt(String clear) {
		String str_encrypted;
		char[] charact;
		char[] charact_enc = new char[clear.length()];
		int code_char_n, code_char;
		char char_temp;

		// Normalisation of string
		clear = super.normaliser(clear);
		// From initial string to char array
		charact = clear.toCharArray();

		// Encrypting
		for (int i = 0; i < clear.length(); i++) {
			// Calcul of Unicode number of letter
			if (charact[i] >= 'a' && charact[i] <= 'z') {

				code_char = (int) charact[i];
				code_char_n = 122 - (code_char - 97);

				// Letter from Unicode number to Char
				char_temp = (char) code_char_n;
				charact_enc[i] = char_temp;
			} else {
				if (charact[i] >= 'A' && charact[i] <= 'Z') {

					code_char = (int) charact[i];

					code_char_n = 90 - (code_char - 65);

					// Encoded letter from Unicode number to Char
					char_temp = (char) code_char_n;
					charact_enc[i] = char_temp;
				} else {

					charact_enc[i] = charact[i];
				}
			}
		}
		// creating of string from char array
		str_encrypted = new String(charact_enc);
		return str_encrypted;
	}

	
	/**
	 * @param ciphered : a string encrypted with Atbash cipher
	 * @return decrypted string
	 */
	public String decrypt(String ciphered) {
		String decrypted;
		char[] charact;
		char[] charact_dec = new char[ciphered.length()];
		int code_char_n, code_char;
		char char_temp;

		// Normalisation of string
		ciphered = super.normaliser(ciphered);
		// From initial string to char array
		charact = ciphered.toCharArray();

		// Decrypting
		for (int i = 0; i < ciphered.length(); i++) {
			// calcul of Unicode number of letter

			if (charact[i] >= 'a' && charact[i] <= 'z') {
				code_char = (int) charact[i];

				code_char_n = -code_char + 122 + 97;

				// letter from Unicode number to Char
				char_temp = (char) code_char_n;
				charact_dec[i] = char_temp;
			} else {
				if (charact[i] >= 'A' && charact[i] <= 'Z') {
					code_char = (int) charact[i];

					code_char_n = -code_char + 90 + 65;

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
