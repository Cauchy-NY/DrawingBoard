package client.controller;

import Entity.Picture;
import Entity.Point;
import Entity.Shape;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import server.service.PictureService;
import server.service.impl.PictureServiceImpl;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


public class Controller implements Initializable{

    @FXML
    private Canvas canvas;

    @FXML
    private ChoiceBox<String> historyList;

    private boolean mouseState = false;

    GraphicsContext context;

    List<Shape> shapeList;  // 存放当前画板上的几何图形列表
    List<Point> tmpPointList;  // 当前画完的几何图形的边界点列表

    PictureService ps = new PictureServiceImpl();

    private static final String sep = File.separator;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        shapeList = new ArrayList<>();
        context = canvas.getGraphicsContext2D();
    }

    /**
     * 保存响应函数
     */
    @FXML
    private void save() {
        snapshot(canvas);
        Picture picture = new Picture(shapeList);
        ps.savePicture(picture);
        System.out.println("保存成功");
    }

    /**
     * 识别响应函数
     */
    @FXML
    private void recognize() {
        for (Shape shape: shapeList) {
            ps.recognizeShape(shape);
            Point centerPoint = ps.getCenterPoint(shape);
            String type = "";
            switch (ps.recognizeShape(shape)) {
                case CIRCLES: type = "圆"; break;
                case TRIANGLE: type = "三角形"; break;
                case POLYGONS: type = "多边形"; break;
                case RECTANGLE: type = "矩形"; break;
                default: type = "未知图形"; break;
            }
            context.fillText(type, centerPoint.getX(), centerPoint.getY());
        }
    }

    /**
     * 清空响应函数
     */
    @FXML
    private void clear() {
        context.closePath();
        shapeList = new ArrayList<>();
        tmpPointList = new ArrayList<>();
        context.clearRect(0,0,700,500);
    }

    /**
     * 加载响应函数
     */
    @FXML
    private void load() {
        historyList.setVisible(true);
    }

    // 选择历史文件
    @FXML
    private void choose() {
        List<String> fileList = ps.getHistoryList();

        historyList.setItems(FXCollections.observableArrayList(fileList));

        historyList.getSelectionModel().selectedIndexProperty().addListener((ov, oldv, newv)->{
            Picture picture = ps.getPicture(fileList.get(newv.intValue()));
            repaint(picture);
            historyList.setVisible(false);
        });
    }

    // 重画历史文件
    private void repaint(Picture picture) {
        shapeList = picture.getShapeList();

        for (Shape tmpShape: shapeList) {
            List<Point> tmpList = tmpShape.getPointList();
            context.beginPath();
            context.setStroke(Color.BLACK);
            context.setLineWidth(3);
            context.moveTo(tmpList.get(0).getX(), tmpList.get(0).getY());
            for (int i = 1; i < tmpList.size(); i++) {
                context.lineTo(tmpList.get(i).getX(), tmpList.get(i).getY());
                context.stroke();
            }
            context.closePath();
        }
    }

    @FXML
    private void canvasMouseDown(MouseEvent e) {
        mouseState = true;
        tmpPointList = new ArrayList();
        context.beginPath();
        context.setStroke(Color.BLACK);
        context.setLineWidth(3);
        context.moveTo(e.getX(),e.getY());
        tmpPointList.add(new Point(e.getX(), e.getY()));
    }

    @FXML
    private void canvasMouseMove(MouseEvent e) {
        if(mouseState) {
            context.lineTo(e.getX(),e.getY());
            context.stroke();
            tmpPointList.add(new Point(e.getX(), e.getY()));
        }
    }

    @FXML
    private void canvasMouseUp() {
        //自动闭合
        context.lineTo(tmpPointList.get(0).getX(), tmpPointList.get(0).getY());
        context.stroke();
        tmpPointList.add(tmpPointList.get(0));
        context.closePath();

        shapeList.add(new Shape(tmpPointList));
        mouseState = false;
    }

    // 屏幕截图
    private void snapshot(Node view) {
        String path = System.getProperty("user.dir") + sep + "src" + sep +
                "main" + sep + "resources" + sep + "data" + sep + "snapshot" + sep + getCurrentTime() + ".png";

        Image image = view.snapshot(null, null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss"); // 设置日期格式
        String date = df.format(new Date()); // new Date()为获取当前系统时间
        return date;
    }
}
