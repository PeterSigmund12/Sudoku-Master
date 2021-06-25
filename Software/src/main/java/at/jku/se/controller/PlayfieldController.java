package at.jku.se.controller;

import at.jku.se.sudokumaster.AnchorPoint;
import at.jku.se.sudokumaster.SimpleBoard;
import at.jku.se.sudokumaster.SimpleSolver;
import at.jku.se.utility.NewScreen;
import at.jku.se.utility.NewScreenDropDown;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.simple.JSONObject;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.platform.commons.util.StringUtils;

public class PlayfieldController {
    TextField[][]textFields;
    private static final String BTN_REGULAR = "rbSaRegulaer";
    private static final String BTN_SAMURAI = "rbSaSamurai";
    private static final String BTN_FREIFORM = "rbSaFreiform";
    private static final String BTN_EASY = "leicht";
    private static final String BTN_MEDIUM = "mittel";
    private static final String BTN_HARD = "schwer";

    @FXML
    private AnchorPane root;

    @FXML
    private GridPane playfield;
    @FXML
    private SplitPane splitPane;
    @FXML
    private Button btnSolve;

    @FXML
    private MenuBar menuBar;
    @FXML
    private Label lbClock;
    @FXML
    private Button btnHint, btnStartGame;
    @FXML
    private GridPane colorList;
    @FXML
    private ListView colorListNew;
    @FXML
    private MenuItem miSave;

    int [] groupCount;
    //anchorpoints 0/0 , 12/0 , 0/12 , 6/6 , 12/12
    String version ="";
    String generateType="";
    String diffculty ="";
    int fieldSize;
    int initPerc = 0;
    int colorindex = 0;
    String fileName="";
    boolean isNew = true;
    Stage stage;
    SudokuHelper h = new SudokuHelper();
    ObservableList<String> colors = FXCollections.observableArrayList(
            "orange", "aquamarine", "bisque", "darkseagreen", "khaki",
            "lightgreen", "lightsalmon", "lightsteelblue", "tan"
    );
    String currentColor = colors.get(0);
    Thread timerThread;
    String time ="";
    long loadtime = 0;
    long longtimer = 0;

    public void initializePlayfield() {
        playfield.setGridLinesVisible(false);
        playfield.setAlignment(Pos.CENTER);
        playfield.setHgap(5);
        playfield.setVgap(5);
        if (generateType.equals("automatisch") && isNew){
            switch (diffculty){
                case BTN_EASY:
                    initPerc = 40;
                    break;
                case BTN_MEDIUM:
                    initPerc = 25;
                    break;
                case BTN_HARD:
                    initPerc = 15;
                    break;
            }
        }
        switch (version){
            case BTN_REGULAR:
                fieldSize=9;
                initField();
                break;
            case BTN_SAMURAI:
                fieldSize=21;
                initField();
                break;
            case BTN_FREIFORM:
                fieldSize=9;
                initFreiformField();
                break;
            default:
                System.err.println("No valid Fieldtype selected");
                break;
        }

        if(isNew && !generateType.equals("manuell")){
            startTimer();
            btnStartGame.setVisible(false);

        }else{
            miSave.setDisable(true);
        }



        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(version.equals(BTN_REGULAR) || version.equals(BTN_FREIFORM)){
                    for (int row = 0; row < fieldSize; row++){
                        for (int col = 0; col < fieldSize; col++){
                            textFields[row][col].setPrefWidth(playfield.getWidth()/fieldSize);
                            textFields[row][col].setFont(Font.font((playfield.getHeight()/fieldSize)/4));
                        }
                    }
                }
            }
        });

        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                    for (int row = 0; row < fieldSize; row++){
                        for (int col = 0; col < fieldSize; col++){
                            textFields[row][col].setPrefHeight(playfield.getHeight()/fieldSize);
                            textFields[row][col].setFont(Font.font((playfield.getHeight()/fieldSize)/4));
                        }
                    }


            }
        });

       /* splitPane.getDividers().get(0).positionProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                for (int row = 0; row < fieldSize; row++){
                    for (int col = 0; col < fieldSize; col++){
                        textFields[row][col].setPrefWidth(playfield.getWidth()/fieldSize);
                    }
                }
            }
        });*/
    }

    private void initField() {
        textFields=new TextField[fieldSize][fieldSize];
        for (int c=0; c<fieldSize;c++){
            for (int r=0; r<fieldSize;r++){
                TextField t = h.defaultTextField();
                textFields[c][r] = t;
                setStyle(c,r,t,"");
                h.hideUnusedBoxes(c, r, t);
                playfield.add(t,c,r);

            }
        }
        textFields=initFields(textFields,initPerc);
    }
    class  ColorCell extends ListCell<String>{
        @Override
        protected void updateItem(String item, boolean b) {
            super.updateItem(item, b);


            HBox stack = new HBox();
            stack.setAlignment(Pos.CENTER_LEFT);
            Circle circle = new Circle(20,20,20);
            if (item!=null){
                Text text = new Text("\t"+"Color  "+ item.substring(0, 1).toUpperCase() + item.substring(1)+" " + groupCount[colors.indexOf(item)] + " / 9");
                circle.setFill(Color.web(item));
                stack.getChildren().addAll(circle,text);
                setGraphic(stack);
            }
        }
    }
    private void initFreiformField() {
        groupCount = new int[9];
        textFields=new TextField[fieldSize][fieldSize];
        colorListNew.setItems(colors);
        colorListNew.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView listView) {
                return new ColorCell();
            }
        });
        if (!colorListNew.getItems().isEmpty()){
            colorListNew.getSelectionModel().select(0);
        }
        colorListNew.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("ListView selection changed from oldValue = "
                        + oldValue + " to newValue = " + newValue);
                currentColor = newValue;
                colorindex = colors.indexOf(currentColor);
            }
        });
        for (int c=0; c<fieldSize;c++){
            for (int r=0; r<fieldSize;r++){
                TextField t = h.defaultTextField();
                t.setOnMouseClicked(e -> {
                    if (t.getStyle().contains("-fx-background-color:")){
                        String [] styles = t.getStyle().split(";");
                        for (int i =0; i<styles.length;i++){
                            if (styles[i].contains("-fx-background-color:")){
                                String color = styles[i].split(":")[1];
                                if (color.equals(currentColor)){
                                    groupCount[colorindex]--;
                                    styles[i]="";
                                    t.setStyle(String.join(";",styles));
                                    t.setId(colorindex+"");
                                }else if (groupCount[colorindex] <9) {
                                    int oldColor = colors.indexOf(color);
                                    groupCount[oldColor]--;
                                    groupCount[colorindex]++;
                                    styles[i]="-fx-background-color:"+currentColor;
                                    t.setStyle(String.join(";",styles));
                                    t.setId(colorindex+"");
                                }
                            }
                        }
                    }else if (groupCount[colorindex] <9){
                        t.setStyle(t.getStyle()+" -fx-background-color:"+currentColor);
                        t.setId(colorindex+"");
                        groupCount[colorindex]++;
                        System.out.println(groupCount[colorindex]);
                    }
                    System.out.println("Clicked Row: " +GridPane.getRowIndex(t)+ " Column: "+ GridPane.getColumnIndex(t) + t.getId());
                    colorListNew.setCellFactory(new Callback<ListView, ListCell>() {
                        @Override
                        public ListCell call(ListView listView) {
                            return new ColorCell();
                        }
                    });
                });
                t.setEditable(false);
                textFields[c][r] = t;
                //t.setDisable(true);

                //setStyle(c,r,t,"");
                h.hideUnusedBoxes(c, r, t);
                playfield.add(t,c,r);
            }
        }
        textFields=initFields(textFields,initPerc);
    }
    public SimpleBoard initFirstNumbers(TextField[][] initField) {
        SimpleSolver s = new SimpleSolver(initField.length);
        AnchorPoint[] points = s.getAnchorpoints();
        for (int i = 0; i<5;i++){
            AnchorPoint point = points[new Random().nextInt(points.length)];
            int randC = new Random().nextInt(9)+point.getCol();
            int randR = new Random().nextInt(9)+point.getRow();
            int randNum = new Random().nextInt(9);
            initField[randC][randR].setText(randNum+"");
            if(h.getBoardSolution(initField)!=null){
                initField[randC][randR].setDisable(true);
                setStyle(randC,randR,initField[randC][randR],"-fx-text-inner-color: darkblue;");
            }else {
                initField[randC][randR] = new TextField();
                i--;
            }

        }

        return h.getBoardSolution(initField);
    }
    public void setStyle(int c, int r, TextField t,String style) {
        if (!version.equals(BTN_FREIFORM)) {
            h.setNormalBoxesStyle(c, r, t,style);
        }else {
            System.out.println("Freiform");
            //TODO: Freiform Styling
        }
    }

    private TextField[][] initFields(TextField[][] textFields, int initPerc) {
        if (initPerc==0)return textFields;
        int anchorC = 0;
        int anchorR = 0;
        double size = textFields.length * textFields.length;
        size=(size*(initPerc+new Random().nextInt(9)))/100;
        int value = Math.toIntExact(Math.round(size));
        SimpleBoard solution = initFirstNumbers(textFields);
        SimpleSolver s = new SimpleSolver(textFields.length);
        AnchorPoint[] points = s.getAnchorpoints();
        SimpleBoard board = h.getCurrentBoard(textFields);
        if (solution != null && !s.validAndFull(board)){
            while (value > 0){
                AnchorPoint point = points[new Random().nextInt(points.length)];
                anchorC = point.getCol();
                anchorR = point.getRow();
                int randC = new Random().nextInt(9);
                int randR = new Random().nextInt(9);
                if (textFields[randC+anchorC][randR +anchorR].getText().trim().equals("")){
                    textFields[randC+anchorC][randR+anchorR].setText(""+solution.get(randC+anchorC,randR+anchorR).getValue());
                    textFields[randC+anchorC][randR+anchorR].setDisable(true);
                    setStyle(randC+anchorC,randR+anchorR,textFields[randC+anchorC][randR+anchorR],"-fx-text-inner-color: darkblue;");
                    value--;
                }
            }
        }
        return textFields;
    }

    @FXML
    public void handleButtonSolve(ActionEvent event){
        switch (version) {
            case BTN_REGULAR: case BTN_SAMURAI: case BTN_FREIFORM:
                h.solveBoard(textFields);
                break;
            default:
                break;
        }
        //btnSolve.setDisable(true);
    }

    @FXML
    public void handleButtonStartGame(ActionEvent event){

        startTimer();
        miSave.setDisable(false);
        btnStartGame.setVisible(false);
        if(h.getBoardSolution(textFields)!=null){
            for (int row = 0; row < fieldSize; row++){
                for (int col = 0; col < fieldSize; col++){
                    if(!textFields[col][row].getText().equals("")) {
                        textFields[col][row].setDisable(true);
                        setStyle(col, row, textFields[col][row], "-fx-text-inner-color: darkblue;");
                    }
                }
            }
        }
        //TODO: Move if Freeform Numbers entered
        if (version.equals(BTN_FREIFORM)){
            for (int c=0; c<fieldSize;c++){
                for (int r=0; r<fieldSize;r++){
                    TextField t = textFields[c][r];
                    textFields[c][r].setEditable(true);
                    textFields[c][r].setOnMouseClicked(null);
                    textFields[c][r].setOnMouseClicked(e -> {
                        System.out.println(t.getId());
                    });
                }
            }
        }
    }
    public void handleButtonHint(ActionEvent event){
        switch (version) {
            case BTN_REGULAR: case BTN_SAMURAI:
                h.getHint(textFields);
                break;
                //TODO: Choose Random Playfield, but check if solvable
            default:
                break;
        }
    }
    @FXML
    public void handleButtonBacktoMain(ActionEvent event) throws IOException {

        stopTimer();
        Stage oldStage = (Stage)menuBar.getScene().getWindow();
        NewScreenDropDown.handleButtonBacktoMain(event, "/fxml/mainmenue.fxml", oldStage);

        /*
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(NewScreen.class.getResource("/fxml/mainmenue.fxml"));
        Parent root2 = null;
        try {
            root2 = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
        }
        Stage stage = new Stage();
        stage.setTitle("Load game");
        stage.setScene(new Scene(root2));

        stage.show();
        oldStage.close();

         */
    }


    @FXML
    public void handleButtonSaveGame(ActionEvent event) throws AWTException, IOException {

        if(isNew){
            loadtime = longtimer*-1;
            isNew = false;
            stopTimer();
        }
        JSONObject saveGame = new JSONObject();
        JSONObject newFreiform = new JSONObject();
        String file ="";
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save Game");
        dialog.setContentText("Filename: ");

        Optional<String> fileName = dialog.showAndWait();
        if(fileName.isPresent()){

            file = fileName.get();
        }
        String row="";
        SimpleBoard board = h.getCurrentBoard(textFields);
        int groupID = 0;
        System.out.println(generateType);
        if( generateType.equals("manuell") && version.equals(BTN_FREIFORM)) {
            System.out.println("testen");
            for (int rows = 0; rows < fieldSize; rows++) {
                row = "";
                for (int col = 0; col < fieldSize; col++) {
                    System.out.println(""+board.get(col, rows).getGroupId());
                    if(board.get(col, rows).getGroupId() != null) {
                        groupID = board.get(col, rows).getGroupId();

                    }
                    else{
                        groupID = 0;
                    }
                    row += groupID + ";";
                }
                newFreiform.put("" + rows, row);
            }

            Random random = new Random();
            try(
            FileWriter saveFreiform = new FileWriter("freiform/Freiformtyp" + random.nextInt(100) + ".json");
            ){
                saveFreiform.write(newFreiform.toJSONString());
            }

        }
        saveGame.put("FileName", file);
        saveGame.put("version", version);
        saveGame.put("generateType", generateType);
        saveGame.put("difficulty",diffculty);
        saveGame.put("time","" + (longtimer*-1));
        startTimer();

        for (int r = 0; r<fieldSize;r++){
            row="";
            for (int c=0;c<fieldSize;c++) {
                try{
                    Integer i = Integer.valueOf(textFields[c][r].getText());
                    row+= i.toString() + ";";
                }catch (NumberFormatException e){
                    row+=  " ;";
                }
            }
            saveGame.put(""+r,row);
        }


        try(
                FileWriter saveFile=new FileWriter("savegames/JSON/"+file+".json")
        ) {
            saveFile.write(saveGame.toJSONString());

            Thread.sleep(500);
            //BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(215,175,425,400));
            ImageIO.write(image, "png", new File("savegames/img/"+file+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }

    public void LoadGameInfos(){
        String row = "";
        JSONParser jsonparser = new JSONParser();
        String[] splitRow;
        try {
            Object obj =   jsonparser.parse(new FileReader("./savegames/JSON/"+fileName+".json"));
            JSONObject gameinfos = (JSONObject) obj;
             loadtime = Long.parseLong((String)gameinfos.get("time"));

            for(int i = 0; i<fieldSize;i++){

                row = (String)gameinfos.get(""+i);
                splitRow =row.split(";");
                for(int j = 0; j<splitRow.length;j++){
                    textFields[j][i].setText(splitRow[j]);
                }
            }

            startTimer();


        } catch (IOException| ParseException e) {
            e.printStackTrace();
        }

    }

    public void startTimer(){

        final Date timerStart = new Date();
        final Date timertesten = new Date(loadtime);
        //System.out.println(timertesten);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if(isNew) {
            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
        timerThread = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            time = simpleDateFormat.format(timertesten.getTime());
            while (true) {
                try {
                    Thread.sleep(1000); //1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                 longtimer = new Date().getTime() - cal.getTime().getTime()-timertesten.getTime();
                time = simpleDateFormat.format(longtimer);
                //System.out.println(new Date());
                Platform.runLater(() -> {
                    lbClock.setText(time);
                });
            }
        });
        timerThread.start();//start the thread and its ok
    }
    public void stopTimer(){
        timerThread.stop();
    }


    public void initData(String version, String generateType, String diffculty) {
        this.generateType = generateType;
        this.diffculty = diffculty;
        this.version = version;
        initializePlayfield();
    }

    public void loadInitData(String version, String generateType, String diffculty, String fileName) {
        this.generateType = generateType;
        this.diffculty = diffculty;
        this.version = version;
        this.fileName = fileName;
        this.isNew = false;
        initializePlayfield();
        LoadGameInfos();
    }



}
