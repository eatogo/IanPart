package _01_register.model;

public interface MemberDao {
public boolean checkphone(String usercellphone);
public int saveUser(MemberBean mb);
public MemberBean queryUser(String userid);

}
