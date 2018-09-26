package server.dao;

import Entity.Picture;
import Entity.Shape;
import server.util.ShapeType;

import java.util.List;

public interface PictureDao {

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

}
