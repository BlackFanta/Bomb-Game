/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package net.thevpc.gaming.atom.examples.kombla.main.client.dal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;

/**
 *
 * @author root
 */
public interface GameClient extends Remote{
    public void modelChanged(DynamicGameModel dynamicGameModel) throws RemoteException;
    
    
}
