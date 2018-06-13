package ciphers;
/**
 * 
 * @author Nelly Alandou
 * @author Evgenii Munin
 *
 */
public interface DecryptEncrypt {
	String encrypt(String clear);

	String decrypt(String ciphered);
}
