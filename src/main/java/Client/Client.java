package Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by lanev_000 on 3.03.2016.
 */
public class Client {
    public static void main(String[] args) throws IOException{

        try (Socket socket = new Socket(InetAddress.getLocalHost(), 1337);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())){
            System.out.println("Client connected to server. Starting copying of file " + args[0] + ".");
            if (args.length != 2) {
                System.out.println("cpserver-DL <input> <output>");
                System.exit(1);
            } else {
                System.out.println("Sending query.");
                dos.writeUTF(args[0]);

                System.out.println("Receiving file.");

                System.out.println(args[1] + "\\" + args[0]);
                File outputFile = new File(args[1] + "\\" + args[0]);
                if (!outputFile.exists()){
                    outputFile.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(outputFile);

                int fileSizeInBytes = dis.readInt();

                byte[] buffer = new byte[1024];
                int byteCount;
                int sumCounter = 0;

                while ((byteCount = dis.read(buffer)) != -1){
                    fos.write(buffer, 0, byteCount);
                    sumCounter += byteCount;
                }

                if (sumCounter == fileSizeInBytes){
                    System.out.println("File copying procedure complete.");
                } else {
                    System.out.println("Something went wrong.");
                }

                fos.flush();
                fos.close();

            }
        }
    }
}
