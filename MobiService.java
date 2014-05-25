import java.net.*;
import java.io.*;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class MobiService {

	static int x_coordinate;
	static int y_coordinate;

	static boolean server_flag;

	static Robot robot;

	static String input, output;
	static int portNumber;

	static DataInputStream dataInputStream;
	static DataOutputStream dataOutputStream;

	public static void main(String[] args) 
		throws IOException, ClassNotFoundException, java.awt.AWTException {

		if(args.length != 1) {
			System.err.println("Usage: java MobiServer <port number>");
            System.exit(1);
		}

		robot = new Robot();

		x_coordinate = 100;
		y_coordinate = 100;

		server_flag = true;

		input = "";
		output = "";

		portNumber = Integer.parseInt(args[0]);

		System.out.format("Server using PORT: %d\n", portNumber);

		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			while(server_flag) {
				Socket clientSocket = serverSocket.accept();

				System.out.println("Client connected");

				dataInputStream = new DataInputStream(clientSocket.getInputStream());
				dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

				while(!input.equals("quit")) {
					
					input = dataInputStream.readUTF();
					System.out.format("%s\n", input);

					if(input.equals("l")) {
						if(x_coordinate != 0)
							x_coordinate -= 100;
						robot.mouseMove(x_coordinate, y_coordinate);
					}
					else if(input.equals("r")) {
						x_coordinate += 100;
						robot.mouseMove(x_coordinate, y_coordinate);
					}
					else if(input.equals("u")) {
						if(y_coordinate != 0)
							y_coordinate -= 100;
						robot.mouseMove(x_coordinate, y_coordinate);
					}
					else if(input.equals("d")) {
						y_coordinate += 100;
						robot.mouseMove(x_coordinate, y_coordinate);
					}
					else if(input.equals("R_CLICK_PRESS")) {
						robot.mousePress(InputEvent.BUTTON3_MASK);
					}
					else if(input.equals("R_CLICK_RELEASE")) {
						robot.mouseRelease(InputEvent.BUTTON3_MASK);
					}
					else if(input.equals("L_CLICK_PRESS")) {
						robot.mousePress(InputEvent.BUTTON1_MASK);
					}
					else if(input.equals("L_CLICK_RELEASE")) {
						robot.mouseRelease(InputEvent.BUTTON1_MASK);
					}
					else if(input.equals("QUIT")) {
						break;
					}

					

					dataOutputStream.writeUTF("SUCCESS");
				}

				dataOutputStream.writeUTF("QUIT");
				
	            dataInputStream.close();
	            dataOutputStream.close();
	            clientSocket.close();
	        }
            serverSocket.close();
		}
		catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
	}

	public static void communicate() throws ClassNotFoundException {
		
		try {
			//input = (String) inputStream.readObject();
			input = dataInputStream.readUTF();
			System.out.format("%s\n", input);
		}
		catch(IOException e) {
			System.out.println("LAME");
		}
	}
}

