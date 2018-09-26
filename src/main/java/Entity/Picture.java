package Entity;

import java.io.Serializable;
import java.util.List;

/**
 * 图片类
 */
public class Picture implements Serializable {

    private List<Shape> shapeList;  // 几何图形列表

    public Picture(List<Shape> shapeList) {
        this.shapeList = shapeList;
    }

    public List<Shape> getShapeList() {
        return shapeList;
    }

}
