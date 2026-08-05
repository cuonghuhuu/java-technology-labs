package vn.edu.eaut.lab1;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            hienThiMenu();
            System.out.print("Chon bai tap: ");
            choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1 -> bai1(scanner);
                    case 2 -> bai2(scanner);
                    case 3 -> bai3(scanner);
                    case 4 -> bai4(scanner);
                    case 5 -> bai5(scanner);
                    case 0 -> System.out.println("Ket thuc chuong trinh.");
                    default -> System.out.println("Lua chon khong hop le!");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Loi: " + ex.getMessage());
            }

            System.out.println();
        } while (choice != 0);

        scanner.close();
    }

    private static void hienThiMenu() {
        System.out.println("========== LAB 1 - JAVA SE CONSOLE ==========");
        System.out.println("1. Tinh S = 2 + 4 + ... + n");
        System.out.println("2. Tinh S = 1 + 1/2 + ... + 1/n");
        System.out.println("3. Kiem tra so nguyen to");
        System.out.println("4. Kiem tra va phan loai tam giac");
        System.out.println("5. Hien thi n so Fibonacci dau tien");
        System.out.println("0. Thoat");
    }

    private static void bai1(Scanner scanner) {
        System.out.print("Nhap n: ");
        int n = scanner.nextInt();

        System.out.println("S = " + So.tongChanDenN(n));
    }

    private static void bai2(Scanner scanner) {
        System.out.print("Nhap n: ");
        int n = scanner.nextInt();

        System.out.printf("S = %.4f%n", So.tongNghichDao(n));
    }

    private static void bai3(Scanner scanner) {
        System.out.print("Nhap n: ");
        int n = scanner.nextInt();

        if (So.laSoNguyenTo(n)) {
            System.out.println(n + " la so nguyen to.");
        } else {
            System.out.println(n + " khong phai la so nguyen to.");
        }
    }

    private static void bai4(Scanner scanner) {
        System.out.print("Nhap a: ");
        double a = scanner.nextDouble();

        System.out.print("Nhap b: ");
        double b = scanner.nextDouble();

        System.out.print("Nhap c: ");
        double c = scanner.nextDouble();

        System.out.println("Ket qua: " + So.loaiTamGiac(a, b, c));
    }

    private static void bai5(Scanner scanner) {
        System.out.print("Nhap n: ");
        int n = scanner.nextInt();

        System.out.println("Day Fibonacci: " + So.dayFibonacci(n));
    }
}