package ciphers;

/**
 * 
 * @author Nelly Alandou
 * @author Evgenii Munin
 *
 */
public class ROT13 extends Caesar implements DecryptEncrypt {
	
	public ROT13(){
		super(13);
	}
	
	/**
	 * @param clear : a clear string 
	 * @return the clear string encrypted with ROT13
	 */
	public String encrypt(String clear){
		// Encrypting
		String str_encrypted=super.encrypt(clear);
		return str_encrypted;
	}
	
	/**
	 * @param ciphered : a string encrypted with ROT13 cipher
	 * @return decrypted string
	 */
	public String decrypt(String ciphered){
		// Decrypting
		String str_clear=super.decrypt(ciphered);
		return str_clear;
	}
}
