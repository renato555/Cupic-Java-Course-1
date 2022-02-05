package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Main class.
 * The app can encrypt/decrypt given file using AES and the 128-bit key,
 * or calculate and check the SHA-256 file digest.
 * @author renat
 */

public class Crypto {
	public static void main( String[] args) {
		if( args.length == 2) {
			// checksha
			
			// validacija
			if( !args[0].equals( "checksha")) {
				System.out.println( "ocekujemo checksha");
				return;
			}
			Path path = Path.of( args[1]);
			if( !Files.exists(path) || !Files.isRegularFile(path)) {
				System.out.println( "navedeno ime nije ime datoteke");
				return;
			}
			
			try( BufferedReader userInput = new BufferedReader( new InputStreamReader(System.in));
				 InputStream inputFile = new BufferedInputStream(Files.newInputStream( path))){
				
				System.out.print( "Please provide expected sha-256 digest for " + args[1] +":\n> ");
				String inputSHA = userInput.readLine(); 
				
				MessageDigest msgDigest = MessageDigest.getInstance( "SHA-256");
				byte[] input = new byte[4096];
				byte[] result;
				while( true) {
					int r = inputFile.read( input);
					if( r < 4096) {
						msgDigest.update( input, 0, r);
						result = msgDigest.digest();
						break;
					}else {
						msgDigest.update( input);
					}
				}
				
				String resultString = Util.bytetohex( result);
				if( resultString.equals( inputSHA)) {
					System.out.println( "Digesting completed. Digest of " + args[1] + " matches expected digest.");
				}else {
					System.out.println( "Digesting completed. Digest of " + args[1]+ " does not match the expected digest."
							+ " Diges was: " + resultString);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
		}else if( args.length == 3) {
			// encrypt/decrypt
			
			// validacija
			if( !args[0].equals( "encrypt") && !args[0].equals( "decrypt")) {
				System.out.println( "ocekujemo encrypt ili decrypt");
				return;
			}
			Path pathFirst = Path.of( args[1]);
			if( !Files.exists( pathFirst) || !Files.isRegularFile( pathFirst)) {
				System.out.println( "navedeno ime nije ime datoteke (2) argument");
				return;
			}
			Path pathSecond = Path.of( args[2]);
			boolean encrypt = args[0].equals( "encrypt") ? true : false;
			
			try( BufferedReader userInput = new BufferedReader( new InputStreamReader(System.in));
				InputStream inputFile = new BufferedInputStream(Files.newInputStream( pathFirst));
				OutputStream outputFile = new BufferedOutputStream(Files.newOutputStream( pathSecond))	){
			
				System.out.print( "Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
				String keyText = userInput.readLine();
				System.out.print( "Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
				String ivText = userInput.readLine();
				
				SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
				AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
				
				byte[] input = new byte[4096];
				byte[] result;
				while( true) {
					int r = inputFile.read( input);
					if( r < 4096) {
						result = cipher.doFinal( input, 0, r);
						outputFile.write( result);
						break;
					}else {
						result = cipher.update( input);
						outputFile.write( result);
					}
				}
				
				System.out.println( (encrypt ? "Encryption" : "Decryption") + " completed. Generated file " + 
									args[2] +" based on file " + args[1] + ".");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println( "nedopusteni broj argumenata!");
		}
	}
}
