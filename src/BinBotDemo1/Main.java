package BinBotDemo1;

import BinBotDemo1.connections.ServerConnection;
import BinBotDemo1.instructions.Instruction;
import BinBotDemo1.instructions.Status;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main
{
    static final String IP = "127.0.0.1";
    static final int PORT = 7001;

    static ServerConnection connection;

    public static void main(String[] args) throws IOException {
        String path = "C:/Users/Sean/Desktop/Client/";
        String recieveFilename = "recieved.jpg";
        System.out.println("Connecting to " + IP + " " + PORT + "...");
        connection = new ServerConnection(IP, PORT);
        System.out.println("Connection established!");

        System.out.println("Attempting to recieve");
        String jsonRecieve = connection.recieve();
        System.out.println("received: " + jsonRecieve);
        Instruction instrRecieve = new Instruction(jsonRecieve);
        System.out.println("Saving image at " + path + recieveFilename);
        ImageIO.write(instrRecieve.img(), "jpg", new File(path + recieveFilename));
        System.out.println("saved!");

        BufferedImage img = ImageIO.read(new File(path + recieveFilename));
        Instruction instrSend = new Instruction(Status.NAVIGATE, img, null, null);
        System.out.print("Sending: " + instrSend.json());
        connection.send(instrSend.json());
        System.out.println("Sent");
    }
}