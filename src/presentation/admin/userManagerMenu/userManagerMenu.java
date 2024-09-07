package presentation.admin.userManagerMenu;

import business.constants.RoleName;
import business.entity.Users;
import business.features.IGenericDesign;
import business.features.impl.UsersImpl;
import business.utils.Colors;
import business.utils.InputMethod;
import presentation.run.Main;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class userManagerMenu {
    IGenericDesign usersImpl =new UsersImpl();
    public void showUserMenu(Scanner scanner) {
        boolean flag = true;
        do {

            // Define ANSI escape codes for colors
            final String PURPLE = "\033[35m";  // Purple for borders
            final String YELLOW = "\033[33m";  // Yellow for text
            final String BLUE = "\033[34m";    // Blue for highlights
            final String GREEN = "\033[32m";   // Green for special text
            final String RESET = "\033[0m";    // Reset to default color




            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                          USER MANAGER MENU                            " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   1. Hiển thị toàn bộ người dùng                      " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   2. Chỉnh sửa người dùng                             " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   3. Xóa người dùng                                   " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   4. Tìm kiếm người dùng theo tên                     " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   5. Hiển thị Role                                    " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   6. Trở lại                                          " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Enter Your Choice: ");



            int choice = InputMethod.getInteger();
            switch(choice) {
                case 1:{
                    showAllUser();
                    break;
                }
                case 2:{
                    editUser(scanner);
                    break;
                }
                case 3:{
                    deleteUser(scanner);
                    break;
                }
                case 4:{
                    searchUser(scanner);
                    break;
                }
                case 5:{
                    showRole(scanner);
                    break;
                }
                case 6:{
                    flag = false;
                    break;
                }
                default:
                    System.err.println("Lựa chọn 1 -> 6");
            }

        }while(flag);

    }

    private void showRole(Scanner scanner) {
        RoleName[] roless=RoleName.values();
        int count=1;
        System.out.println("***************LIST ROLES***************");
        for(RoleName role:roless) {
            System.out.println(count+"."+role);
            count++;
        }
        System.out.println("Có "+(count-1)+" roles");
    }

    private void searchUser(Scanner scanner) {
        System.out.println("Mời bạn nhập idUser cần tìm ");
        int idSearch = InputMethod.getInteger();
        List<Users> matchingUser=UsersImpl.usersList.stream().filter(users -> users.getId()==idSearch).toList();
        if(matchingUser.isEmpty()) {
            System.err.println("Danh sách trống không thể tìm");
        }else {
            matchingUser.forEach(Users::displayData);
        }

    }

    private void deleteUser(Scanner scanner) {
        int idDelete ;
        System.out.println("Mời bạn nhập ID người dùng muốn xóa");
        while (true){
            try {
                idDelete = Integer.parseInt(scanner.nextLine());
                if (idDelete <=0) {
                    System.err.println("Bạn phải nhập Id lớn hơn không");
                    continue;
                }
                break;

            }catch (NumberFormatException e) {
                System.err.println("Bạn phải nhập số nguyên");
            }
        }
        int finalidDelete = idDelete;
        boolean isExit = UsersImpl.usersList.stream().anyMatch(user -> user.getId() == finalidDelete);
        if (!isExit) {
            System.err.println("Không tìm thấy id cần xóa"+idDelete);
        }else {
            usersImpl.remove(finalidDelete);
            System.out.println("Xóa thành công");
        }
    }

    private void editUser(Scanner scanner) {
        int idUpdateUser;
        while (true) {
            try {
                System.out.println("Mời bạn nhập ID muốn Update");
                idUpdateUser = Integer.parseInt(scanner.nextLine());
                if (idUpdateUser <= 0) {
                    System.err.println("Bạn phải nhập Id lớn hơn không");
                    continue;
                }
                break;
            }catch (NumberFormatException e) {
                System.err.println("ID phải là số nguyên");
            }
        }
        int userIndex=usersImpl.findIndexbyID(idUpdateUser);
        if (userIndex==-1) {
            System.err.println("Không tìm thấy ID cần update"+userIndex);
        }
        else {
            Users oldUser = UsersImpl.usersList.get(userIndex);
            if (oldUser != null) {
                // Check if the user is an admin and the logged-in user is not allowed to modify admins
                if (oldUser.getRoleName() == RoleName.ROLE_ADMIN) {
                    System.err.println("Đây là Admin, bạn không có quyền chỉnh sửa");
                } else {
                    // Input new data for the user
                    oldUser.inputData(scanner, false);

                    // Update the user in the list
                    usersImpl.addAndUpdate(oldUser);
                    System.out.println("Cập nhật thông tin người dùng thành công.");
                }
            } else {
                System.err.println("Không thể tìm thấy người dùng có ID: " + idUpdateUser);
            }
        }

    }


    private void showAllUser() {
        int USERS_PER_PAGE = 3;
        int totalUsers = UsersImpl.usersList.size();
        if (UsersImpl.usersList.isEmpty()){
            System.err.println("Danh sách trống");
            return;
        }
        int totalPages = (int) Math.ceil((double) totalUsers / USERS_PER_PAGE); // Tổng số trang
        int currentPage = 1;
        while (true) {
            System.out.println("Trang " + currentPage + " / " + totalPages);
            int start = (currentPage - 1) * USERS_PER_PAGE;
            int end = Math.min(start + USERS_PER_PAGE, totalUsers);
            System.out.println(Colors.PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + Colors.YELLOW + "                          USER MANAGER MENU                            " +Colors.PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            for (int i = start; i < end; i++) {
                UsersImpl.usersList.get(i).displayData();
            }
            System.out.println("Nhập lệnh:");
            System.out.println(Colors.PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┃                           ┃                             ┃" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┃        1. Trang trước     ┃          2. Trang sau       ┃" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┃                           ┃                             ┃" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);

            System.out.println(Colors.PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┃                           ┃                             ┃" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┃        3. Tìm kiếm trang  ┃           4. Thoát          ┃" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┃                           ┃                             ┃" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);

            System.out.println("Mời bạn lựa chọn");


            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1:
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("Đây là trang đầu tiên.");
                    }
                    break;

                case 2:

                if (currentPage < totalPages) {
                    currentPage++;
                } else {
                    System.out.println("Đây là trang cuối cùng.");
                }
                break;
                case 3:
                    System.out.println("Nhập số trang bạn muốn đến:");
                    int inputPage = InputMethod.getInteger();
                    if (inputPage >= 1 && inputPage <= totalPages) {
                        currentPage = inputPage;
                    } else {
                        System.out.println("Số trang không hợp lệ. Vui lòng nhập lại.");
                    }
                    break;
                case 4:
                    return; // Thoát khỏi phân trang
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }

        }
    }
}
