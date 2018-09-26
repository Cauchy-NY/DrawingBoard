package Entity;

import java.io.Serializable;
import java.util.List;

/**
 * 几何图形类
 */
public class Shape implements Serializable {

    private List<Point> pointList;  // 边界点集列表

    public Shape(List<Point> pointList) {
        this.pointList = pointList;
    }

    public List<Point> getPointList() {
        return pointList;
    }

}
