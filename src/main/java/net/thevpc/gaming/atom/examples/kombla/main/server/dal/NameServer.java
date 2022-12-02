/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.thevpc.gaming.atom.examples.kombla.main.server.dal;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author root
 */
public class NameServer {
    public NameServer(){
        
    }
    public static void main(String[] args) throws InterruptedException, RemoteException{
        LocateRegistry.createRegistry(1234);
        Object lock = new Object();
        
        synchronized(lock){
            lock.wait();
        }
    }
}
