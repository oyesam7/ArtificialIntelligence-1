package com.junjunguo.dtl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This file is part of java.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 15/04/16.
 */
public class DTLMain {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        DTLMain             dtlMain      = new DTLMain();
        DTL                 dtl          = new DTL();
        List<List<Integer>> trainingList = dtlMain.getListFromFile("training");
        List<List<Integer>> testList     = dtlMain.getListFromFile("test");
        for (int i = 0; i < 2; i++) {
            List<Integer> attributes = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
            Tree          root       = dtl.decisionTreeLearning(trainingList, attributes, new ArrayList<>(), i==0);
            System.out.println("\nThe root tree: with random importance = " + (i == 0) + "\n" + root.generateTree());
            dtl.test(root, testList);
            dtl.test(root, trainingList);
        }
    }


    /**
     * Gets list from file.
     *
     * @param fileName the file name
     * @return the list from file
     */
    List<List<Integer>> getListFromFile(String fileName) {
        String              path = this.getClass().getClassLoader().getResource("data/" + fileName + ".txt").getPath();
        List<List<Integer>> list = new ArrayList<>();
        Scanner             s    = null;
        try {
            s = new Scanner(new File(path));
            List<Integer> data = new ArrayList<>();
            int           i    = 0;
            while (s.hasNextInt()) {
                i++;
                data.add(s.nextInt());
                if (i == 8) {
                    list.add(data);
                    data = new ArrayList<>();
                    i = 0;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
        return list;
    }
}
