



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class CTF {
	public static final int PORT = 9700;
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(PORT);
			while(true) {
				socket = serverSocket.accept();
				CTFHandler handler = new CTFHandler(socket);
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
