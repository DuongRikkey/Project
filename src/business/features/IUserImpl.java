package business.features;

import business.entity.Users;

public interface IUserImpl extends IGenericDesign<Users,Integer>{
    Users login(Users user);
}
