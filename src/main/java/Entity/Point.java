package Entity;

import java.io.Serializable;

/**
 * 点类
 */
public class Point implements Serializable {
    private double x;  // x坐标
    private double y;  // y坐标

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
