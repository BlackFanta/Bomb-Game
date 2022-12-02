/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.thevpc.gaming.atom.examples.kombla.main.server.dal;

import java.rmi.RemoteException;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.GameClientImplementation;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;

/**
 *
 * @author root
 */
public class GameServerImplementation implements GameServer {
    MainServerDAOListener listener;
    StartGameInfo gInfo;
    
    @Override
    public StartGameInfo connect(String player, GameClientImplementation gameClient) {
        gInfo = listener.onReceivePlayerJoined(player);
        return this.gInfo;
    }

    @Override
    public void moveRight() throws RemoteException {
        listener.onReceiveMoveRight(gInfo.getPlayerId());
    }

    @Override
    public void moveLeft() throws RemoteException {
        listener.onReceiveMoveLeft(gInfo.getPlayerId());
    }

    @Override
    public void moveUp() throws RemoteException {
        listener.onReceiveMoveUp(gInfo.getPlayerId());
    }

    @Override
    public void moveDown() throws RemoteException {
        listener.onReceiveMoveDown(gInfo.getPlayerId());
    }

    @Override
    public void fire() throws RemoteException {
        listener.onReceiveReleaseBomb(gInfo.getPlayerId());
    }
    
}
