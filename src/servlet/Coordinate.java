package servlet;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Coordinate {
    private double x;
    private double y;
    private double r;
    private String requestTime;
    private long executionTime;
    private boolean correct;

    Coordinate(double x, double y, double r, String requestTime, long executionTime, boolean correct) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.requestTime = requestTime;
        this.executionTime = executionTime;
        this.correct = correct;
    }

    public String editOutput(double n){
        DecimalFormat df = new DecimalFormat("###.##");
        DecimalFormat kk = new DecimalFormat("###.#");
        kk.setRoundingMode(RoundingMode.CEILING);
        df.setRoundingMode(RoundingMode.CEILING);
        if (Math.abs(n) >= 1000000000000000.0){ return String.valueOf(n).substring( 0, 3) + "�";}
        else if (Math.abs(n) >= 1000000000000.0){ return kk.format(n / 1000000000000.0) + "k^4";}
        else if (Math.abs(n) >= 1000000000) {return kk.format(n / 1000000000) + "kkk";}
        else if (Math.abs(n) >= 1000000) { return kk.format(n / 1000000) + "kk";}
        else if (Math.abs(n) >= 1000){ return df.format(n / 1000) + "k";}
        else { return df.format(n) + "";}
    }

    public double getX() {
        return x;
    }

    public Number getCastedX() {
        return integerCast(x);
    }

    public double getY() {
        return y;
    }

    public Number getCastedY() {
        return integerCast(y);
    }

    public double getR() {
        return r;
    }

    public Number getCastedR() {
        return integerCast(r);
    }


    public String getRequestTime() {
        return requestTime;
    }

    public double getExecutionTime() {
        return executionTime/1000000.0;
    }

    public String getCorrectWords() {
        if (correct) {
            return "<td class='green-color'>Входит</td>";
        }
        return "<td class='red-color'>Не входит</td>";
    }

    private Number integerCast(double a) {
        if (a % 1 == 0) {
            return (int) a;
        }
        return a;
    }
}