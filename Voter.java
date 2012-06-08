




import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


public class Voter extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int CLA = 9800, CTF = 9700;
	JTextArea text = new JTextArea(5, 20);
	JButton cla = new JButton("Contact CLA");
	JButton ctf = new JButton("Contact CTF");






	HashMap<String, VoterPublicKey> voterPublicKeys;
	HashMap<String, VoterPrivateKey> voterPrivateKeys;
	private	ArrayList<BigInteger> bigArray = new ArrayList<BigInteger>();
	public ArrayList<String> ssn = new ArrayList<String>();

	String word = "" ;
	boolean first = false;


	private BigInteger claE;
	private BigInteger claN;


	private BigInteger ctfE;
	private BigInteger ctfN;

	String cipher ;

	public static void main(String a[]){
		new Voter();
	}

	public Voter(){
		setLayout(new FlowLayout());
		text.setSize(20, 5);
		cla.addActionListener(this);
		ctf.addActionListener(this);
		getContentPane().add(text);
		getContentPane().add(cla);	
		getContentPane().add(ctf);
		pack();
		setVisible(true);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() == cla ){
			String ssn = JOptionPane.showInputDialog("Please enter your Social Security #:");
			String host = JOptionPane.showInputDialog("Please enter the CLA's ip address:");

			setUpPublicKeys();
			setUpPrivateKeys();

			readCLAPublicKey();
			readctfPublicKeys();

			VoterPublicKey key = getVoterPublicKeys().get(ssn);


			if(key != null){

				cipher = encrypt(key);
				CallVoter call = new CallVoter(cipher, host, CLA);
				String decryptValidationnumber = getPrivateKeys(call.listen());
				
				text.setText( decryptValidationnumber );

			} else {
				System.out.println("Not a valid ssn!  ");
				String m = "Not a valid ssn! Please enter valid ssn" ;
				text.setText( m );
			}

			
		}
		else if ( e.getSource() == ctf ){
			String val = JOptionPane.showInputDialog("Please enter your Validation #:");
			String host = JOptionPane.showInputDialog("Please enter the CTF's ip address:");
			int vote = JOptionPane.showOptionDialog(null, "Vote for which?", "Vote", 
					JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Red", "Blue", "Green"}, "Red");
			System.out.println(vote);
			CallVoter call = new CallVoter(vote + "" + val, host, CTF);
			text.setText(call.listen());
		}

	}



	public void setUpPublicKeys() {
		voterPublicKeys = new HashMap<String, VoterPublicKey>();
		voterPublicKeys.put("1234567", new VoterPublicKey(new BigInteger("17") ,new BigInteger("551"), "1234567"));
		voterPublicKeys.put("2345678", new VoterPublicKey(new BigInteger("5") ,new BigInteger("119"), "2345678"));
		voterPublicKeys.put("3456789", new VoterPublicKey(new BigInteger("9") ,new BigInteger("391"), "3456789"));
	}
	public void setUpPrivateKeys() {
		voterPrivateKeys = new HashMap<String, VoterPrivateKey>();
		voterPrivateKeys.put("1234567", new VoterPrivateKey(new BigInteger("89") ,new BigInteger("551"), "1234567"));
		voterPrivateKeys.put("2345678", new VoterPrivateKey(new BigInteger("77") ,new BigInteger("119"), "2345678"));
		voterPrivateKeys.put("3456789", new VoterPrivateKey(new BigInteger("313") ,new BigInteger("391"), "3456789"));
	}


	public HashMap<String, VoterPublicKey> getVoterPublicKeys(){
		return this.voterPublicKeys;
	}

	public HashMap<String, VoterPrivateKey> getVoterPrivateKeys(){
		return this.voterPrivateKeys;
	}
	public void readCLAPublicKey( ) {
		ArrayList<BigInteger> clapublickey = new ArrayList<BigInteger>();
		clapublickey.add(new BigInteger ("17"));
		clapublickey.add(new BigInteger("551"));
		clapublickey.add(new BigInteger("89"));
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

	}

	public String encrypt(VoterPublicKey publicKey) {			
		RSAEncrypt rsa  = new RSAEncrypt();
		return rsa.encrypt(publicKey.getSSN(), claE, claN);

	}


	public String getPrivateKeys( String validNumber ){
		
		StringTokenizer st = new StringTokenizer(validNumber, "##" );
		String validssn = st.nextToken();
		System.out.println("Valid SSN: " + validssn );
		String message = st.nextToken();
		System.out.println("Message: " + message );
		
		VoterPrivateKey voterPrivate = voterPrivateKeys.get(validssn);
		System.out.println("voterssn "+ voterPrivate.getSSN());
		RSADecrypt rsadec = new RSADecrypt();
		return  rsadec.decrypt(message, voterPrivate.getD(), voterPrivate.getN());
	}







}