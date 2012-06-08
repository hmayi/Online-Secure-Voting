

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class OutVoter {
	private PrintWriter out;

	// for stdout
	public OutVoter(OutputStream os) { out = new PrintWriter(os, true); }
	public OutVoter()                { this(System.out);                }

	// for Socket output
	public OutVoter(Socket socket) {
		try                     { out = new PrintWriter(socket.getOutputStream(), true); }
		catch (IOException ioe) { ioe.printStackTrace();                                 }
	}

	// for file output
	public OutVoter(String s) {
		try                     { out = new PrintWriter(new FileOutputStream(s), true);  }
		catch(IOException ioe)  { ioe.printStackTrace();                                 }
	}

	public void close() { out.close(); }


	public void println()          { out.println();  }
	public void println(Object x)  { out.println(x); }
	public void println(String x)  { out.println(x); }
	public void println(boolean x) { out.println(x); }
	public void println(char x)    { out.println(x); }
	public void println(double x)  { out.println(x); }
	public void println(float x)   { out.println(x); }
	public void println(int x)     { out.println(x); }
	public void println(long x)    { out.println(x); }

	public void print()            {                 out.flush(); }
	public void print(Object x)    { out.print(x);   out.flush(); }
	public void print(String x)    { out.print(x);   out.flush(); }
	public void print(boolean x)   { out.print(x);   out.flush(); }
	public void print(char x)      { out.print(x);   out.flush(); }
	public void print(double x)    { out.print(x);   out.flush(); }
	public void print(float x)     { out.print(x);   out.flush(); }
	public void print(int x)       { out.print(x);   out.flush(); }
	public void print(long x)      { out.print(x);   out.flush(); }

}

