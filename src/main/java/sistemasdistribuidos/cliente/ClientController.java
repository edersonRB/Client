package sistemasdistribuidos.cliente;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class ClientController {
    private static final String SECRET_KEY = "AoT3QFTTEkj16rCby/TPVBWvfSQHL3GeEz3zVwEd6LDrQDT97sgDY8HJyxgnH79jupBWFOQ1+7fRPBLZfpuA2lwwHqTgk+NJcWQnDpHn31CVm63Or5c5gb4H7/eSIdd+7hf3v+0a5qVsnyxkHbcxXquqk9ezxrUe93cFppxH4/kF/kGBBamm3kuUVbdBUY39c4U3NRkzSO+XdGs69ssK5SPzshn01axCJoNXqqj+ytebuMwF8oI9+ZDqj/XsQ1CLnChbsL+HCl68ioTeoYU9PLrO4on+rNHGPI0Cx6HrVse7M3WQBPGzOd1TvRh9eWJrvQrP/hm6kOR7KrWKuyJzrQh7OoDxrweXFH8toXeQRD8=";

    @FXML
    private Label pageTitle;

    @FXML
    private TextField connectionIP;

    @FXML
    private TextField connectionPort;

    @FXML
    private HBox loginBox;

    @FXML
    private HBox connectBox;

    @FXML
    private VBox registerBox;

    @FXML
    private VBox profileBox;

    @FXML
    private HBox newPointBox;

    @FXML
    private VBox newSegmentBox;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField register_name;

    @FXML
    private TextField register_email;

    @FXML
    private PasswordField register_password;

    @FXML
    private TextField pointName;

    @FXML
    private TextField pointObs;

    @FXML
    private VBox listBox;
    
    @FXML
    private VBox changeBox;
    
    @FXML
    private TextField change_id;

    @FXML
    private TextField change_name;
    
    @FXML
    private TextField change_email;
    
    @FXML
    private TextField change_password;

    @FXML
    private Button newPointButton;

    @FXML
    private Button newSegmentButton;

    @FXML
    private TextField deleteUserId;
    
    @FXML
    private HBox deleteUserBox;

    @FXML
    private TextField deletePointId;

    @FXML
    private TextField deleteSegmentId;

    @FXML
    private HBox deletePointBox;

    @FXML
    private HBox deleteSegmentBox;

    @FXML
    private Button listUsersButton;

    @FXML
    private TextField segmentDirection;

    @FXML
    private TextField segmentDistance;

    @FXML
    private TextField segmentObs;

    @FXML
    private TextField segmentOrigin;

    @FXML
    private TextField segmentDestiny;

    private boolean connected = false;

    private User user;

    private ArrayList<Point> pointList = new ArrayList<>();

    private ArrayList<Segment> segmentList = new ArrayList<>();

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

                    goToLogin();
//                    goToRegister();

                    connected = true;
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

    private void setTitle(String str) {
        Platform.runLater(() -> {
            pageTitle.setText(str); // Update the label on the JavaFX Application Thread
        });
    }

    private void goToConnection() {
        setTitle("Conecte-se a um servidor");
        connectBox.setVisible(true);
        connectBox.setManaged(true);
        profileBox.setVisible(false);
        profileBox.setManaged(false);
        loginBox.setVisible(false);
        loginBox.setManaged(false);
        registerBox.setVisible(false);
        registerBox.setManaged(false);
        changeBox.setVisible(false);
        changeBox.setManaged(false);
        newPointBox.setVisible(false);
        newPointBox.setManaged(false);
        newSegmentBox.setManaged(false);
        newSegmentBox.setVisible(false);
    }
    @FXML
    private void goToLogin() {
        setTitle("Entre");
        connectBox.setVisible(false);
        connectBox.setManaged(false);
        profileBox.setVisible(false);
        profileBox.setManaged(false);
        loginBox.setVisible(true);
        loginBox.setManaged(true);
        registerBox.setVisible(false);
        registerBox.setManaged(false);
        changeBox.setVisible(false);
        changeBox.setManaged(false);
        newPointBox.setVisible(false);
        newPointBox.setManaged(false);
        newSegmentBox.setManaged(false);
        newSegmentBox.setVisible(false);
    }

    private void goToProfile() {
        setTitle("Menu");
        connectBox.setVisible(false);
        connectBox.setManaged(false);
        profileBox.setVisible(true);
        profileBox.setManaged(true);
        loginBox.setVisible(false);
        loginBox.setManaged(false);
        registerBox.setVisible(false);
        registerBox.setManaged(false);
        changeBox.setVisible(false);
        changeBox.setManaged(false);
        newPointBox.setVisible(false);
        newPointBox.setManaged(false);
        newSegmentBox.setManaged(false);
        newSegmentBox.setVisible(false);
    }

    @FXML
    private void goToNewPoint() {
        setTitle("Cadastrar novo ponto");
        connectBox.setVisible(false);
        connectBox.setManaged(false);
        profileBox.setVisible(false);
        profileBox.setManaged(false);
        loginBox.setVisible(false);
        loginBox.setManaged(false);
        registerBox.setVisible(false);
        registerBox.setManaged(false);
        changeBox.setVisible(false);
        changeBox.setManaged(false);
        newPointBox.setVisible(true);
        newPointBox.setManaged(true);
        newSegmentBox.setManaged(false);
        newSegmentBox.setVisible(false);
    }

    @FXML
    private void goToNewSegment() {
        setTitle("Cadastrar novo segmento");
        connectBox.setVisible(false);
        connectBox.setManaged(false);
        profileBox.setVisible(false);
        profileBox.setManaged(false);
        loginBox.setVisible(false);
        loginBox.setManaged(false);
        registerBox.setVisible(false);
        registerBox.setManaged(false);
        changeBox.setVisible(false);
        changeBox.setManaged(false);
        newPointBox.setVisible(false);
        newPointBox.setManaged(false);
        newSegmentBox.setManaged(true);
        newSegmentBox.setVisible(true);
    }
    private void goToRegister() {
        setTitle("Cadastre-se");
        connectBox.setVisible(false);
        connectBox.setManaged(false);
        profileBox.setVisible(false);
        profileBox.setManaged(false);
        loginBox.setVisible(false);
        loginBox.setManaged(false);
        registerBox.setVisible(true);
        registerBox.setManaged(true);
        changeBox.setVisible(false);
        changeBox.setManaged(false);
        newSegmentBox.setManaged(false);
        newSegmentBox.setVisible(false);
    }

    private void goToChange() {
        setTitle("Meu perfil");
        connectBox.setVisible(false);
        connectBox.setManaged(false);
        profileBox.setVisible(false);
        profileBox.setManaged(false);
        loginBox.setVisible(false);
        loginBox.setManaged(false);
        registerBox.setVisible(false);
        registerBox.setManaged(false);
        changeBox.setVisible(true);
        changeBox.setManaged(true);
        newSegmentBox.setManaged(false);
        newSegmentBox.setVisible(false);
    }
    
    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showUser(User user) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Meus dados:");
        alert.setContentText(user.displayUser());
        alert.showAndWait();
    }
    private static Jws<Claims> parseToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
    }
    public static boolean isAdmin(String token) {
        Jws<Claims> parsedToken = parseToken(token);

        return (boolean) parsedToken.getBody().get("admin", Boolean.class);
    }
    @FXML
    protected void onLoginClick() {
        if (connected) {
            String email = emailField.getText();
            String password = passwordField.getText();

            String hashedPassword = hashPasswordMD5(password);
            String loginRequest = "{ \"action\": \"login\", \"data\": { \"email\": \"" + email + "\", \"password\": \"" + hashedPassword + "\" } }";

            System.out.println("C-->S: " + loginRequest);
            sendMessageToServer(loginRequest);

            String response = receiveMessageFromServer();
            if (response != null) {
                Platform.runLater(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        String action = jsonResponse.getString("action");
                        boolean error = jsonResponse.getBoolean("error");
                        String message = jsonResponse.getString("message");

                        if (!error) {
                            token = jsonResponse.getJSONObject("data").getString("token");

                            clearLoginFields();
                            goToProfile();
                            System.out.println("S-->C: " + response);
                            if(isAdmin(token)) {
                                listUsersButton.setVisible(true);
                                listUsersButton.setManaged(true);
                                deleteUserBox.setVisible(true);
                                deleteUserBox.setManaged(true);
                                deletePointBox.setVisible(true);
                                deletePointBox.setManaged(true);
                                deleteSegmentBox.setVisible(true);
                                deleteSegmentBox.setManaged(true);
                                newPointButton.setVisible(true);
                                newPointButton.setManaged(true);
                                newSegmentButton.setVisible(true);
                                newSegmentButton.setManaged(true);
                            }
                            else {
                                listUsersButton.setVisible(false);
                                listUsersButton.setManaged(false);
                                deleteUserBox.setVisible(false);
                                deleteUserBox.setManaged(false);
                                deletePointBox.setVisible(false);
                                deletePointBox.setManaged(false);
                                deleteSegmentBox.setVisible(true);
                                deleteSegmentBox.setManaged(true);
                                newPointButton.setVisible(false);
                                newPointButton.setManaged(false);
                                newSegmentButton.setVisible(true);
                                newSegmentButton.setManaged(true);
                            }
                        } else {
                            showWarning(message);
                            System.out.println("S-->C: " + response);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("Error parsing JSON response");
                    }
                });
            }
        } else {
        }
    }

    private void clearRegisterFields() {
        register_password.clear();
        register_email.clear();
        register_name.clear();
    }

    private void clearNewPointFields() {
        pointName.clear();
        pointObs.clear();
    }
    private void clearLoginFields() {
        emailField.clear();
        passwordField.clear();
    }
    
    private void clearChangeFields() {
    	change_id.clear();
    	change_name.clear();
    	change_password.clear();
    }
    
    @FXML
    protected void onSelfRegisterClick() {
        goToRegister();
    }
    @FXML
    protected void ongoToProfile() {
    	goToProfile();
    }
    @FXML
    protected void onChangeUserClick() {
    	clearChangeFields();

        findMyUser();

    	if(isAdmin(token)) {
    		change_id.setDisable(false);
    	} else {
    		change_id.setDisable(true);
    		//change_id.setEditable(false);
    		//change_id.setText("1");
    	}
    	
    	goToChange();
    }

    @FXML
    protected void onRegisterClick() {
        if (connected) {
            String name = register_name.getText();
            String email = register_email.getText();
            String password = register_password.getText();

            String hashedPassword = hashPasswordMD5(password);
            String registerRequest = "{ \"action\": \"autocadastro-usuario\", \"data\": {  \"name\": \"" + name + "\", \"email\": \"" + email + "\", \"password\": \"" + hashedPassword + "\" } }";

            System.out.println("C-->S: " + registerRequest);
            sendMessageToServer(registerRequest);

            String response = receiveMessageFromServer();
            if (response != null) {
                Platform.runLater(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        String action = jsonResponse.getString("action");
                        boolean error = jsonResponse.getBoolean("error");
                        String message = jsonResponse.getString("message");

                        if (!error && action.equals("autocadastro-usuario")) {
                            clearRegisterFields();
                        } else {
                            showWarning(message);
                        }
                        System.out.println("S-->C: " + response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("Error parsing JSON response");
                    }
                });
            }
        } else {
            showWarning("Erro na conexão!");
        }
    }

    @FXML
    protected void onNewPointClick() {
        goToNewPoint();
    }

    @FXML
    protected void onNewSegmentClick() {
        goToNewSegment();
    }

    @FXML
    protected void onSaveNewPointClick() {
        if (connected) {
            String name = pointName.getText();
            String obs = pointObs.getText();

            String registerRequest = "{ \"action\": \"cadastro-ponto\", \"data\": {  \"token\": \"" + token + "\", \"name\": \"" + name + "\", \"obs\": \"" + obs + "\" } }";

            System.out.println("C-->S: " + registerRequest);
            sendMessageToServer(registerRequest);

            String response = receiveMessageFromServer();
            if (response != null) {
                Platform.runLater(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        String action = jsonResponse.getString("action");
                        boolean error = jsonResponse.getBoolean("error");
                        String message = jsonResponse.getString("message");

                        if (!error && action.equals("cadastro-ponto")) {
                            clearNewPointFields();
                        } else {
                            showWarning(message);
                        }
                        System.out.println("S-->C: " + response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("Error parsing JSON response");
                    }
                });
            }
        } else {
            showWarning("Erro na conexão!");
        }
    }

    public void findPoints() {
        // Clear the existing points in the list
        pointList.clear();

        String listRequest = "{ \"action\": \"listar-pontos\", \"data\": {  \"token\": \"" + token + "\"} }";
        System.out.println("C-->S: " + listRequest);
        sendMessageToServer(listRequest);

        String response = receiveMessageFromServer();
        System.out.println("S-->C: " + response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            JSONArray pointsArray = dataObject.getJSONArray("pontos");

            listBox.setVisible(true);
            listBox.getChildren().clear();

            for (int i = 0; i < pointsArray.length(); i++) {
                JSONObject pointObject = pointsArray.getJSONObject(i);

                int id = pointObject.getInt("id");
                String name = pointObject.getString("name");
                String obs = pointObject.getString("obs");

                // Create a Point object (you may need to create a Point class)
                Point point = new Point(id, name, obs);

                // Add the point to the list
                pointList.add(point);

                // Display the point in the UI
                Text usuario = new Text("Id: " + id + " - Nome: " + name + " | Obs: " + obs);
                listBox.getChildren().add(usuario);
            }
        } catch (JSONException e) {
            System.out.println(e.toString());
        }
    }

    public void findSegments() {
        // Clear the existing points in the list
        segmentList.clear();

        String listRequest = "{ \"action\": \"listar-segmentos\", \"data\": {  \"token\": \"" + token + "\"} }";
        System.out.println("C-->S: " + listRequest);
        sendMessageToServer(listRequest);

        String response = receiveMessageFromServer();
        System.out.println("S-->C: " + response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            JSONArray segmentsArray = dataObject.getJSONArray("segmentos");

            listBox.setVisible(true);
            listBox.getChildren().clear();

            for (int i = 0; i < segmentsArray.length(); i++) {
                JSONObject segmentObject = segmentsArray.getJSONObject(i);

                int id = segmentObject.getInt("id");
                String direction = segmentObject.getString("direcao");
                int distance = segmentObject.getInt("distancia");
                String obs = segmentObject.getString("obs");

                JSONObject origin = segmentObject.getJSONObject("ponto_origem");
                int originId = origin.getInt("id");
                String originName = origin.getString("name");
                String originObs = origin.getString("obs");
                Point pointOrigin = new Point(originId, originName, originObs);

                JSONObject destiny = segmentObject.getJSONObject("ponto_destino");
                int destinyId = origin.getInt("id");
                String destinyName = origin.getString("name");
                String destinyObs = origin.getString("obs");
                Point pointDestiny = new Point(destinyId, destinyName, destinyObs);

                // Create a Point object (you may need to create a Point class)
                Segment segment = new Segment(id, pointOrigin, pointDestiny, direction, distance, obs);

                // Add the point to the list
                segmentList.add(segment);

                // Display the point in the UI
                Text textSegment = new Text("Id: " + id + " - Direção: " + direction + " | Obs: " + obs + " PONTO ORIGEM:" + pointOrigin.toString()+ "==> PONTO DESTINO:" + pointDestiny.toString());
                listBox.getChildren().add(textSegment);
            }
        } catch (JSONException e) {
            System.out.println(e.toString());
        }
    }
    @FXML
    protected void onSaveNewSegmentClick() {
        findPoints();

        if (connected) {
            String direction = segmentDirection.getText();
            int distance = 0;
            int id_origin = -1;
            int id_destiny = -1;
            Point originPoint = null;
            Point destinyPoint = null;

            try {
                distance = Integer.parseInt(segmentDistance.getText());
            } catch (NumberFormatException e) {
                showWarning("A distância deve ser um número!");
                System.err.println("Input is not a valid number: " + segmentDistance.getText());
                return;
            }

            try {
                id_origin = Integer.parseInt(segmentOrigin.getText());
            } catch (NumberFormatException e) {
                showWarning("O id de origem deve ser um número!");
                System.err.println("Input is not a valid number: " + segmentOrigin.getText());
                return;
            }

            try {
                id_destiny = Integer.parseInt(segmentDestiny.getText());
            } catch (NumberFormatException e) {
                showWarning("O id de destino deve ser um número!");
                System.err.println("Input is not a valid number: " + segmentDestiny.getText());
                return;
            }

            for (Point point : pointList) {
                if (point.getId() == id_origin) {
                    originPoint = point;
                } else if (point.getId() == id_destiny) {
                    destinyPoint = point;
                }

                if (originPoint != null && destinyPoint != null) {
                    break;
                }
            }

            if (originPoint == null) {
                System.out.println("Ponto de origem não encontrado: " + id_origin);
                showWarning("Ponto de origem com id " + id_origin + " não encontrado!");
                return;
            }

            if (destinyPoint == null) {
                System.out.println("Ponto de destino não encontrado: " + id_destiny);
                showWarning("Ponto de destino com id " + id_destiny + " não encontrado!");
                return;
            }

            String obs = segmentObs.getText();

            JSONObject request = new JSONObject();
            JSONObject data = new JSONObject();
            JSONObject segment = new JSONObject();
            JSONObject origin = new JSONObject();
            JSONObject destiny = new JSONObject();

            try {
                request.put("action","cadastro-segmento");
                request.put("token",token);

                segment.put("direcao",direction);
                segment.put("distancia",distance);
                segment.put("obs",obs);

                origin.put("id",originPoint.getId());
                origin.put("name",originPoint.getName());
                origin.put("obs",originPoint.getObs());
                segment.put("ponto_origem",origin);

                destiny.put("id",destinyPoint.getId());
                destiny.put("name",destinyPoint.getName());
                destiny.put("obs",destinyPoint.getObs());
                segment.put("ponto_destino",destiny);

                data.put("segmento",segment);
                request.put("data",data);
            }
            catch(JSONException e) {
                System.out.println(e.toString());
            }

            System.out.println("C-->S: " + request.toString());
            sendMessageToServer(request.toString());

            String response = receiveMessageFromServer();
            if (response != null) {
                Platform.runLater(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        String action = jsonResponse.getString("action");
                        boolean error = jsonResponse.getBoolean("error");
                        String message = jsonResponse.getString("message");

                        if (!error && action.equals("cadastro-ponto")) {
                            clearNewPointFields();
                        } else {
                            showWarning(message);
                        }
                        System.out.println("S-->C: " + response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("Error parsing JSON response");
                    }
                });
            }
        } else {
            showWarning("Erro na conexão!");
        }
    }

    protected void findMyUser() {
        if (connected) {
            String myDataRequest = "{ \"action\": \"pedido-proprio-usuario\", \"data\": {  \"token\": \"" + token + "\"} }";

            System.out.println("C-->S: " + myDataRequest);
            sendMessageToServer(myDataRequest);

            String response = receiveMessageFromServer();
            if (response != null) {
                Platform.runLater(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONObject jsonData = jsonResponse.getJSONObject("data");

                        String action = jsonResponse.getString("action");
                        boolean error = jsonResponse.getBoolean("error");
                        String message = jsonResponse.getString("message");

                        if (!error && action.equals("pedido-proprio-usuario")) {
                            user = new User(
                                    Integer.parseInt(jsonData.getJSONObject("user").getString("id")),
                                    jsonData.getJSONObject("user").getString("name"),
                                    jsonData.getJSONObject("user").getString("email"),
                                    token
                            );
                            user.setAdmin(jsonData.getJSONObject("user").getString("type").equals("admin"));

                            change_id.setText(Integer.toString(user.getId()));
                            change_email.setText(user.getEmail());
                            change_name.setText(user.getName());
                        } else {
                            showWarning(message);
                        }
                        System.out.println("S-->C: " + response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("Error parsing JSON response");
                    }
                });
            }
        } else {
            showWarning("Erro na conexão!");
        }
    }
    @FXML
    protected void onMyDataClick() {
        if (connected) {
            String myDataRequest = "{ \"action\": \"pedido-proprio-usuario\", \"data\": {  \"token\": \"" + token + "\"} }";

            System.out.println("C-->S: " + myDataRequest);
            sendMessageToServer(myDataRequest);

            String response = receiveMessageFromServer();
            if (response != null) {
                Platform.runLater(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONObject jsonData = jsonResponse.getJSONObject("data");
                        
                        String action = jsonResponse.getString("action");
                        boolean error = jsonResponse.getBoolean("error");
                        String message = jsonResponse.getString("message");

                        if (!error && action.equals("pedido-proprio-usuario")) {
                            user = new User(
                                    Integer.parseInt(jsonData.getJSONObject("user").getString("id")),
                                    jsonData.getJSONObject("user").getString("name"),
                                    jsonData.getJSONObject("user").getString("email"),
                                    token
                                    );
                            user.setAdmin(jsonData.getJSONObject("user").getString("type").equals("admin"));
                            showUser(user);
                        } else {
                            showWarning(message);
                        }
                        System.out.println("S-->C: " + response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("Error parsing JSON response");
                    }
                });
            }
        } else {
            showWarning("Erro na conexão!");
        }
    }

    @FXML
    protected void onListUsersClick() {
        //System.out.println("A");
        String listRequest = "{ \"action\": \"listar-usuarios\", \"data\": {  \"token\": \"" + token + "\"} }";
        System.out.println("C-->S: " + listRequest);
        sendMessageToServer(listRequest);
        
        String response = receiveMessageFromServer();
        System.out.println("S-->C: " + response);
        
        try {
        	JSONObject jsonObject = new JSONObject(response);
        	JSONObject dataObject = jsonObject.getJSONObject("data");
        	JSONArray usersArray = dataObject.getJSONArray("users");
        		
        	listBox.setVisible(true);
        	listBox.getChildren().clear();
        	
		        for (int i = 0; i < usersArray.length(); i++) {
		        	JSONObject userObject = usersArray.getJSONObject(i);
		        	
		        	String id = userObject.getString("id");
		        	String name = userObject.getString("name");
		        	String type = userObject.getString("type");
		        	String email = userObject.getString("email");
		        	
		        	Text usuario = new Text("Id: " + id + " - Nome: " + name + " - " + type + " | E-mail: " + email);
		        	listBox.getChildren().add(usuario);		        	
		        }		       
	    	} catch(JSONException e) {
            System.out.println(e.toString());
        }
    }

    @FXML
    protected void onListPointsClick() {
        findPoints();
    }

    @FXML
    protected void onListSegmentsClick() {
        findSegments();
    }
        
    @FXML
    protected void onChangeClick() {
    	
    	String user_id = change_id.getText();
    	String name = change_name.getText();
    	String email = change_email.getText();
    	String password = change_password.getText();
    	String hashedPassword = hashPasswordMD5(password);
    	
    	if(isAdmin(token)) {
    		try {
    			String changeRequest = "{ \"action\": \"edicao-usuario\", \"data\": {  \"token\": \"" + token + "\", \"user_id\": \"" + user_id + "\", \"name\": \"" + name + "\", \"email\": \"" + email + "\", \"password\": \"" + hashedPassword + "\", \"type\": \"" + "admin" + "\" } }";    		
    			
        		sendMessageToServer(changeRequest);
        		String response = receiveMessageFromServer();
        		
        		JSONObject jsonResponse = new JSONObject(response);
        		boolean error = jsonResponse.getBoolean("error");
                String message = jsonResponse.getString("message");
        		
                System.out.println("------------------------------------------------------------------");
        		System.out.println("C-->S: " + changeRequest);
        		System.out.println("S--C: " + response);
    			
    		} catch (JSONException e){
    			
    		}
	
    	} else {
    		try {
    			//pedido para definir id
    			String DataRequest = "{ \"action\": \"pedido-proprio-usuario\", \"data\": {  \"token\": \"" + token + "\"} }";
    			sendMessageToServer(DataRequest);
    			System.out.println("C-->S: " + DataRequest);
    			String response = receiveMessageFromServer();
    			System.out.println("S-->C: " + response);
    			
    			JSONObject jsonresponse = new JSONObject(response);
    			JSONObject dataObject = jsonresponse.getJSONObject("data");
    			//System.out.println(dataObject);
    			JSONObject userObject = dataObject.getJSONObject("user");
    			String id = userObject.getString("id");
    			
    			String changeRequest = "{ \"action\": \"autoedicao-usuario\", \"data\": { \"token\": \"" + token + "\", \"id\": \"" + id + "\", \"name\": \"" + name + "\", \"email\": \"" + email + "\", \"password\": \"" + hashedPassword + "\"} }";
    			
    			sendMessageToServer(changeRequest);
    			System.out.println("C-->S: " + changeRequest);
    			response = receiveMessageFromServer();
    			System.out.println("S-->C: " + response);
    			
    		} catch (JSONException e) {
                e.printStackTrace();
                System.out.println("Error parsing JSON response");
            }
    	}
    	
    	
    	//System.out.println("Funcionou");
    }
   
    @FXML
    protected void onLogoutClick() {
        if (connected) {
            String logoutRequest = "{ \"action\": \"logout\", \"data\": { \"token\": \"" + token + "\" } }";
            System.out.println("------------------------------------------------------------------");
            System.out.println("C-->S: " + logoutRequest);
            sendMessageToServer(logoutRequest);

            String response = receiveMessageFromServer();
            if (response != null) {
                Platform.runLater(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        boolean error = jsonResponse.getBoolean("error");
                        String message = jsonResponse.getString("message");

                        if (!error) {
                            System.out.println("S-->C: " + response);

                            emailField.setText("");
                            passwordField.setText("");

                            if (clientSocket != null && !clientSocket.isClosed()) {
                                try {
                                    clientSocket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            listUsersButton.setVisible(false);
                            listUsersButton.setManaged(false);
                            goToConnection();
                        } else {
                            showWarning(message);
                            System.out.println("S-->C: " + response);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("Error parsing JSON response");
                    }
                });
            }
        } else {
            showWarning("Erro na conexão!");
        }
    }

    @FXML
    protected void onSelfDeleteClick() {
        if (connected) {
            TextInputDialog emailDialog = new TextInputDialog();
            emailDialog.setTitle("Para confirmar a exclusão digite seu e-mail");
            emailDialog.setHeaderText("Enter Email:");
            emailDialog.setContentText("Email:");
            Optional<String> emailResult = emailDialog.showAndWait();

            if (emailResult.isPresent()) {
                String email = emailResult.get();

                TextInputDialog passwordDialog = new TextInputDialog();
                passwordDialog.setTitle("Para confirmar a exclusão digite sua senha");
                passwordDialog.setHeaderText("Enter Password:");
                passwordDialog.setContentText("Password:");
                Optional<String> passwordResult = passwordDialog.showAndWait();

                if (passwordResult.isPresent()) {
                    String password = hashPasswordMD5(passwordResult.get());

                    String logoutRequest = "{ \"action\": \"excluir-proprio-usuario\", \"data\": { \"token\": \"" + token + "\", \"email\": \"" + email + "\", \"password\": \"" + password + "\" } }";
                    System.out.println("C-->S: " + logoutRequest);
                    sendMessageToServer(logoutRequest);

                    String response = receiveMessageFromServer();
                    if (response != null) {
                        Platform.runLater(() -> {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                boolean error = jsonResponse.getBoolean("error");
                                String message = jsonResponse.getString("message");

                                if (!error) {
                                    System.out.println("S-->C: " + response);

                                    emailField.setText("");
                                    passwordField.setText("");

                                    if (clientSocket != null && !clientSocket.isClosed()) {
                                        try {
                                            clientSocket.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    goToConnection();
                                } else {
                                    showWarning(message);
                                    System.out.println("S-->C: " + response);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("Error parsing JSON response");
                            }
                        });
                    }
                }
            }
        } else {
            showWarning("Conexão falhou");
        }
    }

    @FXML
    private void onDeleteUserClick() {
    	
    	int id = Integer.parseInt(deleteUserId.getText());
    	String deleteRequest = "{ \"action\": \"excluir-usuario\", \"data\": { \"token\": \"" + token + "\", \"user_id\": " + id + " } }";
    	
    	sendMessageToServer(deleteRequest);
    	System.out.println("C-->S: " + deleteRequest);
    	
    	String response = receiveMessageFromServer();
    	
    	try {
    		JSONObject jsonResponse = new JSONObject(response);
    		System.out.println("S-->C: " + jsonResponse.toString());
            if(jsonResponse.getBoolean("error")) {
                showWarning(jsonResponse.getString("message"));
            }
            else {
                showWarning("Usuário removido com sucesso");
            }
    	} catch(JSONException e) {
    		System.out.println(e);
    	}
    	
    }

    @FXML
    private void onDeletePointClick() {

        int id = Integer.parseInt(deletePointId.getText());
        String deleteRequest = "{ \"action\": \"excluir-ponto\", \"data\": { \"token\": \"" + token + "\", \"ponto_id\": " + id + "} }";

        sendMessageToServer(deleteRequest);
        System.out.println("C-->S: " + deleteRequest);

        String response = receiveMessageFromServer();

        try {
            JSONObject jsonResponse = new JSONObject(response);
            System.out.println("S-->C: " + jsonResponse.toString());
            if(jsonResponse.getBoolean("error")) {
                showWarning(jsonResponse.getString("message"));
            }
            else {
                showWarning("Ponto removido com sucesso");
            }
        } catch(JSONException e) {
            System.out.println(e);
        }

    }

    @FXML
    private void onDeleteSegmentClick() {

        int id = Integer.parseInt(deleteSegmentId.getText());
        String deleteRequest = "{ \"action\": \"excluir-segmento\", \"data\": { \"token\": \"" + token + "\", \"segmento_id\": \"" + id + "\"} }";

        sendMessageToServer(deleteRequest);
        System.out.println("C-->S: " + deleteRequest);

        String response = receiveMessageFromServer();

        try {
            JSONObject jsonResponse = new JSONObject(response);
            System.out.println("S-->C: " + jsonResponse.toString());
            if(jsonResponse.getBoolean("error")) {
                showWarning(jsonResponse.getString("message"));
            }
            else {
                showWarning("Segmento removido com sucesso");
            }
        } catch(JSONException e) {
            System.out.println(e);
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