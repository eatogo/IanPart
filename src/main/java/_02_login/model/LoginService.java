package _02_login.model;

import _01_register.model.MemberBean;

public interface LoginService {
public MemberBean checkIDPassword(String userid ,String userpassword);
}
