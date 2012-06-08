


import javax.swing.JOptionPane;
import java.net.Socket;

public class ChatClient { //extends JFrame implements ActionListener {

	//private String screenName;

	// GUI stuff
	//private JTextArea  enteredText = new JTextArea(10, 32);
	//private JTextField typedText   = new JTextField(32);

	// socket for connection to chat server
	private Socket socket;

	// for writing to and reading from the server
	private Out out;
	private In in;

	ChatClient(String ssn, String hostName) {

		// connect to server
		try {
			socket = new Socket(hostName, 9800);
			out    = new Out(socket);
			in     = new In(socket);
		}
		catch (Exception ex) { ex.printStackTrace(); }
		// this.screenName = screenName;

		// close output stream  - this will cause listen() to stop and exit
		/*         addWindowListener(
                new WindowAdapter() {
                   public void windowClosing(WindowEvent e) {
                     out.close();
                  }
               }
            );*/

		// create GUI stuff
		//         enteredText.setEditable(false);
		//       enteredText.setBackground(Color.lightGray);
		//     typedText.addActionListener(this);

		//   Container content = getContentPane();
		// content.add(new JScrollPane(enteredText), BorderLayout.CENTER);
		//content.add(typedText, BorderLayout.SOUTH);


		// display the window, with focus on typing box
		/*         setTitle("Chat Client 1.0: [" + screenName + "]");
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         pack();
         typedText.requestFocusInWindow();
         setVisible(true);*/
		//    }

		// process TextField after user hits Enter
		//       public void actionPerformed(ActionEvent e) {
		out.println("CLA" + ssn);
		//typedText.setText("");
		//typedText.requestFocusInWindow();
	}

	// listen to socket and print everything that server broadcasts
	public String listen() {
		String s;
		while ((s = in.readLine()) != null) {
			break;
			//enteredText.insert(s + "\n", enteredText.getText().length());
			//enteredText.setCaretPosition(enteredText.getText().length());
		}
		out.close();
		in.close();
		try
		{ socket.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.err.println("Closed client socket");
		return s;
	}

	public static void main(String argv[])  {
		String nickname = JOptionPane.showInputDialog("Please enter your Social Security #:");
		String serverhost = JOptionPane.showInputDialog("Please enter the CLA's ip address:");
		ChatClient client = new ChatClient(nickname, serverhost);
		String val = client.listen();
		System.out.println(val);
	}
}