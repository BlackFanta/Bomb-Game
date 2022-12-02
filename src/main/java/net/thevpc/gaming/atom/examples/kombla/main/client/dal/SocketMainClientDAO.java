/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.thevpc.gaming.atom.examples.kombla.main.client.dal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.thevpc.gaming.atom.examples.kombla.main.shared.dal.ProtocolConstants;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;

/**
 *
 * @author nader
 */
public class SocketMainClientDAO implements MainClientDAO {
    private AppConfig properties;
    private MainClientDAOListener listener;
    private Socket s;
    public ObjectInputStream oin;
    public ObjectOutputStream oos;
    public StartGameInfo o;
    public DynamicGameModel model;
    @Override
    public void start(MainClientDAOListener listener, AppConfig properties) {
        this.properties= properties;
        this.listener= listener;
    }

    @Override
    public StartGameInfo connect() {
        StartGameInfo startGameInfo=null;
        try {
            s= new Socket(this.properties.getServerAddress(), this.properties.getServerPort());
        } catch (IOException ex) {
            Logger.getLogger(SocketMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
                    try {
                        oin = new ObjectInputStream(s.getInputStream());
                    } catch (IOException ex) {
                        Logger.getLogger(SocketMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        oos = new ObjectOutputStream(s.getOutputStream());
                    } catch (IOException ex) {
                        Logger.getLogger(SocketMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        oos.writeInt(ProtocolConstants.CONNECT);
                    } catch (IOException ex) {
                        Logger.getLogger(SocketMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        oos.writeUTF(properties.getPlayerName());
                    } catch (IOException ex) {
                        Logger.getLogger(SocketMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
               
        try {
            oin.readInt();
        } catch (IOException ex) {
            Logger.getLogger(SocketMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            startGameInfo = (StartGameInfo) oin.readObject();
        } catch (IOException ex) {
            Logger.getLogger(SocketMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SocketMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        onLoopReceiveModelChanged();
        return startGameInfo;
    }
    public void onLoopReceiveModelChanged(){
        Thread thread = new Thread(() -> {
                while(true){
                    try {
                        model = (DynamicGameModel) oin.readObject();
                    } catch (IOException ex) {
                        Logger.getLogger(SocketMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(SocketMainClientDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    listener.onModelChanged(model);
                }
                });
        thread.start();
    }
    @Override
    public void sendMoveLeft() {
        try{
            oos.writeInt(ProtocolConstants.LEFT);
            
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMoveRight() {
        try{
            oos.writeInt(ProtocolConstants.RIGHT);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMoveUp() {
        try{
            oos.writeInt(ProtocolConstants.UP);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMoveDown() {
        try{
            oos.writeInt(ProtocolConstants.DOWN);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendFire() {
        try{
            oos.writeInt(ProtocolConstants.FIRE);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    
}
}
