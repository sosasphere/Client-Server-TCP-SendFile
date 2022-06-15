import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	public void startServer(int port) {
		System.out.println("Server is up...");
		try {
			serverSocket = new ServerSocket(port);
			clientSocket = serverSocket.accept();
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						File video = new File("receivedvideo.mp4");
						FileOutputStream writeToVideoFile = new FileOutputStream(video);
						InputStream dataFromTCP = clientSocket.getInputStream();
						byte[] buffer = new byte[1024];
						int count = dataFromTCP.read(buffer, 0, 1024);
						//while is used to terminate writing to stream when InputStream is empty
						while(count != -1) {
							writeToVideoFile.write(buffer, 0, 1024);
							count = dataFromTCP.read(buffer, 0, 1024);
						}
						writeToVideoFile.close();
						clientSocket.close();	
						System.out.println("Server Received File!");	
					} catch (IOException e) {
                        e.printStackTrace();
                    }}		
			});
			t.start();
		} catch (IOException e) {
			System.out.println("Could not accept");
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.startServer(8889);
	}

}
