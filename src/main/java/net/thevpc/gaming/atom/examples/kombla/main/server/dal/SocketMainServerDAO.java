/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.thevpc.gaming.atom.examples.kombla.main.server.dal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.thevpc.gaming.atom.examples.kombla.main.shared.dal.ProtocolConstants;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;

/**
 *
 * @author root
 */
public class SocketMainServerDAO implements MainServerDAO{
    private MainServerDAOListener listener;
    private AppConfig properties;
    class ClientSession {
        private int playerID;
        private Socket playerSocket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        public ClientSession(int playerID, Socket playerSocket, ObjectInputStream
                ois, ObjectOutputStream oos){
            this.playerID = playerID;
            this.playerSocket = playerSocket;
            this.ois = ois;
            this.oos = oos;
        }
    }
    private Map<Integer, ClientSession> playerToSocketMap = new HashMap<>();
    @Override
    public void start(MainServerDAOListener listener, AppConfig properties) {
        this.listener = listener;
        this.properties = properties;
        new Thread(() -> {
            try {
                ServerSocket serverS = new ServerSocket();
                while(true){
                    try {
                        Socket s = serverS.accept();
                        ObjectInputStream in = (ObjectInputStream) s.getInputStream();
                        ObjectOutputStream out = (ObjectOutputStream) s.getOutputStream();
                        String name = in.readUTF();
                        StartGameInfo startGameInfo = listener.onReceivePlayerJoined(name);
                        ClientSession client = new ClientSession(startGameInfo.getPlayerId(),s,in,out);
                        this.playerToSocketMap.put(startGameInfo.getPlayerId(), client);
                        out.writeObject(startGameInfo);
                        out.writeInt(ProtocolConstants.OK);
                        switch (client.ois.readInt()){
                            case ProtocolConstants.UP:
                                listener.onReceiveMoveUp(client.playerID);
                                break;
                            case ProtocolConstants.DOWN:
                                listener.onReceiveMoveDown(client.playerID);
                                break;
                            case ProtocolConstants.LEFT:
                                listener.onReceiveMoveLeft(client.playerID);
                                break;
                            case ProtocolConstants.RIGHT:
                                listener.onReceiveMoveRight(client.playerID);
                                break;
                            case ProtocolConstants.FIRE:
                                listener.onReceiveReleaseBomb(client.playerID);
                                break;
                            default:
                                throw new AssertionError();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(SocketMainServerDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(SocketMainServerDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    @Override
    public void sendModelChanged(DynamicGameModel dynamicGameModel) {
        for(Map.Entry<Integer, ClientSession> entry:this.playerToSocketMap.entrySet()){
            try {
                entry.getValue().oos.writeObject(dynamicGameModel);
            } catch (IOException ex) {
                Logger.getLogger(SocketMainServerDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
