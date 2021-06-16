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
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author sandaru
 */
public class FileServicesImp extends UnicastRemoteObject implements FileServices, Serializable {

    public FileServicesImp() throws RemoteException {
        super();
    }

    @Override
    public void uploadFileToServer(byte[] mydata, String serverpath, int length) throws RemoteException {
        try {
            File serverpathfile = new File(serverpath);
            FileOutputStream out = new FileOutputStream(serverpathfile);
            byte [] data = mydata;

            out.write(data);
            out.flush();
            out.close();

        } catch (IOException e) {
            System.out.println("File upload error: "+e.getMessage());
        }
    	
    	System.out.println("Done writing data...");
    }

    @Override
    public byte[] downloadFileFromServer(String serverpath) throws RemoteException {
        byte [] mydata;	
		
        File serverpathfile = new File(serverpath);			
        mydata=new byte[(int) serverpathfile.length()];
        FileInputStream in;
        
        try {
            in = new FileInputStream(serverpathfile);
            try {
                in.read(mydata, 0, mydata.length);
            } catch (IOException e) {
                e.printStackTrace();
            }						
            try {
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }		

        return mydata;
    }
    
    @Override
    public String[] listFiles(String serverpath) throws RemoteException {
        File serverpathdir = new File(serverpath);
        return serverpathdir.list();
    }
    
}
