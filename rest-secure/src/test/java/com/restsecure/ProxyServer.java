package com.restsecure;

import lombok.Getter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author jcgonzalez.com
 */
public class ProxyServer {
    private static ThreadProxy threadProxy;

    public static void runServer(String host, int remotePort, int localPort) {
        try {
            // and the local port that we listen for connections on
            // Print a start-up message
            System.out.println("Starting proxy for " + host + ":" + remotePort
                    + " on port " + localPort);
            ServerSocket server = new ServerSocket(localPort);
            threadProxy = new ThreadProxy(server, host, remotePort);
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Usage: java ProxyMultiThread "
                    + "<host> <remoteport> <localport>");
        }
    }

    public static boolean isUsed() {
        return threadProxy.isUsed();
    }

    public static void teardown() {
        if (threadProxy != null) {
            try {
                threadProxy.teardown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * Handles a socket connection to the proxy server from the client and uses 2
 * threads to proxy between server and client
 *
 * @author jcgonzalez.com
 */
class ThreadProxy extends Thread {
    @Getter
    private boolean isUsed;
    private Socket sClient;
    private ServerSocket socket;
    private final String SERVER_URL;
    private final int SERVER_PORT;

    ThreadProxy(ServerSocket socket, String ServerUrl, int ServerPort) {
        this.SERVER_URL = ServerUrl;
        this.SERVER_PORT = ServerPort;
        this.socket = socket;
        this.start();
    }

    @Override
    public void run() {
        try {
            sClient = socket.accept();
            final byte[] request = new byte[1024];
            byte[] reply = new byte[4096];
            final InputStream inFromClient = sClient.getInputStream();
            final OutputStream outToClient = sClient.getOutputStream();
            Socket client = null, server = null;
            // connects a socket to the server
            try {
                server = new Socket(SERVER_URL, SERVER_PORT);
            } catch (IOException e) {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(outToClient));
                out.flush();
                System.out.println("SERVER_URL = " + SERVER_URL + " : " + SERVER_PORT);
                throw new RuntimeException(e);
            }
            // a new thread to manage streams from server to client (DOWNLOAD)
            final InputStream inFromServer = server.getInputStream();
            final OutputStream outToServer = server.getOutputStream();
            // a new thread for uploading to the server
            new Thread(() -> {
                int bytes_read;
                try {
                    while ((bytes_read = inFromClient.read(request)) != -1) {
                        outToServer.write(request, 0, bytes_read);
                        outToServer.flush();
                        isUsed = true;
                    }
                } catch (IOException e) {
                }
                try {
                    outToServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            // current thread manages streams from server to client (DOWNLOAD)
            int bytes_read;
            try {
                while ((bytes_read = inFromServer.read(reply)) != -1) {
                    outToClient.write(reply, 0, bytes_read);
                    outToClient.flush();
                    //TODO CREATE YOUR LOGIC HERE
                }
            } catch (IOException e) {
                // e.printStackTrace();
            } finally {
                try {
                    if (server != null)
                        server.close();
                    if (client != null)
                        client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            outToClient.close();
            sClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void teardown() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        interrupt();
    }
}
