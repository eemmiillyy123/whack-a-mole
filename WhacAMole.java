/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.shape.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import javafx.scene.effect.*;
import javafx.scene.input.*;

public class WhacAMole extends Application {

    private int count = 1; // 打中地鼠數量
    private Button btStart = new Button(); //重新開始按鈕
    private TextField tfCount = new TextField(); //顯示打中地鼠數量
//private Label label;
    private Text text = new Text();
    private Image mouse = new Image("https://i.imgur.com/e40fmY5.png");//地鼠影象image/m1.png
    private Image mouse1 = new Image("https://i.imgur.com/e40fmY5.png"); 
    private Image mouse2 = new Image("https://i.imgur.com/e40fmY5.png"); 
    private Image mole = new Image("https://i.imgur.com/z8tZPhD.jpg"); //背景影象image/hh.jpg
    private Image hammer = new Image("https://i.imgur.com/tsaNSjB.png"); //錘子影象image/b.png
    private ImageView mouseView = new ImageView(mouse);
    private ImageView mouseView1 = new ImageView(mouse1);
    private ImageView mouseView2 = new ImageView(mouse2);
    private ImageView moleView = new ImageView(mole);
    private ImageView hammerView = new ImageView(hammer);
    private boolean ifHit = true; //判斷是否可以打地鼠，true時能擊打
    private boolean ifHit1 = true;
    private boolean ifHit2 = true;
    final double x1 = 260, y1 = 190, xx = 250, yy = 150; //第一個地洞位置(260, 190)及間隔(250, 150)
    BoxBlur bb = new BoxBlur(); //虛化

    @Override
    public void start(Stage primaryStage) {
        Primary(primaryStage);
    }

    public void Primary(Stage stage) {
        Label view = new Label("-----打地鼠-----"); //primary的介面頂端label打地鼠
        view.setFont(Font.font("Times News Roman", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 100));
        view.setStyle("-fx - text - fill:rgb(150, 46, 5);-fx - text - border - color:rgb(138, 95, 69);");
        GridPane gridpane = new GridPane();
        BorderPane b = new BorderPane();
        Button btStartGame = new Button("Start"); //新增開始按鍵
        btStartGame.setBackground(null); //設定背景為空白
        btStartGame.setStyle("-fx - text - fill:rgb(195, 46, 5);");
        btStartGame.setFont(Font.font("Times News Roman", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 90));

        gridpane.setVgap(5);
        gridpane.setHgap(5);
        gridpane.add(btStartGame, 2, 1);

        gridpane.setAlignment(Pos.CENTER);
        view.setAlignment(Pos.BASELINE_CENTER);

//按鍵虛化設定
        bb.setWidth(5);
        bb.setHeight(5);
        bb.setIterations(3);
        
        btStartGame.addEventHandler(MouseEvent.MOUSE_ENTERED, (e -> { //滑鼠碰到的時候虛化
            btStartGame.setEffect(bb); 
        }));
        btStartGame.addEventHandler(MouseEvent.MOUSE_EXITED, (e -> { //滑鼠離開的時候恢復
            btStartGame.setEffect(null);
        }));

//點選
        btStartGame.setOnAction(e -> { //點選start會跳轉至startgame介面
            startgame(stage);
        });

        b.setTop(view);
        b.setCenter(gridpane);
        BorderPane.setAlignment(view, Pos.CENTER);
        Scene scene = new Scene(b, 800, 550);
        stage.setScene(scene);
        stage.setTitle("打地鼠");
        stage.setX(500);
        stage.setY(200);
        stage.show();

    }

    public void startgame(Stage stage) {
        Pane pane = new Pane();

        EventHandler<ActionEvent> eventHandler = e -> {
            //地鼠運動
            Line line = MouseAppear(); //地鼠1
            pane.getChildren().add(line);
            PathTransition pt = new PathTransition();
            pt.setDuration(Duration.millis(400));
            pt.setPath(line);
            pt.setNode(mouseView);
            pt.setCycleCount(2);
            pt.setAutoReverse(true);
            pt.play();

            Line line1 = MouseAppear(); //地鼠2
            pane.getChildren().add(line1);
            PathTransition pt1 = new PathTransition();
            pt1.setDuration(Duration.millis(800));
            pt1.setPath(line1);
            pt1.setNode(mouseView1);
            pt1.setCycleCount(2);
            pt1.setAutoReverse(true);
            pt1.play();

            Line line2 = MouseAppear(); //地鼠3
            pane.getChildren().add(line2);
            PathTransition pt2 = new PathTransition();
            pt2.setDuration(Duration.millis(600));
            pt2.setPath(line2);
            pt2.setNode(mouseView2);
            pt2.setCycleCount(2);
            pt2.setAutoReverse(true);
            pt2.play();

            this.ifHit = true; //置為能打
            this.ifHit1 = true;
            this.ifHit2 = true;

            //打中地鼠記錄次數 滑鼠事件
            pane.setOnMousePressed(e1 -> {
                if (e1.getX() < line.getEndX() + 80 && e1.getX() > line.getEndX() - 80 && this.ifHit && e1.getY() < line.getStartY() + 110 && e1.getY() > line.getEndY() - 110) {
                    this.count++;

                    //文字框輸出
                    this.tfCount.setText(String.format("%d", this.count)); //分數顯示
                    this.ifHit = false; //置為不能打
                }

                if (e1.getX() < line1.getEndX() + 80 && e1.getX() > line1.getEndX() - 80 && this.ifHit1 && e1.getY() < line1.getStartY() + 110 && e1.getY() > line1.getEndY() - 110) {
                    this.count++;
                    
                    //文字框輸出
                    this.tfCount.setText(String.format("%d", this.count)); //分數顯示
                    this.ifHit1 = false; //置為不能打
                }

                if (e1.getX() < line2.getEndX() + 80 && e1.getX() > line2.getEndX() - 80 && this.ifHit2 && e1.getY() < line2.getStartY() + 110 && e1.getY() > line2.getEndY() - 110) {
                    this.count++;
                    
                    //文字框輸出
                    this.tfCount.setText(String.format("%d", this.count)); //分數顯示
                    this.ifHit2 = false; //置為不能打    
                }

            });

        };

        //動畫
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1300), eventHandler)); //地鼠速度
        animation.setCycleCount(30); //地鼠鑽出次數
        animation.play();

//背景
        this.moleView.fitWidthProperty().bind(pane.widthProperty());
        this.moleView.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(moleView);

        //按鈕與文字框
        HBox hBox = new HBox(20);

        btStart.setText("Quit");  //離開按鈕
        btStart.setPrefSize(400, 80);
        tfCount.setPrefSize(200, 40); //顯示次數的大小
        btStart.setStyle("-fx - text - fill:rgb(195, 46, 5);");
        tfCount.setStyle("-fx - text - fill:rgb(195, 46, 5);");
        btStart.setBackground(null);
        tfCount.setBackground(null);
        btStart.setFont(Font.font("Times News Roman", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 40));
        tfCount.setFont(Font.font("Times News Roman", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 80));
        
        //quit虛化設定
        bb.setWidth(5);
        bb.setHeight(5);
        bb.setIterations(3);
        btStart.addEventHandler(MouseEvent.MOUSE_ENTERED, (e -> {
            btStart.setEffect(bb);
        }));
        btStart.addEventHandler(MouseEvent.MOUSE_EXITED, (e -> {
            btStart.setEffect(null);
        }));

        //重新開始按鍵
        btStart.setOnAction(e -> { //按離開後跳回primary介面，點擊次數歸零
            animation.stop();
            this.count = 0;
            this.tfCount.clear();
            Primary(stage);
        });

        hBox.getChildren().add(btStart);
        hBox.getChildren().add(tfCount);
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        pane.getChildren().add(hBox);

        //地鼠影象
        this.mouseView1.setFitWidth(120);
        this.mouseView1.setFitHeight(210);
        this.mouseView1.setX(x1);
        this.mouseView1.setY(y1);
        pane.getChildren().add(mouseView1);

        this.mouseView2.setFitWidth(120);
        this.mouseView2.setFitHeight(210);
        this.mouseView2.setX(x1);
        this.mouseView2.setY(y1);
        pane.getChildren().add(mouseView2);

        this.mouseView.setFitWidth(120);
        this.mouseView.setFitHeight(210);
        this.mouseView.setX(x1);
        this.mouseView.setY(y1);
        pane.getChildren().add(mouseView);

        //滑鼠錘子
        this.hammerView.setFitWidth(160);
        this.hammerView.setFitHeight(200);
        pane.setOnMouseMoved(e -> { //事件源必須設在pane上，不能設在圖片上，否則滑鼠要放在圖片上才能動
            this.hammerView.setX(e.getX() - 60);
            this.hammerView.setY(e.getY() - 80);
        });
        pane.getChildren().add(hammerView);

        Scene scene = new Scene(pane, 1000, 768);
        stage.setTitle("開始啦~");
        stage.setScene(scene);
        stage.show();
    }

    public Line MouseAppear() {
//        int ran = (int) (Math.random() * 10 * 0.9);
        int ran = (int) (Math.random() * 9);
        double x0 = x1, y0 = y1;
        switch (ran) {
            case 0: //第一橫排
                x0 = x1; 
                y0 = y1; 
                break;
            case 1:
                x0 = x1 + xx; 
                y0 = y1; 
                break;
            case 2:
                x0 = x1 + xx * 2; 
                y0 = y1; 
                break;
            case 3: //第二橫排
                x0 = x1; 
                y0 = y1 + yy; 
                break;
            case 4:
                x0 = x1 + xx; 
                y0 = y1 + yy;
                break;
            case 5:
                x0 = x1 + xx * 2; 
                y0 = y1 + yy;
                break;
            case 6: //第三橫排
                x0 = x1;
                y0 = y1 + yy * 2;
                break;
            case 7:
                x0 = x1 + xx;
                y0 = y1 + yy * 2;
                break;
            case 8:
                x0 = x1 + xx * 2;
                y0 = y1 + yy * 2;
                break;
        }
        Line line = new Line(x0, y0 , x0, y0 -1);
        line.setStroke(Color.TRANSPARENT);
        return line;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
