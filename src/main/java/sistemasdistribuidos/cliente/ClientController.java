package sistemasdistribuidos.cliente;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.*;
import java.net.*;
import javafx.application.Platform;
import org.json.JSONObject;
import org.json.JSONException;

public class ClientController {
    @FXML
    private Label welcomeText;

    @FXML
    private Label connectionIPLabel;

    @FXML
    private Label connectionPortLabel;

    @FXML
    private TextField connectionIP;

    @FXML
    private TextField connectionPort;

    @FXML
    private Button helloButton;

    @FXML
    private HBox loginBox;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;
    private boolean connected = false;

    private String token;
    private Socket clientSocket;

    @FXML
    protected void onHelloButtonClick() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    String serverAddress = connectionIP.getText();
                    int serverPort = Integer.parseInt(connectionPort.getText());
                    clientSocket = new Socket(serverAddress, serverPort);

                    loginBox.setVisible(true);
                    connectionIP.setVisible(false);
                    connectionPort.setVisible(false);
                    helloButton.setVisible(false);
                    connectionIPLabel.setVisible(false);
                    connectionPortLabel.setVisible(false);
                    connected = true;

                    String response = receiveMessageFromServer();
                    if (response != null) {
                        Platform.runLater(() -> {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                boolean error = jsonResponse.getBoolean("error");
                                String message = jsonResponse.getString("message");

                                if (!error) {
                                    token = jsonResponse.getJSONObject("data").getString("token");
                                    welcomeText.setText("Logado com sucesso");
                                    System.out.println("Resposta do servidor: " + response);
                                    System.out.println("Token recebido: " + token);
                                } else {
                                    welcomeText.setText("Login failed: " + message);
                                    System.out.println("Server Response: " + message);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                welcomeText.setText("Error parsing JSON response");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private String hashPasswordMD5(String password) {
        return DigestUtils.md5Hex(password).toUpperCase();
    }

    @FXML
    protected void onLoginClick() {
        if (connected) {
            String email = emailField.getText();
            String password = passwordField.getText();

            String hashedPassword = hashPasswordMD5(password);
            String loginRequest = "{ \"action\": \"login\", \"data\": { \"email\": \"" + email + "\", \"password\": \"" + hashedPassword + "\" } }";

            System.out.println("Mandando JSON para servidor: " + loginRequest);
            sendMessageToServer(loginRequest);
        } else {
        }
    }

    @FXML
    protected void onLogoutClicked() {
        if (connected) {
            String logoutRequest = "{ \"action\": \"logout\", \"data\": { \"token\": \"" + token + "\" } }";
            System.out.println("Mandando JSON para servidor: " + logoutRequest);
            sendMessageToServer(logoutRequest);

            String response = receiveMessageFromServer();
            if (response != null) {
                Platform.runLater(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        boolean error = jsonResponse.getBoolean("error");
                        String message = jsonResponse.getString("message");

                        if (!error) {
                            welcomeText.setText("Logout efetuado com sucesso!");
                            System.out.println("Resposta do servidor: " + response);

                            Platform.exit();
                        } else {
                            welcomeText.setText("logout falhou: " + message);
                            System.out.println("Resposta do servidor: " + response);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        welcomeText.setText("Error parsing JSON response");
                    }
                });
            }
        } else {
            welcomeText.setText("Conexão falhou");
        }
    }

    private void sendMessageToServer(String message) {
        try {
            OutputStream out = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String receiveMessageFromServer() {
        try {
            InputStream in = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}