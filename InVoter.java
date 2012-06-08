


import java.net.URLConnection;
import java.net.URL;
import java.net.Socket;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;


class InVoter {
	private BufferedReader br;

	// system independent
	private final static String NEWLINE = System.getProperty("line.separator");

	// for stdin
	public InVoter() {
		InputStreamReader isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
	}

	// for stdin
	public InVoter(Socket socket) {
		try {
			InputStream is        = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br                    = new BufferedReader(isr);
		} 
		catch (IOException ioe) { ioe.printStackTrace(); }
	}

	// for URLs
	public InVoter(URL url) {
		try {
			URLConnection site    = url.openConnection();
			InputStream is        = site.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br                    = new BufferedReader(isr);
		} 
		catch (IOException ioe) { ioe.printStackTrace(); }
	}

	// for files and web pages
	public InVoter(String s) {

		try {

			// first try to read file from local file system
			File file = new File(s);
			if (file.exists()) {
				FileReader fr = new FileReader(s);
				br = new BufferedReader(fr);
			}

			// next try for files included in jar
			URL url = getClass().getResource(s);

			// or URL from web
			if (url == null) url = new URL(s);

			URLConnection site    = url.openConnection();
			InputStream is        = site.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
		} 
		catch(IOException ioe) {  }
	}


	// note read() returns -1 if EOF
	private int readChar() {
		int c = -1;
		try { c = br.read(); }
		catch(IOException ioe) { ioe.printStackTrace(); }
		return c;
	}

	// read a token - delete preceding whitespace and one trailing whitespace character
	public String readString() {
		int c;
		while ((c = readChar()) != -1)
			if (!Character.isWhitespace((char) c)) 
				break;

		if (c == -1) 
			return null;

		String s = "" + (char) c;
		while ((c = readChar()) != -1)
			if (Character.isWhitespace((char) c)) 
				break;
			else s += (char) c;

		return s;
	}

	// return rest of line as string and return it, not including newline 
	public String readLine() {
		if (br == null) 
			return null;
		String s = null;
		try { s = br.readLine(); }
		catch(IOException ioe) { ioe.printStackTrace(); }
		return s;
	}


	// return rest of input as string, use StringBuffer to avoid quadratic run time
	// don't include NEWLINE at very end
	public String readAll() {
		StringBuffer sb = new StringBuffer();
		String s = readLine();
		if (s == null) 
			return null;
		sb.append(s);
		while ((s = readLine()) != null) {
			sb.append(NEWLINE).append(s);
		}   
		return sb.toString();
	}


	public void close() { 
		try { br.close(); }
		catch (IOException e) { e.printStackTrace(); }
	}

}

