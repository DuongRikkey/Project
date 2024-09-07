package business.features.impl;

import business.constants.RoleName;
import business.entity.Users;
import business.features.IUserImpl;
import business.utils.IOFILE;
import business.utils.InputMethod;
import business.utils.ShopConstanst;

import java.util.*;

public class UsersImpl implements IUserImpl {
    public static List<Users> usersList= IOFILE.getListFromFile(ShopConstanst.USER_PATH);

    static {
        usersList.add(new Users(1, "duongadmin", "Admin User", "admin@example.com", "Admin123", false, "+84000000000", "Admin Address", new Date(), new Date(), RoleName.ROLE_ADMIN, false,1000));
    }

    @Override
    public Users login(Users user) {
        return usersList.stream().
                filter(u->u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())).
                findFirst().
                orElse(null);
    }

    @Override
    public void addAndUpdate(Users users) {
        int indexCheck=findIndexbyID(users.getId());
        if(indexCheck==-1){
            users.setId(getNewId());
            usersList.add(users);
        }else {
            usersList.set(indexCheck, users);
        }

        IOFILE.writeListToFile(usersList,ShopConstanst.USER_PATH);
    }

    @Override
    public void remove(Integer id) {
        usersList.remove(findIndexbyID(id));
        IOFILE.writeListToFile(usersList,ShopConstanst.USER_PATH);
    }

    @Override
    public int findIndexbyID(Integer id) {
        return usersList.stream().map(Users::getId).toList().indexOf(id);
    }


    @Override
    public Integer getNewId() {
        Optional<Users> optionalUsers=usersList.stream().max(Comparator.comparingInt(Users::getId));
        if(optionalUsers.isPresent()){
            return optionalUsers.get().getId()+1;
        }
        return 1;
    }

    }

