package server.dao.impl;

import Entity.Picture;
import Entity.Shape;
import server.dao.PictureDao;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PictureDaoImpl implements PictureDao {

    private static final String sep = File.separator;

    @Override
    public Picture getPicture(String name) {
        String path = System.getProperty("user.dir") + sep + "src" + sep +
                "main" + sep + "resources" + sep + "data" + sep + name + ".ser";

        File file = new File(path);

        ObjectInputStream in = null;
        Picture picture = null;

        try {
            in = new ObjectInputStream(new FileInputStream(file));
            picture = (Picture)in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return picture;
    }

    @Override
    public List<String> getHistoryList() {
        List<String> result = new ArrayList<>();
        String path = System.getProperty("user.dir") + sep + "src" + sep +
                "main" + sep + "resources" + sep + "data";
        File file = new File(path);
        File[] files = file.listFiles();
        for (File f: files) {
            String fileName = f.getName();
            if (!fileName.equals("snapshot"))
                result.add(fileName.substring(0, fileName.length()-4));
        }
        return result;
    }

    @Override
    public void savePicture(Picture picture) {
        String path = System.getProperty("user.dir") + sep + "src" + sep +
                "main" + sep + "resources" + sep + "data" + sep + getCurrentTime() + ".ser";

        File file = new File(path);

        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(picture);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss"); // 设置日期格式
        String date = df.format(new Date()); // new Date()为获取当前系统时间
        return date;
    }
}
