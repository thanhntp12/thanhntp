package asm1;
import java.security.SecureRandom;
import java.util.Scanner;

public class Asm1 {
    // Khai báo hằng số
    private static final String AUTHOR = "NGAN HANG SO";
    private static final String VERSION = "FX29867@v2023.3.4";

    public static void main(String[] args) {
        // Hiển thị tiêu đề
        displayHeader();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Chuc nang: ");
            int n = getInput(sc);
            if (n == 1) {
                inputCCCD(sc);
            } else if (n == 0) {
                break;
            } else {
                System.out.println("Vui long nhap lai.");
            }
        }

        sc.close();
    }

    // Hàm hiển thị tiêu đề
    private static void displayHeader() {
        System.out.println("+-----------+------------------------+-----------+");
        System.out.println("| " + AUTHOR + " | " + VERSION + "               |");
        System.out.println("+-----------+------------------------+-----------+");
        System.out.println("| 1. NHAP CCCD                                   |");
        System.out.println("| 0. THOAT                                       |");
        System.out.println("+-----------+------------------------+-----------+");
    }



    // Hàm nhập CCCD
    private static void inputCCCD(Scanner sc) {
        while (true) {
            System.out.println("1. Hard");
            System.out.println("2. Easy");
            System.out.print("Chon ma xac thuc: ");
            int option = getInput(sc);
            sc.nextLine(); // Loại bỏ ký tự thừa

            String maXacThuc = "";
            if (option == 1) {
                maXacThuc = generateRandom(6);
            } else if (option == 2) {
                maXacThuc = String.valueOf(generateRandomNumberInRange(100,999));
            } else {
                System.out.println("Chuc nang khong hop le. Vui long nhap lai.");
                continue; // Tiếp tục vòng lặp
            }

            while (true) {
                System.out.println("Ma xac thuc: " + maXacThuc);
                System.out.print("Nhap lai ma xac thuc: ");
                String nhapLaiMa = sc.nextLine();

                if (nhapLaiMa.equals(maXacThuc)) {
                    displayCCCDInfo(sc);
                    break;
                } else {
                    System.out.println("Nhap sai ma xac thuc. Vui long thu lai.");
                }
            }
            break;
        }
    }
    // Hàm để nhận giá trị nhập từ bàn phím và xử lý lỗi
    private static int getInput(Scanner sc) {
        while (true) {
            try {
                return sc.nextInt();
            } catch (Exception e) {
                System.out.println("Vui long nhap mot so.");
                sc.nextLine(); // Xóa bỏ dữ liệu không hợp lệ
            }
        }
    }
    // Hàm tạo chuỗi ngẫu nhiên có ký tự hợp lệ
    private static String generateRandom(int length) {
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(length);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
    // Hàm tạo số ngẫu nhiên trong khoảng cho trước
    private static int generateRandomNumberInRange(int min, int max) {
        SecureRandom random = new SecureRandom();
        return random.nextInt(max - min + 1) + min;
    }

    // Hàm hiển thị thông tin CCCD
    private static void displayCCCDInfo(Scanner sc) {
        System.out.print("Nhap so CCCD: ");
        String cccd = sc.nextLine();
        if (cccd.length() != 12) {
            System.out.println("So CCCD khong chinh xac, vui long nhap lai.");
            return;
        }

        while (true) {
            System.out.println("    1. Kiem tra noi sinh ");
            System.out.println("    2. Kiem tra nam sinh ");
            System.out.println("    3. Kiem tra so ngau nhien ");
            System.out.println("    0. Thoat ");
            System.out.print("Chon chuc nang: ");
            int chucNang = sc.nextInt();
            sc.nextLine(); // Loại bỏ dấu xuống dòng
            if (chucNang == 1) {
                displayBirthplaceInfo(cccd);
            } else if (chucNang == 2) {
                displayGenderAndDOBInfo(cccd);
            } else if (chucNang == 3) {
                displayRandomNumberInfo(cccd);
            } else if (chucNang == 0) {
                break;
            } else {
                System.out.println("Chi nhap cac so 0, 1, 2, 3 nhu da hien thi, vui long nhap lai");
            }
        }
    }

    // Hàm hiển thị thông tin nơi sinh
    private static void displayBirthplaceInfo(String cccd) {
        String maTinh = cccd.substring(0, 3);

        String[] maTinhTinh = {"001", "002", "004", "006", "008", "010", "011", "012", "014", "015", "017", "019", "020", "022", "024", "025", "026", "027", "030", "031", "033", "034", "035", "036", "037", "038", "040", "042", "044", "045", "046", "048", "049", "051", "052", "054", "056", "058", "060", "062", "064", "066", "067", "068", "070", "072", "074", "075", "077", "079", "080", "082", "083", "084", "086", "087", "089", "091", "092", "093", "094", "095", "096"};
        String[] tenTinh = {"Ha Noi", "Ha Giang", "Cao Bang", "Bac Kan", "Tuyen Quang", "Lao Cai", "Đien Bien", "Lai Chau", "Son La", "Yen Bai", "Hoa Binh", "Thai Nguyen", "Lang Son", "Quang Ninh", "Bac Giang", "Phu Tho", "Vinh Phuc", "Bac Ninh", "Hai Duong", "Hai Phong", "Hung Yen", "Thai Binh", "Ha Nam", "Nam Đinh", "Ninh Binh", "Thanh Hoa", "Nghe An", "Ha Tinh", "Quang Binh", "Quang Tri", "Thua Thien Hue", "Đa Nang", "Quang Nam", "Quang Ngai", "Binh Đinh", "Phu Yen", "Khanh Hoa", "Ninh Thuan", "Binh Thuan", "Kon Tum", "Gia Lai", "Đak Lak", "Đak Nong", "Lam Đong", "Binh Phuoc", "Tay Ninh", "Binh Duong", "Đong Nai", "Ba Ria - Vung Tau", "Ho Chi Minh", "Long An", "Tien Giang", "Ben Tre", "Tra Vinh", "Vinh Long", "Đong Thap", "An Giang", "Kien Giang", "Can Tho", "Hau Giang", "Soc Trang", "Bac Lieu", "Ca Mau"};

        int index = -1;
        for (int i = 0; i < maTinhTinh.length; i++) {
            if (maTinhTinh[i].equals(maTinh)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            System.out.println("Noi sinh: " + tenTinh[index]);
        } else {
            System.out.println("Ma tinh khong hop le.");
        }
    }

    // Hàm hiển thị thông tin giới tính và năm sinh
    private static void displayGenderAndDOBInfo(String cccd) {
        char gioiTinhChar = cccd.charAt(3);
        int gioiTinh = Character.getNumericValue(gioiTinhChar);

        String naNamSinh = cccd.substring(4, 6);

        int theKy = -1;
        switch (gioiTinh) {
            case 0:
            case 1:
                theKy = 20;
                break;
            case 2:
            case 3:
                theKy = 21;
                break;
            case 4:
            case 5:
                theKy = 22;
                break;
            case 6:
            case 7:
                theKy = 23;
                break;
            case 8:
            case 9:
                theKy = 24;
                break;
            default:
                System.out.println("Ma gioi tinh khong hop le");
        }

        int namSinh = Integer.parseInt(naNamSinh) + (theKy - 1) * 100;

        if (gioiTinh % 2 == 0) {
            System.out.println("Gioi tinh: Nam  | Nam sinh: " + namSinh);
        } else {
            System.out.println("Gioi tinh: Nu  | Nam sinh: " + namSinh);
        }
    }

    // Hàm hiển thị thông tin số ngẫu nhiên
    private static void displayRandomNumberInfo(String cccd) {
        String sauSoCuoi = cccd.substring(6);
        System.out.println("So ngau nhien: " + sauSoCuoi);
    }
}

