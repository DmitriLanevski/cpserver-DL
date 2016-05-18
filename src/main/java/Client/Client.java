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
            System.out.println("Echo client opened.");
            if (args.length != 2) {
                System.out.println("cpserver-DL <input> <output>");
                System.exit(1);
            } else {
                dos.writeUTF(args[0]);
                int fileSizeInBytes = Integer.parseInt(dis.readUTF());
                byte[] buffer = new byte[fileSizeInBytes];
                dis.read(buffer, 0, fileSizeInBytes);
                fos.write(buffer, 0, fileSizeInBytes);
            }
        }
    }
}
