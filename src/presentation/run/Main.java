package presentation.run;

import business.constants.RoleName;
import business.entity.Users;
import business.features.IUserImpl;
import business.features.impl.UsersImpl;
import business.utils.IOFILE;
import business.utils.InputMethod;
import business.utils.ShopConstanst;
import presentation.admin.MenuAdmin;
import presentation.user.GuestView;
import presentation.user.MenuUser;

import java.util.Scanner;

public class Main {
    public static IUserImpl userimpl = new UsersImpl();
    public static void main(String[] args) {
        Main main = new Main();

        Scanner sc = new Scanner(System.in);
        do {
            final String PURPLE = "\033[35m";  // ANSI code for purple (border)
            final String YELLOW = "\033[33m";  // ANSI code for yellow (text)
            final String RESET = "\033[0m";    // ANSI reset to default color

            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                           ┃");
            System.out.println("┃" + YELLOW + "                          M A I N  M E N U                                 " + PURPLE + "┃");
            System.out.println("┃                                                                           ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                           ┃");
            System.out.println("┃" + YELLOW + "       1. Visit Shop                 " + PURPLE + "┃" + YELLOW + "        2. Login                     " + PURPLE + "┃");
            System.out.println("┃                                                                           ┃");
            System.out.println("┃━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┃━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┃");
            System.out.println("┃                                                                           ┃");
            System.out.println("┃" + YELLOW + "       3. Register                   " + PURPLE + "┃" + YELLOW + "       4. Forgot password            " + PURPLE + "┃");
            System.out.println("┃                                                                           ┃");
            System.out.println("┃━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┃");
            System.out.println("┃                                                                           ┃");
            System.out.println("┃" + YELLOW + "                                 5. Exit                                   " + PURPLE + "┃");
            System.out.println("┃                                                                           ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Lựa chọn của bạn: ");

            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1:
                    GuestView.showGuestView();
                    break;
                case 2: {
                    main.handleLogin(sc);
                    break;
                }
                case 3: {
                    main.handleRegister(sc);
                    break;
                }
                case 4: {
                    System.out.println("Chưa phát triển...");
                    break;
                }
                case 5: {
                    System.exit(0);
                    break;
                }
                default:
                    System.err.println("Vui lòng nhập lại từ 1 -> 5");
            }
        }
        while (true);
    }

    private void handleRegister(Scanner sc) {
        Users users = new Users();
        users.inputData(sc, true);
        userimpl.addAndUpdate(users);
    }

    private void handleLogin(Scanner sc) {
        Users users = new Users();
        users.inputLogin(sc);
        users = userimpl.login(users);
        if (users == null) {
            System.err.println("Tên đăng nhập hoặc mật khẩu sai");
            return; // Added to stop further execution if login fails
        }

        if (users.getRoleName().equals(RoleName.ROLE_ADMIN)) {
            MenuAdmin Admin = new MenuAdmin();
            Admin.menuAdmin(sc);
        } else {
            MenuUser User = new MenuUser();
            User.menuUser(sc);
        }


    }
}
