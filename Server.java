
import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;

public class Server {
        public static void main(String args[]) {
                try {
                        int serverPort = 6880;
                        ServerSocket networkSocket = new ServerSocket(serverPort);
                        System.out.println("Server listening on port " + serverPort);
                        while (true) {
                                Socket clientSocket = networkSocket.accept();
                                InputStream input = clientSocket.getInputStream();
                                BufferedReader br = new BufferedReader(new InputStreamReader(input));

                                String req = br.readLine();
                                String[] reqArray = req.split(" "); // ['GET', 'index.html', 'HTTP/1.1']

                                String filePath = reqArray[1].substring(1); 
                                if (filePath != null)
                                        loadPage(clientSocket, filePath, br);
                                
                        }
                } catch (IOException e) {
                        System.out.println("Listen :" + e.getMessage());
                }
        }

        public static void loadPage(Socket clientSocket, String filePath, BufferedReader br) {
                try {

                        PrintWriter output = new PrintWriter(clientSocket.getOutputStream());
                        File file = new File(filePath);
                        if (!file.exists()) {
                                output.write("HTTP/1.1 404 Not Found\r\n" + "Content-Type: text/html" + "\r\n\r\n");
                                byte[] fileBytes = Files.readAllBytes(Paths.get("error.html"));
                                String fileString = new String(fileBytes, StandardCharsets.UTF_8);
                                output.write(fileString);
                                System.out.println("[INCOMING] File does not exist. Client requesting file at : " + filePath);
                                output.close();
                        }

                        else {
                                output.write("HTTP/1.1 200 OK\r\n" + "Content-Type: text/html" + "\r\n\r\n");
                                byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
                                String fileString = new String(fileBytes, StandardCharsets.UTF_8);
                                output.write(fileString);

                                System.out.println("[INCOMING] responded successfully");
                                output.close();

                        }

                } catch (IOException e) {
                        System.out.println("Connection: " + e.getMessage());
                }
        }
}