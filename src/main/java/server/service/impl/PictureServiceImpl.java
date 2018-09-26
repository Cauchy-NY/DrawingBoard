package server.service.impl;

import Entity.Picture;
import Entity.Point;
import Entity.Shape;
import server.dao.PictureDao;
import server.dao.impl.PictureDaoImpl;
import server.service.PictureService;
import server.util.ShapeType;

import java.util.ArrayList;
import java.util.List;

public class PictureServiceImpl implements PictureService {

    PictureDao pd = new PictureDaoImpl();

    @Override
    public Picture getPicture(String name) {
        return pd.getPicture(name);
    }

    @Override
    public List<String> getHistoryList() {
        return pd.getHistoryList();
    }

    @Override
    public void savePicture(Picture picture) {
        pd.savePicture(picture);
    }

    @Override
    public ShapeType recognizeShape(Shape shape) {
        Shape frame = getFrame(shape);
        double s1 = calculateArea(shape);
        double s2 = calculateArea(frame);
        double rate = s1 / s2;
        // 1/2 -> pi/4
        return rate < 0.28 ? ShapeType.TRIANGLE :
                rate < 0.55 ? ShapeType.CIRCLES :
                        rate < 0.60 ? ShapeType.POLYGONS : ShapeType.RECTANGLE;
    }

    // calculate the center point
    @Override
    public Point getCenterPoint(Shape shape) {
        double x = 0.0, y = 0.0;
        List<Point> pointList = shape.getPointList();
        for (Point point: pointList) {
            x += point.getX();
            y += point.getY();
        }
        return new Point(x/(double)pointList.size(), y/(double)pointList.size());
    }

    // calculate the area
    private double calculateArea(Shape shape) {
        double area = 0.0;
        List<Point> pointList = shape.getPointList();
        for (int i = 0; i < pointList.size()-1; i++) {
            Point p1 = pointList.get(i);
            Point p2 = pointList.get(i+1);
            area += ((p1.getX() * p2.getY()) - (p1.getY() * p2.getX()));
        }
        area = Math.abs(area) / 2.0;
        return area;
    }

    private Shape getFrame(Shape shape) {
        List<Point> pointList = shape.getPointList();
        List<Point> result = new ArrayList<>();
        Point p = pointList.get(0);
        double high = p.getY(), low = p.getY();
        double right = p.getX(), left = p.getX();
        for (Point point: pointList) {
            if (point.getY() > high)
                high = point.getY();

            if (point.getY() < low)
                low = point.getY();

            if (point.getX() < left)
                left = point.getX();

            if (point.getX() > right)
                right = point.getX();
        }
        result.add(new Point(left, high));
        result.add(new Point(left, low));
        result.add(new Point(right, high));
        result.add(new Point(right, low));
        return new Shape(result);
    }
}
