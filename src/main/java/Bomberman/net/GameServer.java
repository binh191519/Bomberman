package Bomberman.net;

import Bomberman.BombermanGame;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

public class GameServer extends Thread {
  private DatagramSocket socket;
  private BombermanGame bombermanGame;

  public GameServer(BombermanGame bombermanGame) {
    this.bombermanGame = bombermanGame;
    try {
      socket = new DatagramSocket(1331);
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
  }

  public void run() {
    while (true) {
      byte[] data = new byte[1024];
      DatagramPacket packet = new DatagramPacket(data, data.length);
      try {
        socket.receive(packet);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      String message = new String(packet.getData());
      System.out.println("CLIENT > " + message);
      if (message.trim().equalsIgnoreCase("ping")) {
        sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
      }
    }
  }

  public void sendData(byte[] data, InetAddress ipAddress, int port) {
    DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
    try {
      socket.send(packet);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
