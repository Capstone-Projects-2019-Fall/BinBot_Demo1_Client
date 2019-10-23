package edu.temple.cis.capstone.BinBotDemo1;

import edu.temple.cis.capstone.BinBotDemo1.connections.ServerConnection;
import edu.temple.cis.capstone.BinBotDemo1.instructions.Instruction;
import edu.temple.cis.capstone.BinBotDemo1.instructions.Status;
import edu.temple.cis.capstone.BinBotDemo1.instructions.TreadInstruction;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;

public class Main
{
	static final String IP = "127.0.0.1";
	static final int PORT = 7001;

	static ServerConnection connection;

	public static void main(String[] args) throws IOException {
		System.out.println("Connecting to " + IP + " " + PORT + "...");
		connection = new ServerConnection(IP, PORT);
		System.out.println("Connection established!");

		List<Pair<Double, Double>> l = TreadInstruction.calcInstructions(0, 0, 0, 0);

		Instruction instruction = new Instruction(Status.NAVIGATE, null, l, null);
		System.out.println("Attempting to recieve");
		System.out.println("received: " + connection.recieve());
		System.out.print("Sending: ");
		System.out.println(instruction.json());
		connection.send(instruction.json());
		System.out.println("Sent");
	}
}
