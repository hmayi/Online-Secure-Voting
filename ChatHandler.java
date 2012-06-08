// http://www.acm.org/crossroads/xrds6-1/ovp61.html

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

public class ChatHandler extends Thread {
	static Vector handlers = new Vector( 10 );
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public ChatHandler(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(
				new OutputStreamWriter(socket.getOutputStream()));
	}
	public void run() {
		String line;
		synchronized(handlers) {
			handlers.addElement(this);
		}
		try {
			while(!(line = in.readLine()).equalsIgnoreCase("/quit")) {
				for(int i = 0; i < handlers.size(); i++) {	
					synchronized(handlers) {
						ChatHandler handler =
							(ChatHandler)handlers.elementAt(i);
						handler.out.println(line + "\r");
						handler.out.flush();
					}
				}
			}
		} 
		catch(IOException ioe) {
			ioe.printStackTrace();
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
					handlers.removeElement(this);
				}
			}
		}
	}
}
