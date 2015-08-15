import java.io.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.net.*;
import java.awt.*;
import java.awt.event.*;


public class chatclient extends JFrame
{private JTextField usertext;
 private JTextArea chatwindow;
 private ObjectOutputStream output;
 private ObjectInputStream input;
 String message="";
 String serverIp ;
 private Socket connection;
 
public static void main(String args[])
	{
	chatclient client=new chatclient("127.0.0.1");
    client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    client.startRunning();
	}

 public chatclient(String host)
 {super("the client");
  serverIp=host;
  usertext=new JTextField();
usertext.addActionListener(

new ActionListener()
{public void actionPerformed(ActionEvent e)
{sendMessage(e.getActionCommand());
usertext.setText("");}
}

);
chatwindow=new JTextArea();
add(usertext,BorderLayout.NORTH);
add(new JScrollPane(chatwindow));
setSize(300,300);
setVisible(true);
 
 }
 
 public void startRunning()
 {try{
     connectToServer();
     setUpStreams();
     whileChatting();
 }
 catch(EOFException e)
 {showMessage("\n client ended the connection");}
 catch(IOException e)
 {e.printStackTrace();}
 finally
 {closeCrap();}
 }

 private void connectToServer() throws IOException
 {showMessage("attempting connection ......");
  connection=new Socket(InetAddress.getByName(serverIp),6789);
  showMessage("\n connected to" + connection.getInetAddress().getHostName());
 
 }
 
 private void setUpStreams() throws IOException
 {output=new ObjectOutputStream(connection.getOutputStream());
 output.flush();
 input=new ObjectInputStream(connection.getInputStream());
 showMessage("\n yeah!! now we are connected");
 
 }
 
 private void whileChatting() throws IOException
 {abletotype(true);
 do
 {try{message=(String)input.readObject();
 showMessage("\n"+message);}
  catch(ClassNotFoundException e)
 {showMessage("\n dont try to hack me");}
 }while(!message.equals("SERVER-END"));
 

 }
 
 private void closeCrap() 
 {abletotype(false);
showMessage("\n connection closed");
try
 {
  output.close();
 input.close();
 connection.close();
 
 }
     
 catch(IOException e)
 {e.printStackTrace();}
 
 }
 
 private void sendMessage(String message)
{
try
{output.writeObject(message);
output.flush();
showMessage("\n Client_"+message);
}
catch(IOException e)
{chatwindow.append("i cant send tht message \n");}
}
 
 private void showMessage(final String message)
{SwingUtilities.invokeLater(new Runnable()
{public void run()
{chatwindow.append(message);}
});
}
 
  private void abletotype(final boolean tof)
 {SwingUtilities.invokeLater(new Runnable()
 {public void run()
 {usertext.setEditable(tof);}
 });
	 
 }

}
