import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;

public class chatserver extends JFrame
{private JTextField usertext;
 private JTextArea chatwindow;
 private ObjectOutputStream output;
 private ObjectInputStream input;
 private ServerSocket server;
 private Socket connection;

 public static void main(String args[])
	{
	 chatserver sally=new chatserver();
		sally.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sally.startRunning();
	}
 
 public  chatserver()
 {super("chandans text");
 usertext= new JTextField();
 usertext.setEditable(false);
 usertext.addActionListener(
		 new ActionListener()
		 {public void actionPerformed(ActionEvent event)
			 {sendMessage(event.getActionCommand());
			 usertext.setText("");}
			 
		 }
		 );
 
 
 add(usertext,BorderLayout.NORTH);
 chatwindow=new JTextArea();
 add(new JScrollPane(chatwindow));
 setSize(500,500);
 setVisible(true);	 
 }
 
 
 public void startRunning()
 {try
 {server= new ServerSocket(6789,100);
 
 while(true)
 {
	 try
	 {waitForConnections();
	 setupStreams();
	 WhileChatting();
		 
	 }
	 catch(EOFException eofexception)
	 {showMessage("i am closing the connection");}
	 finally
	 {closeCrap();}
	 
 }
	 
 }
 catch(IOException ioException)
 {ioException.printStackTrace();}
	 
 }
 
 private void waitForConnections() throws IOException
 {showMessage("\n waiting for connections....");
  connection=server.accept();
  showMessage("\n now connected to "+ connection.getInetAddress().getHostName());
 }
 
 
 private void setupStreams() throws IOException
 {
	 output=new ObjectOutputStream(connection.getOutputStream());
	 output.flush();
	 input=new ObjectInputStream(connection.getInputStream());
	 showMessage("\n now the connections have been made");
 }
 
 private void WhileChatting() throws IOException
 {
	 String message="\n you are now connected";
	 sendMessage(message);
	 abletotype(true);
	 
	 do
	 {try{message=(String)input.readObject();
	 showMessage("\n"+ message);}
	 catch(ClassNotFoundException co)
	 {showMessage("\n i dont know what are you typing");}
		 
		 
		 
	 }while(!message.equals("CLIENT -END"));
 }
 
private void closeCrap()
 {showMessage("\n closing the connection.....\n");
 abletotype(false);
 try
 {input.close();
 output.close();
 connection.close();}
 catch(IOException ioexception)
 {ioexception.printStackTrace();}
 
	 
 }

private void sendMessage(String message)
{
try
{output.writeObject(message);
output.flush();
showMessage("\n SERVER_"+message);
}
catch(IOException e)
{chatwindow.append("i cant send tht message \n");}
}

private void showMessage(final String message)
{SwingUtilities.invokeLater(new Runnable()
{public void run()
{chatwindow.append("\n"+message);}
});
	
}
 private void abletotype(final boolean tof)
 {SwingUtilities.invokeLater(new Runnable()
 {public void run()
 {usertext.setEditable(tof);}
 });
	 
 }
}

