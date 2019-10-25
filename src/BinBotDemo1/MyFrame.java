package BinBotDemo1;

import BinBotDemo1.connections.ServerConnection;
import BinBotDemo1.instructions.Instruction;
import BinBotDemo1.instructions.Status;
import org.opencv.core.Core;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MyFrame extends JFrame {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JPanel contentPane;
    private VideoCap videoCap = new VideoCap();

    static final String IP = "127.0.0.1";
    static final int PORT = 7001;

    static ServerConnection connection;

    public static void main(String[] args) throws IOException {
        setup();
        EventQueue.invokeLater(() -> {

            try {
                MyFrame frame = new MyFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    public MyFrame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1280, 720);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        new MyThread().start();

    }

    public static void setup() throws IOException {
        System.out.println("Connecting to " + IP + " " + PORT + "...");
        connection = new ServerConnection(IP, PORT);
        System.out.println("Connection established!");
    }

    public static void sendCapture(BufferedImage image) {
        // Send to server
        Instruction imgSend = new Instruction(Status.NAVIGATE, image, null, null);
//        System.out.print("Sending: " + imgSend.json());
        System.out.print("Sending image to server..");
        try {
            connection.send(imgSend.json());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sent");
    }

    public static BufferedImage receiveCapture() {
        System.out.println("Attempting to receive");
        String jsonReceive = null;
        try {
            jsonReceive = connection.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("received: " + jsonReceive);
        System.out.println("received image from server");
        Instruction imgReceive = new Instruction(jsonReceive);
        return imgReceive.img();
    }

    public void paint(Graphics g) {
        g = contentPane.getGraphics();
        BufferedImage image = videoCap.getOneFrame();
        sendCapture(image);
        image = receiveCapture();

        g.drawImage(image, 0, 0, this);
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            for (; ; ) {
                repaint();
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
