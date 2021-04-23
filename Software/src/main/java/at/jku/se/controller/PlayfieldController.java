package at.jku.se.controller;

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
import java.util.Optional;

public class PlayfieldController {
    TextField[][]textFields;
    private static final String BTN_REGULAR = "rbSaRegulaer";
    private static final String BTN_SAMURAI = "rbSaSamurai";
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

    public void initializePlayfield() {
        System.out.println("Version: "+version);
        playfield.setGridLinesVisible(false);
        playfield.setAlignment(Pos.CENTER);
        playfield.setHgap(5);
        playfield.setVgap(5);
        switch (version){
            case BTN_REGULAR:
                initRegularField();
                break;
            case BTN_SAMURAI:
                initSamuraiField();
                break;
            default:
                System.err.println("No valid Fieldtype selected");
                break;
        }

    }

    private void initSamuraiField() {
        textFields=new TextField[21][21];
        for (int c=0; c<21;c++){
            for (int r=0; r<21;r++){
                //show coordinates
                //t.setText(c+" "+r);
                TextField t = new SudokuHelper().defaultTextField();
                //t.setPadding(new Insets(15,15,15,15));
                textFields[c][r] = t;
                setCornerBoxesStyle(c, r, t);
                setMiddleBoxesStyle(c, r, t);
                hideUnusedBoxes(c, r, t);
                playfield.add(t,c,r);
            }
        }
    }

    private static void hideUnusedBoxes(int c, int r, TextField t) {
        if(((c>=9 && c<12) && (r<6 || r>=15))||((c<6 || c>=15)&&(r>=9 && r<12))){
            t.setVisible(false);
        }
    }

    private static void setMiddleBoxesStyle(int c, int r, TextField t) {
        if ((((c>2 && c<6)||(c>14 && c<18))&&((r>2 && r<6)||r>14 && r<18)||((c>8 && c<12)&&(r>8 && r<12)) )){
            t.setStyle("-fx-background-color:rgb(220,240,240)");
        }
    }

    private static void setCornerBoxesStyle(int c, int r, TextField t) {
        if ((c<=2||(c>=6 && c<=8)||(c>=12 && c<=14)|| c>=18) && ((r<=2||(r>=6 && r<=8))||(r>=12 && r<=14)||r>=18)) {
            t.setStyle("-fx-background-color:rgb(220,240,255)");
        }
    }

    private void initRegularField() {
        textFields=new TextField[9][9];
        for (int c=0; c<9;c++){
            for (int r=0; r<9;r++){
                TextField t = new SudokuHelper().defaultTextField();
                //t.setPadding(new Insets(15,15,15,15));
                textFields[c][r] = t;
                if ((c<3||c>=6) && (r<3||r>=6) || (r>=3 && r<6) && (c>=3 && c<6)) {
                    t.setStyle("-fx-background-color:rgb(220,240,255)");
                }
                playfield.add(t,c,r);
            }
        }
    }

    @FXML
    public void handleButtonSolve(ActionEvent event){
        SudokuHelper h = new SudokuHelper();
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
        SudokuHelper h = new SudokuHelper();
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
