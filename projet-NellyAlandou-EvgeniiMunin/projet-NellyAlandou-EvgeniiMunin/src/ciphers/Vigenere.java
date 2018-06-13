package ciphers;

import java.util.HashMap;
import ciphers.Caesar;

import java.util.ArrayList;

/**
 * 
 * @author Nelly Alandou
 * @author Evgenii Munin
 *
 */
public class Vigenere extends Cipher implements DecryptEncrypt {

	private String key = new String();
	private HashMap<String, Character> vigenereTable_low = new HashMap<String, Character>();
	private HashMap<String, Character> vigenereTable_up = new HashMap<String, Character>();

	
	/**
	 * @param key : a key of Vigenere cipher 
	 */
	public Vigenere(String key) {
		this.key = key;
		build_vigenereTables();
	}

	private void build_vigenereTables() {

		/* creating of the key set for the table
		 * for each string in K row is the first letter,
		 *  column the second low case
		 */
		// Lower case
		String alphabet_low = new String("abcdefghijklmnopqrstuvwxyz");
		ArrayList<String> K_low = new ArrayList<String>();
		for (int i = 0; i < alphabet_low.length(); i++) {
			for (int j = 0; j < alphabet_low.length(); j++) {
				K_low.add("" + alphabet_low.charAt(i) + alphabet_low.charAt(j));
			}
		}

		// Upper case
		String alphabet_up = alphabet_low.toUpperCase();
		ArrayList<String> K_up = new ArrayList<String>();
		for (int i = 0; i < alphabet_up.length(); i++) {
			for (int j = 0; j < alphabet_up.length(); j++) {
				K_up.add("" + alphabet_up.charAt(i) + alphabet_up.charAt(j));
			}
		}

		// Creating of the final tables
		String crypted_alphabet_up = new String();
		String crypted_alphabet_low = new String();
		Caesar obj;

		//
		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < 26; j++) {
				obj = new Caesar(i);
				crypted_alphabet_up = obj.encrypt(alphabet_up);
				crypted_alphabet_low = obj.encrypt(alphabet_low);

				vigenereTable_up.put(K_up.get(j + i * 26), crypted_alphabet_up.charAt(j));
				vigenereTable_low.put(K_low.get(j + i * 26), crypted_alphabet_low.charAt(j));

			}
		}
	}

	
	/**
	 * @param clear : a clear string 
	 * @return the clear string encrypted with Vigenere
	 */
	public String encrypt(String clear) {
		// Temporary variables for encryption
		String str_encrypted;
		char[] charact;
		char[] charact_enc = new char[clear.length()];
		char[] key_array = new char[clear.length()];
		int j = 0;

		// Normalisation of string
		clear = super.normaliser(clear);
		// From initial string to char array
		charact = clear.toCharArray();

		// repeating of the key to match the length of the String
		//////////////// REPEATING OF KEY
		for (int i = 0; i < clear.length(); i++) {
			if ((charact[i] >= 'a' && charact[i] <= 'z') | (charact[i] >= 'A' && charact[i] <= 'Z')) {
				key_array[i] = this.key.charAt(j);
				j = j + 1;
				if (j > this.key.length() - 1) {
					j = 0;
				}
			} else {
				key_array[i] = ' ';
			}
		}
		String repeated_key = new String(key_array);

		char[] key_array_up = repeated_key.toUpperCase().toCharArray();
		char[] key_array_low = repeated_key.toLowerCase().toCharArray();

		for (int i = 0; i < clear.length(); i++) {

			// Calcul of Unicode number of char encode
			if (charact[i] >= 'a' && charact[i] <= 'z') {

				String id_vigenereTable = "" + key_array_low[i] + charact[i];

				// Searching in Vigenere table
				charact_enc[i] = vigenereTable_low.get(id_vigenereTable);

			} else {

				if (charact[i] >= 'A' && charact[i] <= 'Z') {

					String id_vigenereTable = "" + key_array_up[i] + charact[i];
					// Searching in Vigenere table
					charact_enc[i] = vigenereTable_up.get(id_vigenereTable);

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
	 * @param ciphered : a string encrypted with Vigener cipher
	 * @return decrypted string
	 */
	public String decrypt(String ciphered) {
		String decrypted;
		char[] charact;
		char[] charact_dec = new char[ciphered.length()];
		char[] key_array = new char[ciphered.length()];
		int j = 0;

		// Normalisation of string
		ciphered = super.normaliser(ciphered);
		// From initial string to char array
		charact = ciphered.toCharArray();

		// Repeating of the key to match the length of the String
		//////////////// REPEATING OF KEY
		for (int i = 0; i < ciphered.length(); i++) {
			if ((charact[i] >= 'a' && charact[i] <= 'z') | (charact[i] >= 'A' && charact[i] <= 'Z')) {
				key_array[i] = this.key.charAt(j);
				j = j + 1;
				if (j > this.key.length() - 1) {
					j = 0;
				}
			} else {
				key_array[i] = ' ';
			}
		}
		String repeated_key = new String(key_array);

		char[] key_array_up = repeated_key.toUpperCase().toCharArray();
		char[] key_array_low = repeated_key.toLowerCase().toCharArray();

		/* Decrypting */
		for (int i = 0; i < ciphered.length(); i++) {
			// calcul of Unicode number of letter
			if (charact[i] >= 'a' && charact[i] <= 'z') {

				for (String key : vigenereTable_low.keySet()) {

					if (key.startsWith("" + key_array_low[i])) {

						if ((vigenereTable_low.get(key)).equals(charact[i])) {

							charact_dec[i] = key.charAt(1);
						}
					}
				}

			} else {
				if (charact[i] >= 'A' && charact[i] <= 'Z') {

					for (String key : vigenereTable_up.keySet()) {
						if (key.startsWith("" + key_array_up[i])) {
							if ((vigenereTable_up.get(key)).equals(charact[i])) {
								charact_dec[i] = key.charAt(1);
							}
						}
					}

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
