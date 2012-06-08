


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class CLA {
	public static final int PORT = 9800;
	public static void main(String[] args){
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(PORT);
			while(true) {
				socket = serverSocket.accept();
				CLAHandler handler = new CLAHandler(socket);
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
