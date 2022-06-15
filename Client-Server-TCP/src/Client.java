import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	private Socket clientSocket;
	
	public void connectionTCP(String ip, int port)
	{
		try {
			clientSocket = new Socket(ip, port);
			InputStream inputFromVideoFile = new FileInputStream(new File("videoFile.mp4"));
			byte[] buffer = new byte[1024];
			OutputStream outputToTCP = clientSocket.getOutputStream();
			int count = inputFromVideoFile.read(buffer, 0, 1024);
			//while is used to terminate writing to stream when InputStream is empty
			while(count != -1) {
				outputToTCP.write(buffer, 0, 1024);
				count = inputFromVideoFile.read(buffer, 0, 1024);
				outputToTCP.flush();				
			}
			inputFromVideoFile.close();
			outputToTCP.close();
			clientSocket.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		Client client = new Client();
		client.connectionTCP("localhost", 8889);
	}
}
