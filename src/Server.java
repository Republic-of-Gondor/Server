import java.io.IOException;
import java.net.ServerSocket;

public class Server {

	private int clientCount = 0;
	private int port = 9898;

	private void start()	{
		ServerSocket listener = null;
		try {
			listener = new ServerSocket(port);
			System.out.println("Server running. Listening for new connections. Let's party.");
			while (true) {
				new ClientConnection(listener.accept()).start();
				clientCount++;
			}
		} catch (IOException e)	{
			System.out.println("Failed to connect to new client!");
		} finally {
			closeQuietly(listener);
		}

	}

	private static void closeQuietly(ServerSocket socket)	{
		System.out.println("Server closing...");
		try {
			if (socket != null)	{
				socket.close();
			}
		} catch (IOException e)	{
			System.err.println("Failed to close connection to client!");
		}
	}

	public static void main(String[] args) {
		new Server().start();
	}
}


