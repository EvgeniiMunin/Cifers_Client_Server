package ciphers;
/**
 * 
 * @author Nelly Alandou
 * @author Evgenii Munin
 *
 */
import java.text.Normalizer;

abstract class Cipher {
	
	
	/**
	 * @param str
	 * 			a clear string 
	 * @return Normalized clear string
	 */
	// Normalization
	protected static String normaliser(String str) {
		String str_norm;

		// Separation of the accents from characters
		str_norm = Normalizer.normalize(str, Normalizer.Form.NFD);

		// Comparison of characters being characters.
		// Throwing out of non alphabetic symbols
		str_norm = str_norm.replaceAll("[^\\p{ASCII}]", "");

		return str_norm;

	}

}
