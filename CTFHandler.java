  

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CTFHandler extends Thread {
	private static ArrayList<CTFHandler> handlers = new ArrayList<CTFHandler>();
	private static ArrayList<String> validNumbers = new ArrayList<String>();
	private static ArrayList<String> voted = new ArrayList<String>();
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private static int[] votes = new int[]{0, 0, 0};




	private BigInteger claE;
	private BigInteger claD;
	private BigInteger claN;

	private BigInteger ctfE;
	private BigInteger ctfN;
	private BigInteger ctfD;




	RSAEncrypt rsaEncrypt = new RSAEncrypt();
	RSADecrypt rsaDecrypt = new RSADecrypt();
	HashMap<String, VoterPublicKey> voterPublicKeys;






	public CTFHandler(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(
				new OutputStreamWriter(socket.getOutputStream()));
	}
	public void run() {
		
		setUpPublicKeys();
		readctfPublicKeys();
		readCLAPublicKey();
		
		String line;
		synchronized(handlers) {
			handlers.add(this);
		}
		try {
			while(!(line = in.readLine()).equalsIgnoreCase("/quit")) {
				for(int i = 0; i < handlers.size(); i++){	
					synchronized(handlers){
						CTFHandler handler = (CTFHandler)handlers.get(i);
						if ( (line.startsWith("@")  && ! validNumbers.contains(line) ) ){
						
							String decrypt = getValidnum( line.substring(1) );
							validNumbers.add( decrypt );
						}
						else {
							handler.out.println(validateVote(line));
							return;
						}
						handler.out.flush();
					}
				}
			}
		} 
		catch(IOException ioe) {
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
	private String validateVote(String s){
		
		int vote = Integer.parseInt(s.substring(0, 1));
		String line = s.substring(1);


		

		if (!validNumbers.contains(line)){
			System.out.println( "Invalid number! " + line );
			return "Invalid numbe" ;
		}

		if (voted.contains(line)){
			System.out.println( "You already voted! "+" = " + line );
			return "You already voted!!";
		}


		votes[vote]++;
		voted.add(line);
		if( vote == 0 )
			System.out.println("Voter " + line + " vote for Red"  );
		else if( vote == 1 )
			System.out.println("Voter " + line + " vote for Blue"  );
		else if( vote == 2)
			System.out.println("Voter " + line + " vote for Green"  );
		System.out.println("Red: " + votes[0]);
		System.out.println("Blue: " + votes[1]);
		System.out.println("Green: " + votes[2]);
		System.out.println( "Your vote has been counted. Thank you." );
		return "You vote has counted. Thank you.";
	}


	public String getValidnum(String s){
		return rsaDecrypt.decrypt(s, ctfD, ctfN);
	}



	public void setUpPublicKeys() {
		voterPublicKeys = new HashMap<String, VoterPublicKey>();
		voterPublicKeys.put("1234567", new VoterPublicKey(new BigInteger("17") ,new BigInteger("551"), "1234567"));
		voterPublicKeys.put("2345678", new VoterPublicKey(new BigInteger("5") ,new BigInteger("119"), "2345678"));
		voterPublicKeys.put("3456789", new VoterPublicKey(new BigInteger("9") ,new BigInteger("391"), "3456789"));
	}

	public void readCLAPublicKey( ) {
		ArrayList<BigInteger> clapublickey = new ArrayList<BigInteger>();
		clapublickey.add(new BigInteger ("17"));
		clapublickey.add(new BigInteger("551"));

		claE = clapublickey.get(0);
		claN =clapublickey.get(1);

	}


	private void readctfPublicKeys( ) {
		ArrayList<BigInteger> ctfpublickey = new ArrayList<BigInteger>();
		ctfpublickey.add(new BigInteger ("5"));
		ctfpublickey.add(new BigInteger("119"));
		ctfpublickey.add(new BigInteger("77"));

		ctfE = ctfpublickey.get(0);
		ctfN =ctfpublickey.get(1);		 
		ctfD =ctfpublickey.get(2); 
	}







}
