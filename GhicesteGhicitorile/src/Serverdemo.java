import java.util.List;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Serverdemo {
 
	public static void main(String[] args) {
		if (args.length < 1)
			return;

		int port = Integer.parseInt(args[0]);

		//crearea unei legaturi
		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Serverul este conectat la portul " + port);

			List<Ghicitoare> ghicitori = new ArrayList<>();
			Ghicitoare g1 = new Ghicitoare("Cine zboara fara sa zboare?", "avionul");
			Ghicitoare g2 = new Ghicitoare("M-ai auzit inainte, acum ma auzi din nou. Apoi ma sting... Pana ma chemi iar!","ecoul");
			Ghicitoare g3 = new Ghicitoare("Ce este ziua plina si noaptea goala?", "incaltamintea");
			Ghicitoare g4 = new Ghicitoare("Cu cat iei din ea cu atat se face mai mare!", "groapa");
			Ghicitoare g5 = new Ghicitoare("Ce trece prin campii si orase, dar nu se misca niciodata?", "drumul");

			ghicitori.add(g1);
			ghicitori.add(g2);
			ghicitori.add(g3);
			ghicitori.add(g4);
			ghicitori.add(g5);

			//acest while permite serverului sa ruleze la infinit
			while (true) {
				//atunci cand este creata conexiunea cu Clientul, se creeaza si obiectul de tip Socket
				Socket socket = serverSocket.accept();
				System.out.println("Un nou jucator s-a conectat!");

				// primire de la Client
				InputStream input = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));

				// trimitere la Client
				OutputStream output = socket.getOutputStream();
				// trimiterea datelor sub forma de text
				PrintWriter writer = new PrintWriter(output, true);

				String raspuns = null;
				int i = 0;
				
				String ghicitoare = ghicitori.get(i).getGhicitoare();
				raspuns = ghicitori.get(i).getRaspuns();
				
				System.out.println("1.Ghicitoare: " + ghicitoare);
				System.out.println("2.Raspunsul ghicitorii: " + raspuns);
				writer.println(ghicitoare);

				String text;

				do {
					text = reader.readLine().toLowerCase();
					System.out.println("6.Raspuns primit de la client: " + text);
					System.out.println("7.Raspunsul ghicitorii: " + raspuns);

					if (!raspuns.equals(text)) {
						writer.println("8.Mai incearca :(");
					} else {
						writer.println("9.Bravo!");

						if (i <= ghicitori.size()) {
							i++;
							ghicitoare = ghicitori.get(i).getGhicitoare();
							System.out.println("10.Ghicitoarea: " + ghicitoare);
							writer.println(ghicitoare);
							raspuns = ghicitori.get(i).getRaspuns();
							System.out.println("11.Raspunsul ghicitorii: " + raspuns);
						}
					}

				} while (!text.equals("pa"));

				// inchiderea serverului
				writer.println("Ai pierdut!");
				socket.close();
			}

		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	}