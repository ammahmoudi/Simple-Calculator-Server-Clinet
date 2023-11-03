package com.amg.simplecalculatorserver;

import com.amg.simplecalculatorserver.server.ServerHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class ServerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane bottom_stack_pane;

    @FXML
    private HBox bottom_h_box;

    @FXML
    private JFXTextField server_ip_text_field;

    @FXML
    private JFXTextField server_port_text_field;

    @FXML
    private JFXButton connect_button;

    @FXML
    private FontIcon connect_icon;

    @FXML
    private TextArea console_text_area;

    @FXML
    private VBox top_vbox;

    @FXML
    private Label status_label;
    private  static  TextArea console;
    ServerHandler serverHandler;
    @FXML
    void OnConnectButtonAction(ActionEvent event) {
        ServerSocket serverSocket = null;
       if(serverHandler!=null) serverSocket = serverHandler.getServerSocket();
        if(connect_button.getText().equals("stop")){
            try {
                if (serverSocket != null & !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {

                ServerController.writeOnConsole("Something went wrong while closing the serversocket");


            }

            connect_button.setText("run");
        }else {
            ServerHandler.setServerIp(server_ip_text_field.getText());
            ServerHandler.setServerPort(Integer.parseInt(server_port_text_field.getText()));
            serverHandler = new ServerHandler();
            new Thread(serverHandler).start();

            connect_button.setText("stop");

        }
    }

    @FXML
    void initialize() {
        server_ip_text_field.setText(ServerHandler.SERVER_IP);
        server_port_text_field.setText(String.valueOf(ServerHandler.SERVER_PORT));
        console=console_text_area;

    }
    public static void writeOnConsole(String message){
        console.appendText(message+"\n");
    }
}
