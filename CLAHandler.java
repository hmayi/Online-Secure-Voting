

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class CLAHandler extends Thread {
	static ArrayList<CLAHandler> handlers = new ArrayList<CLAHandler>();
	static HashMap<String, String> map = new HashMap<String, String>(); 
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	//private Random gen = new Random();
	private static final int CTF = 9700;



	private BigInteger claE;
	private BigInteger claD;
	private BigInteger claN;

	private BigInteger ctfE;
	private BigInteger ctfN;




	RSAEncrypt rsaEncrypt = new RSAEncrypt();
	RSADecrypt rsaDecrypt = new RSADecrypt();
	HashMap<String, String>  validationNumber  = new HashMap<String,String>();

	ArrayList<String>ctfValidationNumber = new ArrayList<String>();
	HashMap<String, VoterPublicKey> voterPublicKeys;


	ArrayList<BigInteger> bigArray = new ArrayList<BigInteger>();
	ArrayList<String> ssn = new ArrayList<String>();


	String validnumberencrpt ;
	String validnumberCTF ;




	public CLAHandler(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	public void run() {
		String line;
		synchronized(handlers) {
			handlers.add(this);
		}
		try {
			while(!(line = in.readLine()).equalsIgnoreCase("/quit")) {
				for(int i = 0; i < handlers.size(); i++) {	
					synchronized(handlers) {
						CLAHandler handler =
							(CLAHandler)handlers.get(i);

						//System.out.println(line);
						//System.out.println("test " + giveValidationNumber(line));
						handler.out.println(giveValidationNumber(line));
						handler.out.flush();
					}
				}
			}
		} 
		catch(NullPointerException ioe ) {
			
		} 
		catch(IOException ioe){
			//ioe.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
				socket.close();
			} 
			catch(IOException ioe) {
			} 
			finally {
				synchronized(handlers) {
					handlers.remove(this);
				}
			}
		}
	}
	private String giveValidationNumber(String line) {
		if (map.containsKey(line))
			return map.get(line);
		else {
			String value = null;
			do {
				
				value = decryptSSN(line);

			}
			while(map.containsValue(value));
			map.put(line, value);

			new CallVoter("@" + validnumberCTF, "localhost", CTF);

			return value;
		}
	}






	/*****************************************************************/



	private String decryptSSN(String s) {

		
		setUpPublicKeys();
		readCLAPublicKey();
		readctfPublicKeys();
		
		String decryptvalue = getDecrypt(s);
		String validnumbergen = makeEncrypt(decryptvalue);
		System.out.println("Valid Number = "+validnumbergen);
		validnumberencrpt =  encryptvalidnumVoter(decryptvalue,validnumbergen);
		
		System.out.println("Valid Numberencrypt for voter " + validnumberencrpt);
		
		validnumberCTF =  encryptvalidnumCTF( validnumbergen );
		System.out.println( "Validation number Decrypt: " + validnumberCTF );
		
		String ssnAndValidNumber = decryptvalue + "##" + validnumberencrpt;
		return ssnAndValidNumber;


	}
	public void setUpPublicKeys() {
		voterPublicKeys = new HashMap<String, VoterPublicKey>();
		voterPublicKeys.put("1234567", new VoterPublicKey(new BigInteger("17") ,new BigInteger("551"), "1234567"));
		voterPublicKeys.put("2345678", new VoterPublicKey(new BigInteger("5") ,new BigInteger("119"), "2345678"));
		voterPublicKeys.put("3456789", new VoterPublicKey(new BigInteger("9") ,new BigInteger("391"), "3456789"));
	}


	private String encryptvalidnumCTF( String validationNumber ){
		return rsaEncrypt.encrypt(validationNumber, ctfE, ctfN);
	}


	private String encryptvalidnumVoter(String voterSSN, String validationNumber) {
		VoterPublicKey voterKey = voterPublicKeys.get(voterSSN);
		RSAEncrypt rsa  = new RSAEncrypt();
		return rsa.encrypt(validationNumber, voterKey.getE() ,voterKey.getN());

	}

	public void readCLAPublicKey( ) {
		ArrayList<BigInteger> clapublickey = new ArrayList<BigInteger>();
		clapublickey.add(new BigInteger ("17"));
		clapublickey.add(new BigInteger("551"));
		clapublickey.add(new BigInteger("89"));
		claE = clapublickey.get(0);
		claN =clapublickey.get(1);
		claD = clapublickey.get(2);
	}


	private void readctfPublicKeys( ) {
		ArrayList<BigInteger> ctfpublickey = new ArrayList<BigInteger>();
		ctfpublickey.add(new BigInteger ("5"));
		ctfpublickey.add(new BigInteger("119"));
		ctfpublickey.add(new BigInteger("77"));

		ctfE = ctfpublickey.get(0);
		ctfN =ctfpublickey.get(1);		 

	}


	public String getDecrypt(String s){

		RSADecrypt dec = new RSADecrypt();
		return	dec.decrypt(s,claD,claN);
	}


	public HashMap<String, VoterPublicKey> getVoterPublicKeys(){
		return this.voterPublicKeys;
	}

	private String makeEncrypt(String dec) {
		System.out.println("dec +"+ dec);
		String s="";
		if(validationNumber.get(dec)!=null){
			System.out.println("random number already generated");
		}
		else{
			int n	=(int) (1+ Math.round(Math.random()*150000));
			validationNumber.put(dec, (new Integer(n)).toString());
			System.out.println(" "+validationNumber.get(dec));
			s= validationNumber.get(dec).toString();
		}

		return s;
	}



}
