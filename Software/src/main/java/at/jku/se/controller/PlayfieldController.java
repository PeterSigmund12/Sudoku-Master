package at.jku.se.controller;

import at.jku.se.sudokumaster.SimpleBoard;
import at.jku.se.sudokumaster.SimpleSolver;
import at.jku.se.utility.NewScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import org.json.simple.JSONObject;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
    private Button btnSolve;
    @FXML
    private Button btnHint;

    //anchorpoints 0/0 , 12/0 , 0/12 , 6/6 , 12/12
    String version ="";
    String generateType="";
    String diffculty ="";
    SudokuHelper h = new SudokuHelper();

    public void initializePlayfield() {
        playfield.setGridLinesVisible(false);
        playfield.setAlignment(Pos.CENTER);
        playfield.setHgap(5);
        playfield.setVgap(5);
        int initPerc = 0;
        if (generateType.equals("automatisch")){
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
                initRegularField(initPerc);
                break;
            case BTN_SAMURAI:
                initSamuraiField(initPerc);
                break;
            case BTN_FREIFORM:
                initFreiformField(initPerc);
                break;
            default:
                System.err.println("No valid Fieldtype selected");
                break;
        }

    }
    private void initRegularField( int initPerc) {
        textFields=new TextField[9][9];
        for (int c=0; c<9;c++){
            for (int r=0; r<9;r++){
                TextField t = h.defaultTextField();
                textFields[c][r] = t;
                setStyle(c,r,t,"");
                playfield.add(t,c,r);
            }
        }
        textFields=initFields(textFields,initPerc);
    }
    public SimpleBoard initFirstNumbers(int anchorC, int anchorR, TextField[][] initField) {
        for (int i = 0; i<5;i++){
            int randC = new Random().nextInt(9);
            int randR = new Random().nextInt(9);
            int randNum = new Random().nextInt(9);
            initField[randC][randR].setText(randNum+"");
            initField[randC][randR].setDisable(true);
            setStyle(randC+anchorC,randR+anchorR,initField[randC+anchorC][randR+anchorR],"-fx-text-inner-color: darkblue;");
        }
        return h.getBoardSolution(initField, anchorC, anchorR);
    }
    public void setStyle(int c, int r, TextField t,String style) {
        if (version!=BTN_FREIFORM) {
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
        TextField[][]initField = textFields;
        SimpleBoard solution = initFirstNumbers(anchorC, anchorR, initField);
        while (solution==null){
            try{
                initFields(textFields,initPerc);
            }catch (StackOverflowError e){

            }
        }
        textFields = initField;
        SimpleSolver s = new SimpleSolver();
        SimpleBoard board = h.getCurrentBoard(textFields, anchorC, anchorR);
        if (solution != null && !s.validAndFull(board)){
            while (value > 0){
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


    private void initSamuraiField(int initPerc) {
        textFields=new TextField[21][21];
        for (int c=0; c<21;c++){
            for (int r=0; r<21;r++){
                //show coordinates
                //t.setText(c+" "+r);
                TextField t = h.defaultTextField();
                //t.setPadding(new Insets(15,15,15,15));
                textFields[c][r] = t;
                setStyle(c,r,t,"");
                h.hideUnusedBoxes(c, r, t);
                playfield.add(t,c,r);
            }
        }
        textFields=initFields(textFields,initPerc);

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
            case BTN_REGULAR:
                h.solveBoard(textFields, 0, 0);
                break;
            case BTN_SAMURAI:
                h.solveBoard(textFields, 6, 6);
                h.solveBoard(textFields, 0, 0);
                h.solveBoard(textFields, 12, 0);
                h.solveBoard(textFields, 0, 12);
                h.solveBoard(textFields, 12, 12);
                break;
            default:
                break;
        }
        btnSolve.setDisable(true);
    }
    public void handleButtonHint(ActionEvent event){
        switch (version) {
            case BTN_REGULAR:
                h.getHint(textFields, 0, 0);
                break;
            case BTN_SAMURAI:
                h.getHint(textFields, 6, 6);
                //TODO: Choose Random Playfield, but check if solvable
                break;
            default:
                break;
        }
    }
    @FXML
    public void handleButtonBacktoMain(ActionEvent event) throws IOException {
        NewScreen.openNewScreen(event,"/fxml/mainmenue.fxml");
    }


    @FXML
    public void handleButtonSaveGame(ActionEvent event) throws AWTException, IOException {
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
        String row="";
        for (int r = 0; r<9;r++){
            row="";
            for (int c=0;c<9;c++) {
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


    public void initData(String version, String generateType, String diffculty) {
        this.generateType = generateType;
        this.diffculty = diffculty;
        this.version = version;
        initializePlayfield();
    }
}
