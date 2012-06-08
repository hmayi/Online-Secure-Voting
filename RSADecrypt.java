
import java.io.*;
import java.math.BigInteger;
import java.util.*;
public class RSADecrypt {
	private   List<Integer>  array = new ArrayList<Integer>();
	BigInteger[] bigarray = new BigInteger[2];

	String finalcipher="";
	String test="";
	String privatetext="";


	RSADecrypt(){	
	}

	RSADecrypt(String t, BigInteger p,BigInteger n ) throws IOException{
		finalcipher=  decrypt(t,p,n);	
	}


	//it gets e and n value from pub_key and calculates the 
	//mod which is ciphertext
	String decrypt(String t,BigInteger p,BigInteger n){
		boolean has=false;
		String ciphertext ="";
		String[] cipher =t.split(" ");
		for(int i=0;i<cipher.length;i++){
			String s = cipher[i];

			BigInteger message = new BigInteger(s);
			BigInteger plain = message.modPow(p, n);


			if(has){
				ciphertext = ciphertext +plain.toString();
			}
			else{
				ciphertext = plain.toString();
				has=true;
			}
		}
		return ciphertext;
	}
}

