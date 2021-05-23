package at.jku.se.controller;

import at.jku.se.sudokumaster.AnchorPoint;
import at.jku.se.sudokumaster.SimpleBoard;
import at.jku.se.sudokumaster.SimpleSolver;
import at.jku.se.utility.NewScreen;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import org.json.simple.JSONObject;
import javax.imageio.ImageIO;
import java.awt.*;
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

public class PlayfieldController {
    TextField[][]textFields;
    private static final String BTN_REGULAR = "rbSaRegulaer";
    private static final String BTN_SAMURAI = "rbSaSamurai";
    private static final String BTN_FREIFORM = "rbSaFreiform";
    private static final String BTN_EASY = "leicht";
    private static final String BTN_MEDIUM = "mittel";
    private static final String BTN_HARD = "schwer";
    private Set<Color> colors;

    @FXML
    private AnchorPane root;

    @FXML
    private GridPane playfield;
    @FXML
    private Button btnSolve;

    @FXML
    private Label lbClock;
    private Button btnHint;
    @FXML
    private GridPane colorList;
    @FXML
    private ListView colorListNew;

    //anchorpoints 0/0 , 12/0 , 0/12 , 6/6 , 12/12
    String version ="";
    String generateType="";
    String diffculty ="";
    int fieldSize;
    int initPerc = 0;
    String fileName="";
    boolean isNew = true;
    SudokuHelper h = new SudokuHelper();
    ObservableList<String> data= FXCollections.observableArrayList(
            "chocolate", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown"
    );
    Thread timerThread;
    String time ="";
    long loadtime = 0;
    long longtimer = 0;

    public void initializePlayfield() {
        colors = new HashSet<>();
        colors.add(Color.AQUA);
        colors.add(Color.AZURE);
        colors.add(Color.LIME);
        colors.add(Color.OLIVE);
        colors.add(Color.PURPLE);
        colors.add(Color.DARKGRAY);
        colors.add(Color.HONEYDEW);
        colors.add(Color.TOMATO);
        colors.add(Color.LAVENDER);
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

        if(isNew){
            startTimer();
        }

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
    private void initFreiformField() {
        textFields=new TextField[fieldSize][fieldSize];
        Set<Color>col = new HashSet<>(colors);
        colorListNew.setItems(data);
        for (int c=0; c<fieldSize;c++){
            //Label l = new Label();
            Color cl = col.iterator().next();
            Circle circle = new Circle(20,20,20);
            circle.setFill(cl);
            //l.setText(""+c);
            //l.setStyle("-fx-text-inner-color:"+cl+" ;");
            col.remove(cl);
            //colorListNew.getItems().add(new ListCell<>(c,circle));
            colorList.add(circle,0,c);
            for (int r=0; r<fieldSize;r++){
                TextField t = h.defaultTextField();
                textFields[c][r] = t;
                t.setDisable(true);
                //setStyle(c,r,t,"");
                h.hideUnusedBoxes(c, r, t);
                playfield.add(t,c,r);
            }
        }
        /*
        colorList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("ListView selection changed from oldValue = "
                        + oldValue + " to newValue = " + newValue);
            }
        });

         */
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



    private void initFreiformField(int initPerc) {
        textFields=new TextField[9][9];
        int[][] x =createGroups();
        for (int c=0; c<9;c++){
            for (int r=0; r<9;r++){
                TextField t = h.defaultTextField();
                textFields[c][r] = t;
                playfield.add(t,c,r);
            }
        }
        textFields=initFields(textFields,initPerc);
    }
    private int[][] createGroups() {
        int[][]groups=new int[9][9];
        int x = 0;
        int y = 0;
        int cnt = 0;
        int num = 1;
        while (true) {
            List<String> list = new ArrayList();
            groups[y][x]=num;
            try {
                if (y > 0 && groups[y - 1][x] == 0) {
                    list.add("topFree");
                }
                if (y < 9 && groups[y + 1][x] == 0) {
                    list.add("botFree");
                }
                if (x > 0 && groups[y][x - 1] == 0) {
                    list.add("leftFree");
                }
                if (x < 9 && groups[y][x + 1] == 0) {
                    list.add("rightFree");
                }
                Random rand = new Random();
                String pick = list.get(rand.nextInt(list.size()));
                if (pick == "topFree")y=y-1;
                if (pick == "botFree")y=y+1;
                if (pick == "leftFree")x=x-1;
                if (pick == "rightFree")x=x+1;
                cnt++;
                //if (cnt==9)break;
                if (cnt==9){
                    num++;
                    cnt=0;
                }
                groups[y][x] = num;
                System.out.println(list.toString() + "\t " + pick + "\t " + num + "\ty:"+y+"\tx:"+x);
            } catch (IndexOutOfBoundsException e) {
            } catch (IllegalArgumentException e) {
            }
            if (cnt==9 && num == 1){
                break;
            }
        }
        //groups[y][x] = x+1;
        return groups;
    }
    @FXML
    public void handleButtonSolve(ActionEvent event){
        switch (version) {
            case BTN_REGULAR: case BTN_SAMURAI:
                h.solveBoard(textFields);
                break;
            default:
                break;
        }
        btnSolve.setDisable(true);
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
        if(isNew){
            stopTimer();
        }
        NewScreen.openNewScreen(event,"/fxml/mainmenue.fxml");
    }


    @FXML
    public void handleButtonSaveGame(ActionEvent event) throws AWTException, IOException {

        if(isNew){
            loadtime = longtimer*-1;
            isNew = false;
            stopTimer();
        }
        JSONObject saveGame = new JSONObject();
        String file ="";
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save Game");
        dialog.setContentText("Filename: ");

        Optional<String> fileName = dialog.showAndWait();
        if(fileName.isPresent()){

            file = fileName.get();
        }
        saveGame.put("FileName", file);
        saveGame.put("version", version);
        saveGame.put("generateType", generateType);
        saveGame.put("difficulty",diffculty);
        saveGame.put("time","" + (longtimer*-1));
        startTimer();
        String row="";
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
        System.out.println(timertesten);

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
                System.out.println(new Date());
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
