package ciphers.tests;

import ciphers.*;

/**
 * 
 * @author Nelly Alandou
 * @author Evgenii Munin
 *
 */

public class TestEvaluationCryptoMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String clear1 = "CESAR";
		String clear2 = "XYZ xyz";
		String clear3 = "Java ça va !";

		// Testing each cipher

		//Test Caesar OK
		DecryptEncrypt obj = new Caesar(3);
		String crypted = obj.encrypt(clear1);
		System.out.println(clear1);
		System.out.println(crypted);
		crypted = obj.encrypt(clear2);
		System.out.println(clear2);
		System.out.println(crypted);
		crypted = obj.encrypt(clear3);
		System.out.println(clear3);
		System.out.println(crypted);
		System.out.println();

		// Test ROT13 OK
		obj = new ROT13();
		crypted = obj.encrypt(clear1);
		System.out.println(clear1);
		System.out.println(crypted);
		crypted = obj.encrypt(clear2);
		System.out.println(clear2);
		System.out.println(crypted);
		crypted = obj.encrypt(clear3);
		System.out.println(clear3);
		System.out.println(crypted);
		System.out.println();

		// Test Atbash OK
		obj = new Atbash();
		crypted = obj.encrypt(clear1);
		System.out.println(clear1);
		System.out.println(crypted);
		crypted = obj.encrypt(clear2);
		System.out.println(clear2);
		System.out.println(crypted);
		crypted = obj.encrypt(clear3);
		System.out.println(clear3);
		System.out.println(crypted);
		System.out.println();

		// Test Vigenere OK
		obj = new Vigenere("KEY");
		crypted = obj.encrypt(clear1);
		System.out.println(clear1);
		System.out.println(crypted);
		crypted = obj.encrypt(clear2);
		System.out.println(clear2);
		System.out.println(crypted);
		crypted = obj.encrypt(clear3);
		System.out.println(clear3);
		System.out.println(crypted);
		System.out.println();

		// Test KEYWORD OK
		obj = new Keyword("KEY");
		crypted = obj.encrypt(clear1);
		System.out.println(clear1);
		System.out.println(crypted);
		crypted = obj.encrypt(clear2);
		System.out.println(clear2);
		System.out.println(crypted);
		crypted = obj.encrypt(clear3);
		System.out.println(clear3);
		System.out.println(crypted);

	}

}
