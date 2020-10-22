package com.app.alertamedicamento.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UtilFile {

    public static File DIR_APP = new File(Environment.getExternalStorageDirectory(), "sys/");
    public static final File TEMP_FILE_IMG = new File(DIR_APP, "temp_00.png");

    public static void salvarTxt(String conteudo, String nomeArquivo, boolean isSalvarNoFim) {
        try {
            FileWriter fileWriter = new FileWriter(nomeArquivo, isSalvarNoFim);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(conteudo);
            writer.newLine();

            writer.close();

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> lerTxt(String nomeArquivo) {
        File file = new File(nomeArquivo);
        return lerTxt(file);
    }

    public static List<String> lerTxt(File file) {
        try {
            if (!file.exists()) {
                System.out.println("Arquivo inexistente! " + file.getAbsolutePath());
                return null;
            }

            FileReader reader = new FileReader(file);
            BufferedReader bf = new BufferedReader(reader);
            List<String> list = new ArrayList<String>();

            String linha = "";

            while ((linha = bf.readLine()) != null) {
                list.add(linha);
            }

            bf.close();
            reader.close();

            return list;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean salvarImageTempBitmap(Bitmap bitmap) {
        try {
            if (TEMP_FILE_IMG.exists()) {
                TEMP_FILE_IMG.delete();
            }

            FileOutputStream out = new FileOutputStream(TEMP_FILE_IMG);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // decodes image and scales it to reduce memory consumption
    public static Bitmap decodeFile(File f) {
        if (f == null) {
            return null;
        }

        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;

            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);

        } catch (FileNotFoundException e) {

        }

        return null;
    }
}

