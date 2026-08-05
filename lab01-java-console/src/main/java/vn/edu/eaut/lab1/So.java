package vn.edu.eaut.lab1;

public class So {

    public static int tongChanDenN(int n) {
        kiemTraNguyenDuong(n);

        int s = 0;
        for (int i = 2; i <= n; i += 2) {
            s += i;
        }
        return s;
    }

    public static double tongNghichDao(int n) {
        kiemTraNguyenDuong(n);

        double s = 0;
        for (int i = 1; i <= n; i++) {
            s += 1.0 / i;
        }
        return s;
    }

    public static boolean laSoNguyenTo(int n) {
        if (n < 2) {
            return false;
        }

        if (n == 2) {
            return true;
        }

        if (n % 2 == 0) {
            return false;
        }

        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static String loaiTamGiac(double a, double b, double c) {
        final double EPS = 1e-9;

        if (a <= 0 || b <= 0 || c <= 0) {
            return "Khong phai tam giac";
        }

        if (a + b <= c || a + c <= b || b + c <= a) {
            return "Khong phai tam giac";
        }

        boolean deu =
                Math.abs(a - b) < EPS
                && Math.abs(b - c) < EPS;

        boolean can =
                Math.abs(a - b) < EPS
                || Math.abs(a - c) < EPS
                || Math.abs(b - c) < EPS;

        double x = a;
        double y = b;
        double z = c;

        if (x > y) {
            double temp = x;
            x = y;
            y = temp;
        }

        if (y > z) {
            double temp = y;
            y = z;
            z = temp;
        }

        if (x > y) {
            double temp = x;
            x = y;
            y = temp;
        }

        boolean vuong =
                Math.abs(x * x + y * y - z * z) < EPS;

        if (deu) {
            return "Tam giac deu";
        }

        if (vuong && can) {
            return "Tam giac vuong can";
        }

        if (vuong) {
            return "Tam giac vuong";
        }

        if (can) {
            return "Tam giac can";
        }

        return "Tam giac thuong";
    }

    public static String dayFibonacci(int n) {
        kiemTraNguyenDuong(n);

        StringBuilder ketQua = new StringBuilder();

        long a = 0;
        long b = 1;

        for (int i = 1; i <= n; i++) {
            if (i > 1) {
                ketQua.append(" ");
            }

            ketQua.append(a);

            long next = a + b;
            a = b;
            b = next;
        }

        return ketQua.toString();
    }

    private static void kiemTraNguyenDuong(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                    "n phai la so nguyen duong"
            );
        }
    }
}