package Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by lanev_000 on 3.03.2016.
 */
public class Client {
    public static void main(String[] args) throws IOException{
        System.out.println(args[1] + "\\" + args[0]);
        File outputFile = new File(args[1] + "\\" + args[0]);
        if (!outputFile.exists()){
            outputFile.createNewFile();
        }
        try (Socket socket = new Socket(InetAddress.getLocalHost(), 1337);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream());
             FileOutputStream fos = new FileOutputStream(outputFile)){
            System.out.println("Client connected to server. Starting copying of file " + args[0] + ".");
            if (args.length != 2) {
                System.out.println("cpserver-DL <input> <output>");
                System.exit(1);
            } else {
                System.out.println("Sending query.");
                dos.writeUTF(args[0]);
                System.out.println("Receiving file.");
                int fileSizeInBytes = Integer.parseInt(dis.readUTF());
                byte[] buffer = new byte[fileSizeInBytes];
                dis.read(buffer, 0, fileSizeInBytes);
                System.out.println("Creating copy of a file in directory " + args[1] + ".");
                fos.write(buffer, 0, fileSizeInBytes);
                System.out.println("File copying procedure complete.");
            }
        }
    }
}
