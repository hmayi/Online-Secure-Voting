


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	public static final int DEFAULT_PORT = 9800;
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			if(args.length > 0)
				port = Integer.parseInt(args[0]);
		} 
		catch(NumberFormatException nfe) {
			System.err.println("Usage: java ChatServer [port]");
			System.err.println("Where options include:");
			System.err.println("\tport the port on which to listen.");
			System.exit(0);
		}
		try {
			serverSocket = new ServerSocket(port);
			while(true) {
				socket = serverSocket.accept();
				ChatHandler handler = new ChatHandler(socket);
				handler.start();
			}
		} 
		catch(IOException ioe) {
			ioe.printStackTrace();
		} 
		finally {
			try {
				serverSocket.close();
			} 
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
