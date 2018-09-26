package server.service;

import Entity.Picture;
import Entity.Point;
import Entity.Shape;
import server.util.ShapeType;

import java.util.List;

public interface PictureService {

    /**
     * 根据名称获取图片
     * @param name 图片名
     * @return 图片
     */
    Picture getPicture(String name);

    /**
     * 获取保存的图片列表
     * @return 保存的图片列表
     */
    List<String> getHistoryList();

    /**
     * 保存图片
     * @param picture 图片
     */
    void savePicture(Picture picture);

    /**
     * 几何图形形状识别
     * @param shape 几何图形
     * @return 几何图形形状
     */
    ShapeType recognizeShape(Shape shape);

    /**
     * 计算获取几何图形中心点
     * @param shape 几何图形
     * @return 几何图形中心点
     */
    Point getCenterPoint(Shape shape);

}
