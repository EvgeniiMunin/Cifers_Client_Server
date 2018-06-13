package client;
//package client;

import ciphers.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.*;

/**
 * 
 * @author Nelly Alandou
 * @author Evgenii Munin
 *
 */
public class TCPClient {

	/**
	 * Executing of client :
	 * 
	 * CLient gives possibility: - Encrypt and decrypt the text files with
	 * simple code: Caesar, ROT13, Atbash, Keyword, Vigenere. Each file can be
	 * encrypted with 1 of these codes, for choise of user. Forwarding, listing,
	 * getting or removing of ecnrypted files in server have to be processed
	 * wtih respect to the protocol;
	 * 
	 * <pre>
	 * java TCPClient
	 * </pre>
	 * 
	 * for closing of client, write "bye"
	 * 
	 * @param args
	 *            did not used
	 * @throws IOException
	 *             Exceptions of initialisation of socket.
	 */
	public static void main(String[] args) throws IOException {
		int id_doc = 0;
		String doc = new String();

		System.out.println("Time to cipher and decipher !");

		// creating the directory to store the different user files
		Path path = Paths.get("data_user");
		try {
			Files.createDirectory(path);
		} catch (IOException e) {
		} finally {
			System.out.println(
					"Make sure to store all your files in the data_user folder that has just been created on your computer");
		}

		// creating of socket for communication with server
		String serverAddress = "localhost";
		int serverPort = 6789;
		Socket socket = new Socket(serverAddress, serverPort);
		System.out.println("Connected to server. Type 90 to terminate the client.\n");

		// use DataStream to encrypted/ decrypted flux of bytes of types (int,
		// long...)
		DataInputStream input = new DataInputStream(socket.getInputStream());
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());

		// HashMap for stock of methodes of encrypting
		HashMap<Long, DecryptEncrypt> methode_crypt = new HashMap<Long, DecryptEncrypt>();
		// communication with user
		BufferedReader msg = new BufferedReader(new InputStreamReader(System.in));
		String str = new String();// commun string for different operations

		// variables for test of commands of user : controlling user related
		// errors
		boolean format = false;
		int strikes = 0;

		// infinite loop for writing of messages
		boolean requesting = true;
		while (requesting) {

			HashMap<Integer, Long> list_of_doc = new HashMap<Integer, Long>();

			System.out.println("What do you want to do? Please answer HELLO");
			String action = msg.readLine();

			if ((action.equalsIgnoreCase("hello"))) {

				// handshake : HELLO
				output.writeInt(0);
				System.out.println("HELLO sent");

				// HELLO_RESP
				int reponse_server = input.readInt();
				if (reponse_server == 100) {
					System.out.println("Client received code 100 : what a great handshake :) !");

					// identifying what the user wants to do next
					if (!(list_of_doc.isEmpty())) {

						System.out.println("What do you want to do? Please answer ADD, REMOVE, GET, LIST or BYE");
						action = msg.readLine();

					} else {

						System.out.println("What do you want to do? Please answer ADD, LIST or BYE");
						action = msg.readLine();
					}

					// loop so that we can do several actions in a row
					while (!(action.equalsIgnoreCase("bye"))) {

						// 4 commands

						////// LIST
						if (action.equalsIgnoreCase("list")) {
							// initializing LIST
							list_of_doc.clear();
							output.writeInt(1);
							System.out.println("LIST sent");

							// response of the server : LIST_REP
							if (input.readInt() == 101) {
								System.out.println("Client received code 101");
								// nb_ids-number of documents stocked by server
								int nb_ids = input.readInt();

								// building our own list of files stored on the
								// server
								for (int k = 0; k < nb_ids; k++) {
									list_of_doc.put(k, input.readLong());
								}
								System.out.println("Here is the list of documents stored in the server ");
								System.out.println(list_of_doc);
							}
						}

						////// ADD
						if (action.equalsIgnoreCase("add")) {

							// retrieving the list of text files in the
							// data_user directory
							ArrayList<Path> list_files = new ArrayList<Path>();
							Path dir = Paths.get("data_user");
							format = false;
							strikes = 0;
							while (!format && strikes < 3) {
								try {
									DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt");
									for (Path entry : stream) {
										list_files.add(0, entry.getFileName());
									}
									format = true;
								} catch (IOException x) {
									// IOException can never be thrown by the
									// iteration.
									// In this snippet, it can // only be thrown
									// by newDirectoryStream.
									System.err.println(
											"There is something wrong, check for data_user folder existence ! Java returned following exception");
									System.err.println(x);
									strikes++;
								}
							}

							if (strikes >= 3) {
								System.out.println("Too many mistakes, should have listen to my warnings");
								action = "bye";
								 
							} else {
								String addendum; // what/how to add : listening
													// to the user
								if (!list_files.isEmpty()) {
									System.out.println(
											"If you want to add an existing text file from the data_user folder to the server TAP 1, else TAP anything else");
									addendum = msg.readLine();
								} else {
									System.out.println("The data_user folder is empty");
									addendum = "2";
								}

								if (addendum.equals("1")) {

									System.out.println("Here is the list of text files");
									System.out.println(list_files);

									// what file does the user want?
									System.out.println(
											"Write the name of the file. Please make sure to put the .txt extension");
									String filename = msg.readLine();
									str = "";

									/*
									 * looping to give the user 3 chances to enter the correct
									 * text file name
									 */
									format = false;
									strikes = 0;
									while (!format && strikes < 3) {
										try {
											FileReader in = new FileReader("data_user/" + filename);
											BufferedReader read = new BufferedReader(in);
											Scanner s = new Scanner(read);
											while (s.hasNext()) {
												str = str.concat(s.nextLine());
											}
											read.close();
											s.close();
											format = true;
										}

										catch (IOException e) {
											System.out.println(
													"File doesn't exist. Please choose a file name from the following list and make sure to put the .txt extension");
											System.out.println(list_files);
											filename = msg.readLine();
											strikes++;
										}
									}

									if (strikes >= 3) {
										System.out.println("Too many mistakes, should have listen to my warnings");
										action = "bye";
										break;
										}

								} else {
									System.out.println("What do you want to write in the new document?");
									// create the doc
									System.out.println("Please make sure your last line is ***");
									String temp = msg.readLine();
									str = new String();
									while (!(temp).equals("***")) {
										str = str.concat(temp);
										temp = msg.readLine();
									}
								}

								System.out.println(
										"Which method do you want to use to encrypt the text? You have the choice between CAESAR, ROT13, KEYWORD, VIGENERE AND ATBASH");
								msg = new BufferedReader(new InputStreamReader(System.in));
								String reponse_user = msg.readLine().toUpperCase();
								/*
								 * creating different parameter depending on
								 * encryption method
								 */

								int offset = 0;
								String keyword = new String();
								DecryptEncrypt method;
								format = false;
								strikes = 0;

								switch (reponse_user) {
								case "CAESAR":
									System.out.println("What is the offset?");

									// looping to deal with user related error
									while (!format && strikes < 3) {
										try {
											offset = Integer.parseInt(msg.readLine());
											format = true;
										} catch (NumberFormatException e) {
											System.out.println("The offset supposed to be an integer !!! ");
											strikes++;
										}
									}
									if (strikes >= 3) {
										System.out.println(
												"Too many mistakes, should have listen to my warnings. Atbash chosen by default");
										method = new Atbash();
										break;
									}
									method = new Caesar(offset);
									 
								case "ROT13":
									method = new ROT13();
									break;
								case "KEYWORD":
									System.out.println("What is the keyword you want to use?");
									strikes = 0;
									while (!format && strikes < 3) {
										keyword = msg.readLine();
										String temp_str = keyword.toLowerCase();
										char[] charact = temp_str.toCharArray();
										format = true;
										// looping to deal with user related
										// error
										for (int j = 0; j < charact.length; j++) {
											if (!(charact[j] >= 'a' && charact[j] <= 'z')
													&& !(charact[j] >= 'A' && charact[j] <= 'Z')) {
												format = false;
											}
										}
										if (!format) {
											strikes++;
											System.out.println(
													"Key must consist only of alphabetic letters, with no accentuation?");
											System.out.println("What is the keyword you want to use?");
										}
									}
									if (strikes >= 3) {
										System.out.println(
												"Too many mistakes, should have listen to my warnings. Atbash chosen by default");
										method = new Atbash();
										 break;
									}
									method = new Keyword(keyword);
									break;
								case "VIGENERE":
									strikes = 0;
									System.out.println("What is the keyword you want to use?");
									while (!format && strikes < 3) {
										keyword = msg.readLine();
										String temp_str = keyword.toLowerCase();
										char[] charact = temp_str.toCharArray();
										format = true;
										// looping to deal with user related
										// error
										for (int j = 0; j < charact.length; j++) {
											if (!(charact[j] >= 'a' && charact[j] <= 'z')
													&& !(charact[j] >= 'A' && charact[j] <= 'Z')) {
												format = false;
											}
										}
										if (!format) {
											strikes++;
											System.out.println(
													"Key must consist only of alphabetic letters, with no accentuation?");
											System.out.println("What is the keyword you want to use?");
										}
									}
									if (strikes >= 3) {
										System.out.println(
												"Too many mistakes, should have listen to my warnings. Atbash chosen by default");
										method = new Atbash();
										break;
									}
									method = new Vigenere(keyword);
									break;
								case "ATBASH":
									method = new Atbash();
									break;
								default:
									System.out.println("You didn't chose a valid cipher so we chose Atbash by default");
									method = new Atbash();
								}

								String crypted = method.encrypt(str);
								output.writeInt(2);
								output.writeInt(crypted.substring(0, crypted.length()).toCharArray().length);
								output.writeChars(crypted);

								// response of the server : ADD_REP
								reponse_server = input.readInt();
								if (reponse_server == 102) {
									long new_id = input.readLong();
									list_of_doc.put(list_of_doc.size(), new_id);
									methode_crypt.put(new_id, method);
									System.out.println("Client received code 102");

								} else {
									int longueur_erreur = input.readInt();
									byte[] data = new byte[longueur_erreur];
									input.read(data);
									System.out.println(data.toString());
								}

							}
						}

						////// REMOVE
						if (action.equalsIgnoreCase("remove") && !(list_of_doc.isEmpty())) {

							System.out.println("Here is the list of documents stored in the server ");
							System.out.println(list_of_doc);
							System.out.println("Which document do you want to remove from the following list:");
							System.out.println(
									"Please give the identifier (int on the left side of the equal) from the list");

							msg = new BufferedReader(new InputStreamReader(System.in));
							doc = msg.readLine();

							format = false;
							strikes = 0;
							while (!format && strikes < 3) {
								try {
									id_doc = Integer.parseInt(doc);
									format = true;
								} catch (NumberFormatException e) {
									strikes++;
									System.out.println("THE ID IS SUPPOSED TO BE AN INTEGER !!! ");
									doc = msg.readLine();
								}
							}
							if (strikes >= 3) {
								System.out.println(
										"Too many mistakes, should have listen to my warnings. Nothing removed");
								 
							} else {
								strikes = 0;

								while (!(list_of_doc.containsKey(id_doc)) && strikes < 3) {
									System.out
											.println("The id you have entered doesn't exist in the list of documents");
									doc = msg.readLine();
									try {
										id_doc = Integer.parseInt(doc);
										format = true;
									} catch (NumberFormatException e) {
										strikes++;
										System.out.println("THE ID IS SUPPOSED TO BE AN INTEGER !!! ");
										doc = msg.readLine();
									}
								}
							}

							if (strikes >= 3) {
								System.out.println(
										"Too many mistakes, should have listen to my warnings. Nothing removed");
								 
							} else {
								// REMOVE
								output.writeInt(3);
								output.writeLong(list_of_doc.get(id_doc));

								// response of the server : REMOVE_REP
								reponse_server = input.readInt();
								if (reponse_server == 103) {
									methode_crypt.remove(list_of_doc.get(id_doc));
									list_of_doc.remove(id_doc);
									System.out.println("The file has been removed");
								} else {
									int longueur_erreur = input.readInt();
									byte[] data = new byte[longueur_erreur];
									input.read(data);
									System.out.println(data.toString());
								}
							}
						}

						////// GET
						if (action.equalsIgnoreCase("get") && !(list_of_doc.isEmpty())) {
							System.out.println("Here is the list of documents stored in the server ");
							System.out.println(list_of_doc);
							System.out.println("Which document do you want to retrieve from the previous list:");
							System.out.println(
									"Please give the identifier (int on the left side of the equal) of the doc from the list");
							msg = new BufferedReader(new InputStreamReader(System.in));
							doc = msg.readLine();
							format = false;
							strikes = 0;
							while (!format && strikes<3) {
								try {
									id_doc = Integer.parseInt(doc);
									format = true;
								} catch (NumberFormatException e) {
									System.out.println("THE ID IS SUPPOSED TO BE AN INTEGER !!! ");
									doc = msg.readLine();
									strikes++;
								}
							}
							
							if(strikes>=3){
								System.out.println("Too many mistakes, should have listen to my warnings. Nothing retrieved");
								 
								}else{

							// GET
							output.writeInt(4);
							while (!(list_of_doc.containsKey(id_doc))
									|| !(methode_crypt.containsKey(list_of_doc.get(id_doc)))) {
								System.out.println(
										"The id you have entered doesn't exist in the list of documents or The id you have entered doesn't exist in the list of crypted documents.");
								doc = msg.readLine();
								format = false;
								while (!format) {
									try {
										id_doc = Integer.parseInt(doc); // id
									} catch (NumberFormatException e) {
										System.out.println("THE ID IS SUPPOSED TO BE AN INTEGER !!! ");
										doc = msg.readLine();
									}
								}
							}
							output.writeLong(list_of_doc.get(id_doc));

							// response of the server : GET_REP
							reponse_server = input.readInt();
							if (reponse_server == 104) {

								int length_get = input.readInt();

								// retrieving the string

								char[] char_array = new char[length_get];
								for (int i = 0; i < length_get; i++) {
									char_array[i] = input.readChar();
								}
								String str_get = new String(char_array);
								DecryptEncrypt method = methode_crypt.get(list_of_doc.get(id_doc));

								/*
								 * Decrypting the string and writing it into a
								 * text file
								 */

								String clear = method.decrypt(str_get);

								System.out.println("Write the filename in which you want to store the retrieved string");
								String filename = msg.readLine();
								FileWriter out = new FileWriter("data_user/" + filename);
								BufferedWriter writer = new BufferedWriter(out);
								String[] text = clear.split("  ");

								for (String line : text) {
									writer.write(line);
									writer.newLine();
								}
								writer.close();
								System.out.println("Client received code 104");

							} else {
								int longueur_erreur = input.readInt();
								byte[] data = new byte[longueur_erreur];
								input.read(data);
								System.out.println(data.toString());
							}
						}}

						if (!(list_of_doc.isEmpty())) {

							System.out.println("What do you want to do? Please answer ADD, REMOVE, GET, LIST or BYE");
							action = msg.readLine();
						} else {
							System.out.println("What do you want to do? Please answer ADD, LIST or BYE");
							action = msg.readLine();
						}
					}
				}

				System.out.println("Do you want to disconnect for good or not ?");
				System.out.println("Please answer yes or no. Note that if you say yes, we empty the server");
				BufferedReader last_doing = new BufferedReader(new InputStreamReader(System.in));
				String doing = new String(last_doing.readLine());

				if (doing.equalsIgnoreCase("yes")) {

					System.out.println("You said BYE for good so we are emptying the server");

					// Emptying the server
					output.writeInt(1);
					if (input.readInt() == 101) {
						// nb_ids-number of documents stocked by server
						int nb_ids = input.readInt();

						long[] reponse_array = new long[nb_ids];
						for (int k = 0; k < nb_ids; k++) {
							reponse_array[k] = input.readLong();
						}
						for (long id : reponse_array) {
							output.writeInt(3);
							output.writeLong(id);
							reponse_server = input.readInt();
						}
					}

					// sending BYE
					output.writeInt(90);
					reponse_server = input.readInt();
					if (reponse_server == 190) {
						// closing of socket
						socket.close();
					}else{
						System.out.println("Client received code "+reponse_server);
						int longueur_erreur = input.readInt();
						byte[] data = new byte[longueur_erreur];
						input.read(data);
						System.out.println(data.toString());
					}

					// Farewell
					System.out.println("No more connection, Goodbye ! ");
					requesting = false;

				} else {

					output.writeInt(90);
					reponse_server = input.readInt();
					if (reponse_server == 190) {
						// closing of socket
						socket.close();
					}
					// reopen of new socket
					socket = new Socket(serverAddress, serverPort);
					input = new DataInputStream(socket.getInputStream());
					output = new DataOutputStream(socket.getOutputStream());

				}

			} else {
				System.out.println(
						"Fisrt message should be HELLO. Your message is not HELLO. Please, be courteous and say hello ");

			}

		}
	}
}
