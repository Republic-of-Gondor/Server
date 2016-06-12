import java.io.*;
import java.net.Socket;

public class ClientConnection extends Thread {
	private int clientNumber;
	private static int clientsCreated = 0;
	private Socket socket;
	private BufferedReader inStream;
	private PrintWriter outStream;

	public ClientConnection(Socket socket)	{
		try {
			this.socket = socket;
			inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outStream = new PrintWriter(socket.getOutputStream(), true);
			this.clientNumber = ++clientsCreated;
		} catch (IOException e)	{
			System.out.println("Failed to establish connection to client " + clientNumber);
		}
		System.out.println("New client connected with number " + clientNumber);
	}

	public void run() {
		try {
			while (true) {
				Thread.sleep(2000);

				if (inStream.ready()) {
					System.out.println(inStream.readLine());
				}

				if (outStream.checkError()) {
					throw new IOException();
				}

				outStream.println("Hi, Client. It's me, Server.");
			}
		} catch (Exception e) {
			System.out.println("Exceptional");
		} finally {
			try {
				socket.close();
				System.out.println("Socket closed for client " + clientNumber);
				throw new IOException();
			} catch (IOException e)	{
				System.err.println("Failed to close socket for client " + clientNumber);
			}
			//Remove the client from the active clients here
		}
	}
}
