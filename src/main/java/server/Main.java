package server;

/*
This is an app that allows you to create a mock server for API calls
Simply place the mapping ie the request URL and which JSON file it should bring back
Once the server is started you can use the cucumber framework that calling the urls defined here
and you will be returned the response mapped

 */


import server.MockServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        new MockServer().start();
    }
}
