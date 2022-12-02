/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.thevpc.gaming.atom.examples.kombla.main.server.dal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.GameClientImplementation;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;


/**
 *
 * @author root
 */
public interface GameServer extends Remote{
    public StartGameInfo connect(String player, GameClientImplementation gameClient);
    void moveRight() throws RemoteException;
    void moveLeft() throws RemoteException;
    void moveUp() throws RemoteException;
    void moveDown() throws RemoteException;
    void fire() throws RemoteException;
    
    
}
