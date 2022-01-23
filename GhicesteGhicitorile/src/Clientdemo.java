import java.io.*;
import java.net.*;

public class Clientdemo {

	public static void main(String[] args) {
		if (args.length < 2)
			return;

		String hostname = args[0];
		int port = Integer.parseInt(args[1]);

		try (Socket socket = new Socket(hostname, port)) {

			// trimitere la Server
			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			Console console = System.console();
			String text = null;

			do {
				// primire de la Server
				InputStream input = socket.getInputStream();

				// citirea datelor ca String cu ajutorul BufferedReader
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));

				String raspuns = reader.readLine();
				System.out.println("3.Primit de la server: " + raspuns);
				
				text = console.readLine("4.Introdu raspunsul: ");
				writer.println(text);
				System.out.println("5.Trimis de client serverului: " + text);

			} while (!text.equals("pa"));
			
			
			if(text.equals("pa")) {
				System.out.println("Ai pierdut!");
			}
			

			// inchiderea conexiunii clientului
			socket.close();

		} catch (UnknownHostException ex) {

			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {

			System.out.println("Ai castigat!");
		}
	}
}
