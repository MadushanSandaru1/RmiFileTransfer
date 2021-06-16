/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmifiletransfer;


import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author sandaru
 */
public class FileServer implements Serializable {
    public static void main(String[] args) {
        
        try {
            Registry reg = LocateRegistry.createRegistry(Configurations.PORT_NO);
            
            FileServicesImp imp =  new FileServicesImp();
            reg.bind(Configurations.SERVER_NAME, imp);
            
            System.out.println("Server is running on port "+Configurations.PORT_NO);
        } catch(Exception e) {
            System.out.println("Server failed: " + e);
	}
        
    }
}
