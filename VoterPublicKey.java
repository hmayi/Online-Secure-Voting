import java.io.InputStream;
import java.math.BigInteger;


public class VoterPublicKey {
	private String ssn;
	private BigInteger e, n;
	//InputStream urlnames=this.getClass().getResourceAsStream("/resources/Voternms.txt");
	//Voter vote = new Voter();

	VoterPublicKey( BigInteger e, BigInteger n, String ssn){
		this.ssn = ssn;
		this.n = n;
		this.e = e;
	}

	public BigInteger getE(){
		return this.e;
	}
	public BigInteger getN(){
		return this.n;
	}
	public String getSSN(){
		return this.ssn;
	}

}

