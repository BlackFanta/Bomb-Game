/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.thevpc.gaming.atom.examples.kombla.main.client.dal;

import java.rmi.RemoteException;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;

/**
 *
 * @author root
 */
public class GameClientImplementation implements GameClient {
    MainClientDAOListener listener;
    protected GameClientImplementation(MainClientDAOListener listener) throws RemoteException{
        this.listener = listener;
    }
    @Override
    public void modelChanged(DynamicGameModel dynamicGameModel) throws RemoteException {
        listener.onModelChanged(dynamicGameModel);
    }
    
    
}
