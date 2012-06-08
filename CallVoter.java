


import java.net.Socket;


public class CallVoter {

	private Socket socket;
	private OutVoter out;
	private InVoter in;

	public CallVoter(String ssn, String hostName, int port) {

		try {
			socket = new Socket(hostName, port);
			out    = new OutVoter(socket);
			in     = new InVoter(socket);
		}
		catch (Exception ex) { ex.printStackTrace();}
		out.println(ssn);
	}

	public String listen() {
		String s;
		while ((s = in.readLine()) != null) {
			break;
		}
		out.close();
		in.close();
		try {socket.close();}
		catch(Exception e){
			e.printStackTrace();
		}
		System.err.println("Closed client socket");
		return s;
	}

}