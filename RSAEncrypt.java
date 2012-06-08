import java.io.*;
import java.math.BigInteger;
import java.util.*;
public class RSAEncrypt {
	private   List<Integer>  array = new ArrayList<Integer>();
	BigInteger[] bigarray = new BigInteger[2];

	String finalcipher="";
	String test="";
	String privatetext="";


	RSAEncrypt(){

	}

	RSAEncrypt(String t, BigInteger p,BigInteger n ) throws IOException{
		finalcipher=  encrypt(t,p,n);
		System.out.println(finalcipher);

	}


	//it gets e and n value from pub_key and calculates the 
	//mod which is ciphertext
	String encrypt(String t,BigInteger p,BigInteger n){
		System.out.println("t ="+t+"p ="+p+"n="+n);
		boolean has=false;
		String plaintext ="";
		for(int i=0;i<t.length();i++){
			char s = t.charAt(i);
			String s1 = Character.toString(s);
			BigInteger message = new BigInteger(s1);
			BigInteger plain = message.modPow(p, n);


			if(has){
				plaintext = plaintext +" "+plain.toString();
			}
			else{
				plaintext = plain.toString();
				has=true;
			}
		}
		return plaintext;
	}
}

