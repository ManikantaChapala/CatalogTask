import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class PolySolver {

    public static void main(String[] args) throws Exception {
        String data = readFile("input.json").replaceAll("\\s+", "");
        int n = Integer.parseInt(find(data, "\"n\":", ","));
        int k = Integer.parseInt(find(data, "\"k\":", "}"));

        List<Point> points = new ArrayList<>();
        for (int i = 1; i <= n && points.size() < k; i++) {
            String search = "\"" + i + "\":{";
            int pos = data.indexOf(search);
            if (pos == -1) continue;
            String baseStr = find(data.substring(pos), "\"base\":\"", "\"");
            String valStr = find(data.substring(pos), "\"value\":\"", "\"");
            BigInteger y = new BigInteger(valStr, Integer.parseInt(baseStr));
            points.add(new Point(BigInteger.valueOf(i), y));
            System.out.println("(" + i + ", " + y + ")");
        }

        System.out.println(lagrange(points));
    }

    static String readFile(String file) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();
        return sb.toString();
    }

    static String find(String text, String start, String end) {
        int s = text.indexOf(start);
        if (s == -1) return "";
        s += start.length();
        int e = text.indexOf(end, s);
        if (e == -1) return "";
        return text.substring(s, e);
    }

    static class Point {
        BigInteger x, y;
        Point(BigInteger x, BigInteger y) { this.x = x; this.y = y; }
    }

    static BigInteger lagrange(List<Point> p) {
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < p.size(); i++) {
            BigInteger term = p.get(i).y;
            for (int j = 0; j < p.size(); j++) {
                if (i != j) {
                    BigInteger num = BigInteger.ZERO.subtract(p.get(j).x);
                    BigInteger den = p.get(i).x.subtract(p.get(j).x);
                    term = term.multiply(num).divide(den);
                }
            }
            sum = sum.add(term);
        }
        return sum;
    }
}
