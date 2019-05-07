package server;

import server.MockServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        new MockServer().start();
    }
}
