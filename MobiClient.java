import java.net.*;
import java.io.*;
import java.util.Scanner;

public class MobiClient {

	static String hostname, input, output;
	static int portNumber;
	static ObjectInputStream inputStream;
	static ObjectOutputStream outputStream;
	static Scanner inputScanner;

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		if(args.length != 2) {
			System.err.println("Usage: java EchoClient <host name> <port number>");
            System.exit(1);
		}

		input = "";
		output = "";

		hostname = args[0];
		portNumber = Integer.parseInt(args[1]);
		inputScanner = new Scanner(System.in);

		System.out.format("Client using PORT: %d on %s\n", portNumber, hostname);

		try {
			Socket socket = new Socket(hostname, portNumber);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());

			while(!input.equals("QUIT")) {

				input = inputScanner.nextLine();

				outputStream.writeObject(input);

				input = (String) inputStream.readObject();

	            System.out.format("%s\n", input);
			}
			
			inputStream.close();
            outputStream.close();
		}
		catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostname);
            System.exit(1);
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostname);
            System.exit(1);
        }

	}
}