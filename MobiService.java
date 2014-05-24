import java.net.*;
import java.io.*;
import java.awt.Robot;

public class MobiService {

	static int x_coordinate;
	static int y_coordinate;

	static Robot robot;

	static String input, output;
	static int portNumber;
	static ObjectInputStream inputStream;
	static ObjectOutputStream outputStream;

	public static void main(String[] args) 
		throws IOException, ClassNotFoundException, java.awt.AWTException {

		if(args.length != 1) {
			System.err.println("Usage: java MobiServer <port number>");
            System.exit(1);
		}

		robot = new Robot();

		x_coordinate = 100;
		y_coordinate = 100;

		input = "";
		output = "";

		portNumber = Integer.parseInt(args[0]);

		System.out.format("Server using PORT: %d\n", portNumber);

		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();

			System.out.println("Client connected");

			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			inputStream = new ObjectInputStream(clientSocket.getInputStream());

			while(!input.equals("quit")) {
				

				input = (String) inputStream.readObject();
				System.out.format("%s\n", input);

				if(input.equals("l")) {
					if(x_coordinate != 0)
						x_coordinate -= 100;
				}
				else if(input.equals("r")) {
					x_coordinate += 100;
				}
				else if(input.equals("u")) {
					if(y_coordinate != 0)
						y_coordinate -= 100;
				}
				else if(input.equals("d")) {
						y_coordinate += 100;
				}
				else if(input.equals("quit")) {
					break;
				}

				robot.mouseMove(x_coordinate, y_coordinate);

				outputStream.writeObject("SUCCESS");
			}

			outputStream.writeObject("QUIT");
			
            inputStream.close();
            outputStream.close();
            serverSocket.close();
            clientSocket.close();
		}
		catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
	}

	public static void communicate() throws ClassNotFoundException {
		
		try {
			input = (String) inputStream.readObject();
			System.out.format("%s\n", input);
		}
		catch(IOException e) {
			System.out.println("LAME");
		}
	}
}

