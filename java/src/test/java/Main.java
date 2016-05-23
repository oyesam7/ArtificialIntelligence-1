import java.util.ArrayList;
import java.util.List;

/**
 * This file is part of java.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 22/04/16.
 */
public class Main {
    public static void main(String[] args) {
        String s = "三人行必有我师也";
        System.out.println(s + "\n" + s.length());

        String a, b, c, d, e;
        a = "-a"; b = "-b"; c = "-c"; d = "-d"; e = "-d";
        System.out.println("a: " + a + "; b: " + b + "; c: " + c + "; d: " + d + "; e: " + e);
        List<String> l = new ArrayList<>();
        System.out.println(l.toString());
        l.add(a); l.add(b); l.add(c); l.add(d); l.add(e);
        a = "--a";

        System.out.println("a: " + a + "; b: " + b + "; c: " + c + "; d: " + d + "; e: " + e);
        System.out.println(l.toString());
    }
}
