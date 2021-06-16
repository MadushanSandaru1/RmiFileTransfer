/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmifiletransfer;

import java.rmi.Remote;

/**
 *
 * @author sandaru
 */
import java.rmi.RemoteException;
public interface FileServices extends Remote {
    public void uploadFileToServer(byte[] mydata, String serverpath, int length) throws RemoteException;
    public byte[] downloadFileFromServer(String serverpath) throws RemoteException;
    public String[] listFiles(String serverpath) throws RemoteException;
}
