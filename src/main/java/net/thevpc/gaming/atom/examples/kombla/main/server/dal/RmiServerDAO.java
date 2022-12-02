/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.thevpc.gaming.atom.examples.kombla.main.server.dal;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.GameClient;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;

/**
 *
 * @author root
 */
public class RmiServerDAO implements MainServerDAO {
    private static Registry reg;

    @Override
    public void start(MainServerDAOListener listener, AppConfig properties) {
        try {
            reg = LocateRegistry.getRegistry(properties.getServerAddress(),
                    properties.getServerPort());
            reg.bind("gameserver", new GameServerImplementation());
        } catch (RemoteException ex) {
            Logger.getLogger(RmiServerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(RmiServerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void sendModelChanged(DynamicGameModel dynamicGameModel) {
        try {
            GameClient client = null;
            client.modelChanged(dynamicGameModel);
        } catch (RemoteException ex) {
            Logger.getLogger(RmiServerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
