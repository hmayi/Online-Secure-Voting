
import java.math.BigInteger;



public class VoterPrivateKey {
	private String ssn;
	private BigInteger d, n;
	//InputStream urlnames=this.getClass().getResourceAsStream("/resources/Voternms.txt");
	//Voter vote = new Voter();

	VoterPrivateKey( BigInteger d, BigInteger n, String ssn){
		this.ssn = ssn;
		this.n = n;
		this.d = d;
	}

	public BigInteger getD(){
		return this.d;
	}
	public BigInteger getN(){
		return this.n;
	}
	public String getSSN(){
		return this.ssn;
	}

}

