/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmifiletransfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sandaru
 */
public class FileClient implements Serializable {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        Scanner scanner = new Scanner(System.in);
        FileServices stub;
        String server_file_path, client_file_path, file_name;
        byte[] mydata;
        File clientpathfile;
        FileOutputStream out;
        FileInputStream in;
        String[] filelist;
        
        try {
            stub = (FileServices) Naming.lookup("//localhost:"+Configurations.PORT_NO+"/"+Configurations.SERVER_NAME);
        } catch (Exception ex) {
            System.out.println("Client error "+ex.getMessage());
            return;
        }
        
        while(true) {
            System.out.println("\n1 - Upload to server\n2 - Download from server\n3 - Files list in server\n4 - Files list in client\n0 - Exit\n");
            System.out.print("Enter option: ");

            int op = scanner.nextInt();
            
            switch(op) {
                case 1: 
                    
                        filelist = stub.listFiles(Configurations.CLIENT_DIRECTORY);
                        System.out.println("\nFiles list in client");
                        for (String i: filelist)
                        {
                            System.out.println(i);
                        }
                        
                        System.out.print("\nEnter file name: ");
                        file_name = scanner.next();
                        
                        client_file_path= Configurations.CLIENT_DIRECTORY+file_name.trim();
                        server_file_path = Configurations.SERVER_DIRECTORY+file_name.trim();

                        try {
                            clientpathfile = new File(client_file_path);
                            
                            mydata = new byte[(int) clientpathfile.length()];
                            
                            in = new FileInputStream(clientpathfile);

                            System.out.println("uploading to server...");

                            in.read(mydata, 0, mydata.length);			 
                            stub.uploadFileToServer(mydata, server_file_path, (int) clientpathfile.length());

                            in.close();
                        } catch (FileNotFoundException e) {
                            System.out.println("File not exist...");
                        }
                        
                        break;
                case 2: 
                        filelist = stub.listFiles(Configurations.SERVER_DIRECTORY);
                        System.out.println("\nFiles list in server");
                        for (String i: filelist)
                        {
                            System.out.println(i);
                        }
                        
                        System.out.print("\nEnter file name: ");
                        file_name = scanner.next();
                        
                        client_file_path= Configurations.CLIENT_DIRECTORY+file_name;
                        server_file_path = Configurations.SERVER_DIRECTORY+file_name;
                        
                        try {
                            mydata = stub.downloadFileFromServer(server_file_path);
                            
                            System.out.println("downloading...");
                            
                            clientpathfile = new File(client_file_path);
                            out = new FileOutputStream(clientpathfile);				
                            out.write(mydata);
                            out.flush();
                            out.close();
                        } catch (FileNotFoundException e) {
                            System.out.println("File not exist...");
                        }
                        
                        break;
                case 3: 
                        filelist = stub.listFiles(Configurations.SERVER_DIRECTORY);
                        System.out.println("\nFiles list in server");
                        for (String i: filelist)
                        {
                            System.out.println(i);
                        }
                        break;
                case 4: 
                        filelist = stub.listFiles(Configurations.CLIENT_DIRECTORY);
                        System.out.println("\nFiles list in client");
                        for (String i: filelist)
                        {
                            System.out.println(i);
                        }
                        break;
                case 0: 
                        System.out.println("Have a good day...");
                        System.exit(0);
                        break;
                default:
                        System.out.println("Invalid option");
                        break;
            }
        }
        
    }
}
