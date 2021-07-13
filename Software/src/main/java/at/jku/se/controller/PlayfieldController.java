package at.jku.se.controller;

import at.jku.se.sudokumaster.AnchorPoint;
import at.jku.se.sudokumaster.SimpleBoard;
import at.jku.se.sudokumaster.SimpleSolver;
import at.jku.se.utility.NewScreenDropDown;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

/**
 * The type Playfield controller.
 */
public class PlayfieldController {
    /**
     * The Text fields.
     */
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
    private Button btnAddNumbers;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label lbClock;
    @FXML
    private Button btnStartGame;
    @FXML
    private GridPane colorList;
    @FXML
    private ListView colorListNew;
    @FXML
    private MenuItem miSave;

    /**
     * The Group counter. Used for Freeform Sudokus
     */
    int [] groupCount;
    /**
     * The Version.
     */
    String version ="";
    /**
     * The Generate type.(Auto, Manual)
     */
    String generateType="";
    /**
     * The Diffculty.
     */
    String diffculty ="";
    /**
     * The Field size.
     */
    int fieldSize;
    /**
     * The Init perc.
     */
    int initPerc = 0;
    /**
     * The Colorindex.
     */
    int colorindex = 0;
    /**
     * The File name.
     */
    String fileName="";
    /**
     * The Is new.
     */
    boolean isNew = true;
    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Sudoku Helper Object
     */
    SudokuHelper h = new SudokuHelper();
    /**
     * The Colors used for Freeform Sudokus.
     */
    ObservableList<String> colors = FXCollections.observableArrayList(
            "orange", "aquamarine", "bisque", "darkseagreen", "khaki",
            "lightgreen", "lightsalmon", "lightsteelblue", "tan"
    );
    /**
     * The Current color.
     */
    String currentColor = colors.get(0);
    /**
     * The Timer thread.
     */
    Thread timerThread;
    /**
     * The Time.
     */
    String time ="";
    /**
     * The Loadtime.
     */
    long loadtime = 0;
    /**
     * The Longtimer.
     */
    long longtimer = 0;

    /**
     * Initialize the playfield and set values retrieved from the user Interface.
     */
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


    }

    /**
     * Inizialize all default Textfields.
     */
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

    /**
     * The type Color cell used for the creation of the freefrom Sudoku
     */
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

    /**
     * Initialize the freeform Board.
     *  1. Fill Listview with ColorCells. (ColorCells show the Color and current click Counter for each color)
     *  2. Add Textfields to Gridpane and add onClick Listener to each TextField
     *  3. If a Textfield is Clicked the current Color is applied to the textfield Background and the board sets the group ID corresponding to each color index.
     *     If the colored Textfield is clicked again with the same color the color will be removed.
     *     If the colored Textfield is clicked again with another color the color will be overwritten and the old color counter will be counted down.
     *  4. If all Textfields are colored a button will be visible and allowes the player to start entering numbers.
     *     After clicking the button the listview will be hidden and the current background colors will be locked.
     */
    private void initFreiformField() {
            groupCount = new int[9];
            textFields = new TextField[fieldSize][fieldSize];
            colorListNew.setItems(colors);
            colorListNew.setCellFactory(new Callback<ListView, ListCell>() {
                @Override
                public ListCell call(ListView listView) {
                    return new ColorCell();
                }
            });
            if (!colorListNew.getItems().isEmpty()) {
                colorListNew.getSelectionModel().select(0);
            }
            colorListNew.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    currentColor = newValue;
                    colorindex = colors.indexOf(currentColor);
                }
            });
            for (int c = 0; c < fieldSize; c++) {
                for (int r = 0; r < fieldSize; r++) {
                    TextField t = h.defaultTextField();
                    t.setOnMouseClicked(e -> {
                        if (t.getStyle().contains("-fx-background-color:")) {
                            String[] styles = t.getStyle().split(";");
                            for (int i = 0; i < styles.length; i++) {
                                if (styles[i].contains("-fx-background-color:")) {
                                    String color = styles[i].split(":")[1];
                                    if (color.equals(currentColor)) {
                                        groupCount[colorindex]--;
                                        styles[i] = "";
                                        t.setStyle(String.join(";", styles));
                                        t.setId(colorindex + "");
                                    } else if (groupCount[colorindex] < 9) {
                                        int oldColor = colors.indexOf(color);
                                        groupCount[oldColor]--;
                                        groupCount[colorindex]++;
                                        styles[i] = "-fx-background-color:" + currentColor;
                                        t.setStyle(String.join(";", styles));
                                        t.setId(colorindex + "");
                                    }
                                }
                            }
                        } else if (groupCount[colorindex] < 9) {
                            t.setStyle(t.getStyle() + " -fx-background-color:" + currentColor);
                            t.setId(colorindex + "");
                            groupCount[colorindex]++;
                        }
                        int cnt = 0;
                        for (int i = 0; i < groupCount.length; i++) {
                            cnt = cnt + groupCount[i];
                        }
                        if (cnt == fieldSize * fieldSize) {
                            btnAddNumbers.setVisible(true);
                            btnAddNumbers.setDisable(false);
                            btnAddNumbers.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    colorListNew.setVisible(false);
                                    colorListNew.setDisable(true);
                                    btnAddNumbers.setOnAction(null);
                                    btnAddNumbers.setDisable(true);
                                    btnAddNumbers.setVisible(false);
                                    for (int c = 0; c < fieldSize; c++) {
                                        for (int r = 0; r < fieldSize; r++) {
                                            TextField t = textFields[c][r];
                                            textFields[c][r].setEditable(true);
                                            textFields[c][r].setOnMouseClicked(null);
                                        }
                                    }
                                }
                            });
                        } else {
                            btnAddNumbers.setVisible(false);
                            btnAddNumbers.setDisable(true);
                        }
                        colorListNew.setCellFactory(new Callback<ListView, ListCell>() {
                            @Override
                            public ListCell call(ListView listView) {
                                return new ColorCell();
                            }
                        });
                    });
                    t.setEditable(false);
                    textFields[c][r] = t;
                    h.hideUnusedBoxes(c, r, t);
                    playfield.add(t, c, r);
                }
            }
            textFields = initFields(textFields, initPerc);
        if(generateType.equals("automatisch")) {
            File file = new File(Paths.get("./freiform").toString());
            File[] files = file.listFiles();
            int random = new Random().nextInt(files.length);
            String row = "";
            JSONParser jsonparser = new JSONParser();
            String[] splitRow;
            try {
                Object obj =   jsonparser.parse(new FileReader(files[random]));
                JSONObject gameinfos = (JSONObject) obj;
                for(int i = 0; i<fieldSize;i++){

                    row = (String)gameinfos.get(""+i);
                    System.out.println(row);
                    splitRow =row.split(";");
                    for(int j = 0; j<splitRow.length;j++){
                            currentColor = colors.get(Integer.parseInt(splitRow[j]));
                            textFields[j][i].setStyle("-fx-background-color: " + currentColor+";");
                            textFields[j][i].setId(splitRow[j]);
                        }
                    }
                startTimer();
            } catch (IOException| ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Init the first numbers simple board to create a random Game.
     * Add five Random Numbers and check if the board is solvable.
     *
     * @param initField the init field
     * @return the simple board
     */
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
                initField[randC][randR].setEditable(false);
                setStyle(randC,randR,initField[randC][randR],"-fx-text-inner-color: darkblue;");
            }else {
                initField[randC][randR] = new TextField();
                initField[randC][randR].setText("");
                initField[randC][randR] = h.defaultTextField();
                i--;
            }

        }

        return h.getBoardSolution(initField);
    }

    /**
     * Sets style.
     *
     * @param c     the column
     * @param r     the row
     * @param t     the textfield
     * @param style the style
     */
    public void setStyle(int c, int r, TextField t,String style) {
        if (!version.equals(BTN_FREIFORM)) {
            h.setNormalBoxesStyle(c, r, t,style);
        }
    }

    /**
     * If the board is solvable add the percentage of numbers set by initPerc add them to the current board.
     *
     * @param textFields
     * @param initPerc
     * @return
     */
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
                    textFields[randC+anchorC][randR+anchorR].setEditable(false);
                    setStyle(randC+anchorC,randR+anchorR,textFields[randC+anchorC][randR+anchorR],"-fx-text-inner-color: darkblue;");
                    value--;
                }
            }
        }
        return textFields;
    }

    /**
     * Handle button solve.
     *
     * @param event the event
     */
    @FXML
    public void handleButtonSolve(ActionEvent event){
        h.solveBoard(textFields);
        stopTimer();
    }

    /**
     * Handle button start game.
     *
     * @param event the event
     */
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
    }

    /**
     * Handle button hint.
     *
     * @param event the event
     */
    public void handleButtonHint(ActionEvent event){
        h.getHint(textFields);
    }

    /**
     * Handle button backto main.
     *
     * @param event the event
     * @throws IOException the io exception
     */
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


    /**
     * Handle button save game.
     *
     * @param event the event
     * @throws AWTException the awt exception
     * @throws IOException  the io exception
     */
    @FXML
    public void handleButtonSaveGame(ActionEvent event) throws AWTException, IOException {

        JSONObject saveGame = new JSONObject();
        JSONObject newFreiform = new JSONObject();
        String file ="";
        String playerName="";
        Pair<String,String> dialogResult = SaveDialog();
        /*TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save Game");
        dialog.setContentText("Filename: ");

        Optional<String> fileName = dialog.showAndWait();
        if(fileName.isPresent()){

            file = fileName.get();
        }*/
        file = dialogResult.getKey();
        playerName = dialogResult.getValue();
        String row="";
        SimpleBoard board = h.getCurrentBoard(textFields);
        int groupID = 0;
        System.out.println(generateType);
        System.out.println(version);
        System.out.println(isNew);
        if(generateType.equals("manuell") && version.equals(BTN_FREIFORM) && isNew) {
            System.out.println("test");
            for (int rows = 0; rows < fieldSize; rows++) {
                row = "";
                for (int col = 0; col < fieldSize; col++) {
                    System.out.println(""+board.get(col, rows).getGroupId());

                    row += textFields[col][rows].getId() +";";

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
        if(isNew){
            loadtime = longtimer*-1;
            isNew = false;
            stopTimer();
        }
        saveGame.put("FileName", file);
        saveGame.put("version", version);
        saveGame.put("generateType", generateType);
        saveGame.put("difficulty",diffculty);
        saveGame.put("player", playerName);
        saveGame.put("time","" + (longtimer*-1));
        startTimer();

        for (int r = 0; r<fieldSize;r++){
            row="";
            for (int c=0;c<fieldSize;c++) {
                if(!version.equals(BTN_FREIFORM)){
                    try{
                        Integer i = Integer.valueOf(textFields[c][r].getText());
                        row+= i.toString() + ";";
                    }catch (NumberFormatException e){
                        row+=  " ;";
                    }
                }else{
                    try{
                        Integer i = Integer.valueOf(textFields[c][r].getText());
                        row+= i.toString()+ "," + textFields[c][r].getId() + ";";
                    }catch (NumberFormatException e){
                        row+= " ," + textFields[c][r].getId() + ";";

                    }
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

    /**
     * Load game infos.
     */
    public void LoadGameInfos(){
        colorListNew.setVisible(false);
        miSave.setDisable(false);
        btnStartGame.setVisible(false);
        String row = "";
        JSONParser jsonparser = new JSONParser();
        String[] splitRow;
        String[] splitCell;

        try {
            Object obj =   jsonparser.parse(new FileReader("./savegames/JSON/"+fileName+".json"));
            JSONObject gameinfos = (JSONObject) obj;
             loadtime = Long.parseLong((String)gameinfos.get("time"));
            version = (String) gameinfos.get("version");
            for(int i = 0; i<fieldSize;i++){

                row = (String)gameinfos.get(""+i);
                splitRow =row.split(";");
                for(int j = 0; j<splitRow.length;j++){
                    splitCell = splitRow[j].split(",");
                    if (splitCell[0].equals(" ")){
                        textFields[j][i].setEditable(true);
                    }
                    textFields[j][i].setText(splitCell[0]);
                    if(version.equals(BTN_FREIFORM)){
                        currentColor = colors.get(Integer.parseInt(splitCell[1]));
                        textFields[j][i].setStyle("-fx-background-color: " + currentColor+";");
                        textFields[j][i].setId(splitCell[1]);


                    }
                }
            }

            startTimer();


        } catch (IOException| ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * Start timer.
     */
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

    /**
     * Stop timer.
     */
    public void stopTimer(){
        timerThread.stop();
    }

    private Pair<String,String> SaveDialog(){
        Dialog<Pair<String,String>> dialog = new Dialog<>();
        dialog.setTitle("Save Game");

        dialog.setResizable(false);

        Label lbFileName = new Label("Filename: ");
        Label leer = new Label("");
        Label lbPlayerName = new Label("Player: ");
        TextField tfFileName = new TextField();
        TextField tfPlayerName = new TextField();

        GridPane grid = new GridPane();
        grid.add(lbFileName, 1, 1);
        grid.add(tfFileName, 2, 1);
        grid.add(lbPlayerName, 1, 4);
        grid.add(tfPlayerName, 2, 4);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeOk) {
                return new Pair<>(tfFileName.getText(), tfPlayerName.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        if (result.isPresent()) {

          return result.get();
        }
        return null;
    }

    /**
     * Init data.
     *
     * @param version      the version
     * @param generateType the generate type
     * @param diffculty    the diffculty
     */
    public void initData(String version, String generateType, String diffculty) {
        this.generateType = generateType;
        this.diffculty = diffculty;
        this.version = version;
        initializePlayfield();
    }

    /**
     * Load init data.
     *
     * @param version      the version
     * @param generateType the generate type
     * @param diffculty    the diffculty
     * @param fileName     the file name
     */
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
