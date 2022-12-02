/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.thevpc.gaming.atom.examples.kombla.main.client.dal;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.GameServer;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.GameServerImplementation;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class RmiMainClientDAO implements MainClientDAO {
    private MainClientDAOListener listener;
    private AppConfig properties;
    private static Registry reg;
    private static GameServer gs;

    @Override
    public void start(MainClientDAOListener listener, AppConfig properties) {
        try {
            reg = LocateRegistry.getRegistry(properties.getServerAddress(), properties.getServerPort());
        } catch (RemoteException ex) {
            Logger.getLogger(RmiMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.listener = listener;
        this.properties = properties;
            
    }

    @Override
    public StartGameInfo connect() {
        StartGameInfo gInfo= null ;
        try {
            gs = (GameServer) reg.lookup("Server");
        } catch (RemoteException ex) {
            Logger.getLogger(RmiMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(RmiMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            gInfo = gs.connect(properties.getPlayerName(), new GameClientImplementation(listener));
        } catch (RemoteException ex) {
            Logger.getLogger(RmiMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gInfo;
    }

    @Override
    public void sendMoveLeft() {
        try {
            gs.moveLeft();
        } catch (RemoteException ex) {
            Logger.getLogger(RmiMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendMoveRight() {
        try {
            gs.moveRight();
        } catch (RemoteException ex) {
            Logger.getLogger(RmiMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendMoveUp() {
        try {
            gs.moveUp();
        } catch (RemoteException ex) {
            Logger.getLogger(RmiMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendMoveDown() {
        try {
            gs.moveDown();
        } catch (RemoteException ex) {
            Logger.getLogger(RmiMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendFire() {
        try {
            gs.fire();
        } catch (RemoteException ex) {
            Logger.getLogger(RmiMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
