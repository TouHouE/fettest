import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgproc.*;
import org.opencv.imgcodecs.*;

import java.util.*;

public class Wave {

    static {//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        OpenCV.loadLocally();
    }

    private static final int WAVE_IMG_SIDE = 3000, C_IMG_SIDE = 500;
    private static final int SQUARE_MAX_SIDE_BASE = 1, COS_MAX_SIDE_BASE = 50, COS_SYN_MAX_SIDE_BASE = 50, TOOTH_MAX_SIDE_BASE = 1, TRIANGLE_MAX_SIDE_BASE = 1;
    private static final int TOOTH_MAX_SIDE_WAVE = 5, COS_MAX_SIDE_WAVE = 300, COS_SYN_MAX_SIDE_WAVE = 300, SQUARE_MAX_SIDE_WAVE = 5, TRIANGLE_MAX_SIDE_WAVE = 5;
    private static float T = 0F, T2 = 0F, SCANN_WAVE_T = 360F;
    private static final int TOOTH_WAVE = 1, SQUARE_WAVE = 2, COS_SYNTHETIC_WAVE = 4, COS_WAVE = 5, TRIANGLE_WAVE = 3;
    private static final int  E_0 = 1, E_1 = 10, E_2 = 100, E_3 = 1000, E_4 = 10000;

    private static final Scalar BLACK = new Scalar(0);
    private static final Scalar WHITE = new Scalar(255);
    private static final WaveFunction WF = new WaveFunction();
    private static final Scanner INPUT = new Scanner(System.in);

    public static void main(String[] args) throws Exception{
        System.out.printf("Welcome to OpenCV %s\n", Core.VERSION);
        boolean continue_bool = true;
        //onlyHomework();

        while(continue_bool) {

            kind_Of_Base_And_Wave();
            System.out.printf("Run out!!!\n");
            //HighGui.waitKey();
            continue_bool = isContinue();
        }

        System.out.printf("Program Fin\n");
    }

    private static boolean isContinue() {
        boolean continueB = true;
        System.out.printf("Continue ?(y/n)");
        String uInput = INPUT.next();

        if(uInput.equals("y") || uInput.equals("Y")) {
            continueB = true;
        } else {
            continueB = false;
        }

        return continueB;
    }

    /*
    *
    *選擇是否要印出波型圖
    *並選擇波型
    *
    * */
    private static void kind_Of_Base_And_Wave() {
        System.out.printf("Choose the base wave:\n  (1)Sawtooth wave\n  (2)Square wave\n  (3)Triangle Wave\n  (4)COS Synthetic Wave\n  (5)COS Wave\n");
        System.out.printf("If you don't want to print pictures. Please enter a number greater than 5\n:");
        int baseInput = INPUT.nextInt();

        switch(baseInput) {
            case TOOTH_WAVE:
                baseMatBuild_SawTooth();
                break;
            case SQUARE_WAVE:
                baseMatBuild_Square();
                break;
            case COS_SYNTHETIC_WAVE:
                baseMatBuild_SyntheticCOS();
                break;
            case COS_WAVE:
                baseMatBuild_COS();
                break;
            case TRIANGLE_WAVE:
                baseMatBuild_Triangle();
                break;
             default:
                System.out.println();
                break;
        }

        /*
         *
         *選擇是否要印出波型轉換圖
         *並選擇轉換的波型
         *
         * */
        System.out.printf("Choose the wave:\n  (1)Sawtooth wave\n  (2)Square wave\n  (3)Triangle Wave\n  (4)COS Synthetic Wave\n  (5)COS Wave\n");
        System.out.printf("If you don't want to print pictures. Please enter a number greater than 5\n:");
        int waveInput = INPUT.nextInt();

        switch(waveInput) {
            case TOOTH_WAVE://鋸齒波
                sawToothWaveMatBuild();
                break;
            case SQUARE_WAVE://方波
                squareWaveMatBuild();
                break;
            case COS_SYNTHETIC_WAVE://cos的合成波
                cosSyntheticWaveMatBuild();
                break;
            case COS_WAVE://純粹的cos波
                cosWaveMatBuild();
                break;
            case TRIANGLE_WAVE://三角波
                triangleWaveMatBuild();
                break;
        }

    }

    private static void baseMatBuild_Square() {
        Mat mSquare = setBaseMat(500, 3000);
        Point pSquare = new Point();
        float temp = 0F;
        double squareWave = 0D;

        for(int i = 0; i <= SCANN_WAVE_T * E_4; i++) {

            temp = (float)i / E_4;
            squareWave = -1 * (WF.squareWave(temp) + 1);

            pSquare.x = temp * 100;
            pSquare.y = 250 + 100 * SQUARE_MAX_SIDE_BASE * squareWave;

            Imgproc.circle(mSquare, pSquare, 1, BLACK, -1);

            printData(temp);
        }

        Imgcodecs.imwrite("SquareWave.png", mSquare);

        //HighGui.imshow("SquareMat", mSquare);
    }

    private static void baseMatBuild_SawTooth() {
        Mat mTooth = setBaseMat(500, 3000);
        Point pTooth = new Point();
        double sawtoothWave = 0D;
        float temp = 0F;

        for(int i = 0; i <= SCANN_WAVE_T * E_3; i++) {

            temp = (float)i / E_3;
            sawtoothWave = (WF.sawtoothWave(i) + 1) * -1;

            pTooth.x = temp * 100;
            pTooth.y = 250 + 100 * TOOTH_MAX_SIDE_BASE * sawtoothWave;

            Imgproc.circle(mTooth, pTooth, 1, BLACK, -1);
            printData(temp);
        }

        Imgcodecs.imwrite("SawToothWave.png", mTooth);
        //HighGui.imshow("SawToothMat", mTooth);
    }

    private static void baseMatBuild_Triangle() {
        Mat mTriangle = setBaseMat(500, 3000);
        Point pTriangle = new Point();
        double triangleWave = 0D;
        float temp = 0F;

        for(int i = 0; i <= SCANN_WAVE_T * E_3; i++) {

            temp = (float)i / E_3;
            triangleWave = (WF.triangleWave(i) + 1) * -1;

            pTriangle.x = temp * 50;
            pTriangle.y = 250 + 100 * TOOTH_MAX_SIDE_BASE * triangleWave;

            Imgproc.circle(mTriangle, pTriangle, 1, BLACK, -1);
            printData(temp);
        }

        Imgcodecs.imwrite("TriangleWave.png", mTriangle);
        //HighGui.imshow("TriangleMat", mTriangle);
    }

    private static void baseMatBuild_SyntheticCOS() {
        Mat mSynCos = setBaseMat(500, 1080);
        Point[] pSynCos = new Point[2];

        for(int i = 0; i < pSynCos.length; ++i) {
            pSynCos[i] = new Point();
        }

        float temp = 0F;
        double synCos = 0D;

        for(int i = 0; i <= SCANN_WAVE_T * E_3; i++) {

            temp = (float)i / E_3;
            synCos = -1 * (WF.syntheticCosWave(temp) + 1);

            pSynCos[0].x = temp * 75;
            pSynCos[0].y = 250 + COS_SYN_MAX_SIDE_BASE * synCos;
            pSynCos[1].x = pSynCos[0].x + 1;
            pSynCos[1].y = pSynCos[0].y + 1;

            Imgproc.rectangle(mSynCos, pSynCos[0], pSynCos[1], BLACK, -1);
            printData(temp);
        }

        Imgcodecs.imwrite("SyntheticWave.png", mSynCos);
       // HighGui.imshow("CosMat", mSynCos);
    }

    private static void baseMatBuild_COS() {
        Mat mCos = setBaseMat(500, 1080);
        Point[] pCos = new Point[2];

        for(int i = 0; i < pCos.length; ++i) {
            pCos[i] = new Point();
        }

        float temp = 0F, I = 0F;

        for(int i = 0; i <= SCANN_WAVE_T * E_4; i++) {

            temp = (float)i / E_4;

            pCos[0].x = temp * 25;
            pCos[0].y = 250 + -1 * COS_MAX_SIDE_BASE * WF.cosWave(temp);
            pCos[1].x = pCos[0].x + 1;
            pCos[1].y = pCos[0].y + 1;

            Imgproc.rectangle(mCos, pCos[0], pCos[1], BLACK, -1);
            printData(temp);
        }

        Imgcodecs.imwrite("CosWave.png", mCos);
        //HighGui.imshow("CosMat", mCos);
    }

    private static void sawToothWaveMatBuild() {
        Mat m5 = setWaveMat();
        Mat m6 = setWaveMat();

        Point p5 = new Point();
        Point p6 = new Point();

        float temp = 0F, tempi = 0F;
        double sawToothWaveValue = 0D;

        for(float t = 0F; t <= 280F; t += 1) {

            temp = t / 10;
            T = temp;
            T2 = temp;

            for (int i = 0; i <= SCANN_WAVE_T * E_3; i++) {

                tempi = (float)i / E_3;
                sawToothWaveValue = (WF.sawtoothWave(tempi) + 1) * -1;

                p5.x = (WAVE_IMG_SIDE / 2) + 100 * TOOTH_MAX_SIDE_WAVE * Math.cos(tempi / T2) * sawToothWaveValue;
                p5.y = (WAVE_IMG_SIDE / 2) + 100 * TOOTH_MAX_SIDE_WAVE * Math.sin(tempi / T2) * sawToothWaveValue;

                p6.x = (WAVE_IMG_SIDE / 2) + 100 * TOOTH_MAX_SIDE_WAVE * Math.cos(tempi * T2) * sawToothWaveValue;
                p6.y = (WAVE_IMG_SIDE / 2) + 100 * TOOTH_MAX_SIDE_WAVE * Math.cos(tempi * T2) * sawToothWaveValue;

                Imgproc.circle(m5, p5, 1, BLACK, -1);
                Imgproc.circle(m6, p6, 1, BLACK, -1);

                printData(T, tempi);
            }

            saveImg(m5, T, "m5/m5---");
            saveImg(m6, T, "m6/m6---");

            m5 = setWaveMat();
            m6 = setWaveMat();
        }
    }

    private static void squareWaveMatBuild() {
        Mat m7 = setWaveMat();
        Mat m8 = setWaveMat();

        Point p7 = new Point();
        Point p8 = new Point();

        float temp = 0F, tempi = 0F;
        double squareWave = 0D;

        for(float t = 0F; t <= 280F; t += 1) {

            temp = t / 10;

            T = temp;
            T2 = temp;

            for (int i = 0; i <= SCANN_WAVE_T * E_3; i++) {

                tempi = (float)i / E_3;
                squareWave = -1 * (WF.squareWave(tempi) + 1);

                p7.x = (WAVE_IMG_SIDE / 2) + 100 * SQUARE_MAX_SIDE_WAVE * Math.cos(tempi / T2) * squareWave;
                p7.y = (WAVE_IMG_SIDE / 2) + 100 * SQUARE_MAX_SIDE_WAVE * Math.sin(tempi / T2) * squareWave;

                p8.x = (WAVE_IMG_SIDE / 2) + 100 * SQUARE_MAX_SIDE_WAVE * Math.cos(tempi * T2) * squareWave;
                p8.y = (WAVE_IMG_SIDE / 2) + 100 * SQUARE_MAX_SIDE_WAVE * Math.cos(tempi * T2) * squareWave;

                Imgproc.circle(m7, p7, 1, BLACK, -1);
                Imgproc.circle(m8, p8, 1, BLACK, -1);

                printData(T, tempi);
            }

            saveImg(m7, T, "m7/m7---");
            saveImg(m8, T, "m8/m8---");

            m7 = setWaveMat();
            m8 = setWaveMat();


        }
    }

    private static void cosSyntheticWaveMatBuild() {
        Mat m3 = setWaveMat();
        Mat m4 = setWaveMat();

        Mat mC3 = setMatC();
        Mat mC4 = setMatC();

        Point p3 = new Point();
        Point p4 = new Point();

        Point pC3 = new Point();
        Point pC4 = new Point();

        float C3 = 0F, C4 = 0F, temp = 0F, tempi = 0F;
        double synCos = 0D;

        for (float t = 0F; t <= 28000; t += 1) {

            temp = t / 10;
            T = temp;
            T2 = temp;

            for(int i = 0; i <= SCANN_WAVE_T * E_3; ++i) {

                tempi = (float)i / E_3;
                synCos = -1 * (WF.syntheticCosWave(tempi) + 1);

                p3.x = (WAVE_IMG_SIDE / 2) + COS_SYN_MAX_SIDE_WAVE * Math.cos(tempi / T2) * synCos;
                p3.y = (WAVE_IMG_SIDE / 2) + COS_SYN_MAX_SIDE_WAVE * Math.sin(tempi / T2) * synCos;

                p4.x = (WAVE_IMG_SIDE / 2) + COS_SYN_MAX_SIDE_WAVE * Math.cos(tempi * T2) * synCos;
                p4.y = (WAVE_IMG_SIDE / 2) + COS_SYN_MAX_SIDE_WAVE * Math.sin(tempi * T2) * synCos;

                Imgproc.circle(m3, p3, 1, BLACK, -1);
                Imgproc.circle(m4, p4, 1, BLACK, -1);

                C3 += p3.x;
                C4 += p4.x;

                printData(T, tempi);
            }

            saveImg(m3, T, "m3/m3---");
            saveImg(m4, T, "m4/m4---");

            pC3.x = (C_IMG_SIDE / 2) + C3 / SCANN_WAVE_T;
            pC3.y = (C_IMG_SIDE / 2) + t * 20;

            pC4.x = (C_IMG_SIDE / 2) + C4 / SCANN_WAVE_T;
            pC4.y = (C_IMG_SIDE / 2) + t * 20;

            Imgproc.circle(mC3, pC3, 1, BLACK, -1);
            Imgproc.circle(mC4, pC4, 1, BLACK, -1);

            m3 = setWaveMat();
            m4 = setWaveMat();

            System.out.printf("T:%f\n M3:%f\n M4:%f\n\n", T, pC3.x / (1000 * SCANN_WAVE_T), pC4.x / (1000 * SCANN_WAVE_T));
        }

        //HighGui.imshow("m3", mC3);
        //HighGui.imshow("m4", mC4);
    }

    private static void cosWaveMatBuild() {
        Mat m1 = setWaveMat();
        Mat m2 = setWaveMat();

        Mat mC1 = setMatC();
        Mat mC2 = setMatC();

        Point p1 = new Point();
        Point p2 = new Point();

        Point pC1 = new Point();
        Point pC2 = new Point();

        double cY = 0D, cX = 0D;
        float tempt = 0F, tempi = 0F;

        for (int t = 0; t <= 280; t += 1) {

            tempt = (float)t / 10;
            T = tempt;
            T2 = tempt;

            for (int i = 0; i <= SCANN_WAVE_T * E_3; i++) {

                tempi = (float)i / E_3;

                p1.x = (WAVE_IMG_SIDE / 2) + COS_MAX_SIDE_WAVE * Math.cos(tempi / T) * (Math.cos(tempi) + 1);
                p1.y = (WAVE_IMG_SIDE / 2) + COS_MAX_SIDE_WAVE * Math.sin(tempi / T) * (Math.cos(tempi) + 1);

                p2.x = (WAVE_IMG_SIDE / 2) + COS_MAX_SIDE_WAVE * Math.cos(tempi * T) * (Math.cos(tempi) + 1);
                p2.y = (WAVE_IMG_SIDE / 2) + COS_MAX_SIDE_WAVE * Math.sin(tempi * T) * (Math.cos(tempi) + 1);

                Imgproc.circle(m1, p1, 1, BLACK, -1);
                Imgproc.circle(m2, p2, 1, BLACK, -1);

                cY += isNaN(p2.y);
                cX += isNaN(p2.x);

                printData(T, tempi);
            }

            saveImg(m1, T, "m1/m1---");
            saveImg(m2, T, "m2/m2---");

            pC1.x = (C_IMG_SIDE / 2) + cY / SCANN_WAVE_T;
            pC1.y = (C_IMG_SIDE / 2) + t * 20;

            pC2.x = (C_IMG_SIDE / 2) + cX / SCANN_WAVE_T;
            pC2.y = (C_IMG_SIDE / 2) + t * 20;

            Imgproc.circle(mC1, pC1, 1, BLACK, -1);
            Imgproc.circle(mC2, pC2, 1, BLACK, -1);

            m1 = setWaveMat();
            m2 = setWaveMat();

            System.out.printf("T:%f\n Mc1:%f\n Mc2:%f\n\n", T, pC1.x / (1000 * SCANN_WAVE_T), pC2.x / (1000 * SCANN_WAVE_T));
        }

       // HighGui.imshow("m1",  mC1);
        //HighGui.imshow("m2", mC2);
    }

    private static void triangleWaveMatBuild() {
        Mat m9 = setWaveMat();
        Mat m10 = setWaveMat();

        Point p9 = new Point();
        Point p10 = new Point();

        float temp = 0F, tempi = 0F;
        double triangleWave = 0D;

        for(float t = 0F; t <= 280F; t += 1) {

            temp = t / 10;

            T = temp;
            T2 = temp;

            for(int i = 0; i <= SCANN_WAVE_T * E_3; ++i) {

                tempi = (float)i / E_3;
                triangleWave = -1 * (WF.triangleWave(tempi) + 1);

                p9.x = (WAVE_IMG_SIDE / 2) + 100 * SQUARE_MAX_SIDE_WAVE * Math.cos(tempi / T2) * triangleWave;
                p9.y = (WAVE_IMG_SIDE / 2) + 100 * SQUARE_MAX_SIDE_WAVE * Math.sin(tempi / T2) * triangleWave;

                p10.x = (WAVE_IMG_SIDE / 2) + 100 * SQUARE_MAX_SIDE_WAVE * Math.cos(tempi * T2) * triangleWave;
                p10.y = (WAVE_IMG_SIDE / 2) + 100 * SQUARE_MAX_SIDE_WAVE * Math.cos(tempi * T2) * triangleWave;

                Imgproc.circle(m9, p9, 1, BLACK, -1);
                Imgproc.circle(m10, p10, 1, BLACK, -1);

                printData(T, tempi);

            }

            saveImg(m9, T, "m9/m9---");
            saveImg(m10, T, "m10/m10---");

            m9 = setWaveMat();
            m10 = setWaveMat();
        }
    }

    private static void saveImg(Mat mat, float t, String imgName) {
        Imgcodecs.imwrite(imgName + t + ".png", mat);
    }

    private static Mat setBaseMat(int rows, int cols) {
        Mat mat = new Mat(rows, cols, CvType.CV_8UC1, WHITE);
        Mat mx = mat.col(0);
        Mat my = mat.row(rows / 2);
        mx.setTo(BLACK);
        my.setTo(BLACK);

        return mat;
    }

    private static Mat setWaveMat() {
        Mat mat = new Mat(WAVE_IMG_SIDE, WAVE_IMG_SIDE, CvType.CV_8UC1, WHITE);
        Mat x = mat.col(WAVE_IMG_SIDE / 2);
        Mat y = mat.row(WAVE_IMG_SIDE / 2);
        x.setTo(BLACK);
        y.setTo(BLACK);

        return mat;
    }

    private static Mat setMatC() {
        Mat mat = new Mat(C_IMG_SIDE, C_IMG_SIDE, CvType.CV_8UC1, WHITE);
        Mat x = mat.row(C_IMG_SIDE / 2);
        Mat y = mat.col(C_IMG_SIDE / 2);
        x.setTo(BLACK);
        y.setTo(BLACK);

        return mat;
    }

    private static void printData(float i) {
        System.out.printf("Time:%f\n\n", i);
    }

    private static void printData(float t, float i) {
        System.out.printf("Round:%f\n  Point:%f\n\n", t, i);
    }

    private static double isNaN(double num) {
        double number = 0D;

        if(!Double.toString(num).equals("NaN")) {
            number = num;
        }

        return number;
    }

    private static void onlyHomework() {
        double[] temp = new double[2];
        double Ttemp = 0D;

        while(true) {
            System.out.printf("Triangle(t):");
            double time = INPUT.nextDouble();
            double triangleT[] = WF.triangleWave(time);

            System.out.println("============================");
            System.out.printf("V(%s)   \t| %s\t     | %s\t\n", Double.toString(time), Double.toString(time), Double.toString(Ttemp));
            System.out.printf("-----------------------------\n");
            System.out.printf("PI true \t| %f\t | %f\t\n", triangleT[1], temp[1]);
            System.out.printf("PI false\t| %f\t | %f\t\n", triangleT[0], temp[0]);
            System.out.println("============================");


            //System.out.printf("Ans:  %f|%f.......include PI\n", triangleT[1]);
            //System.out.printf("      %f.......not include PI\n\n\n================\n", triangleT[0]);
            temp = triangleT;
            Ttemp = time;
        }
    }

}

class WaveFunction {

    private final double PI = Math.PI, F = 3D;

    public double cosWave(float i) {
        double cos = Math.cos(i);
        return cos;
    }

    public double syntheticCosWave(float i) {
        return Math.cos(3 * i) + Math.cos(7 * i) + Math.cos(13 * i) + Math.cos(6 * i) + Math.cos(1.2F * i) + Math.cos(0.2F * i);
    }

    public double sawtoothWave(float i) {
        double x = 0D;

        for(int k = 1; k <= 50; ++k) {
            x += Math.pow(-1D, k) * Math.sin(k * F * i) / k;
        }

        x = (x * 2) / PI;
        return x;
    }

    public double squareWave(float i) {
        double x = 0D;

        for(int k = 1; k <= 50; ++k) {
            x += Math.sin((2 * k - 1) * F * i) / (2 * k - 1);
        }

        x = (x * 4) / PI;

        return x;
    }

    public double triangleWave(float i) {
        double x = 0D;

        for(int k = 0; k <= 25; ++k) {
            x += Math.pow(-1D, k) * Math.sin((2 * k + 1) * i) / Math.pow((2 * k + 1), 2D);
        }

        x = (x * 8) / Math.pow(Math.PI, 2D);
        return x;
    }


    public double[] triangleWave(double i) {
        double x = 0D;

        for(int k = 0; k <= 500; ++k) {
            x += Math.pow(-1D, k) * Math.sin((2 * k + 1) * i) / Math.pow((2 * k + 1), 2D);
        }

        double xA[] = {x, (x * 8) / Math.pow(Math.PI, 2D)};
        return xA;
    }

    public double customsSynCosWave(float i) {
        while(true) {

        }
    }
}
