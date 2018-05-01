package _01_register.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class MemberBean implements Serializable  {
	private static final long serialVersionUID = 1L;
Integer userid=null;	
String usercellphone=null;
String userpassword=null;
String username=null;
String useremail=null;
String useravater=null;
Timestamp usercreatetime=null;
String userstatus=null;
String useridentity=null;
Integer userstore=null;
public MemberBean() {
	super();
}


public MemberBean(Integer userid, String usercellphone, String userpassword, String username, String useremail,
		String useravater, Timestamp usercreatetime, String userstatus, String useridentity, Integer userstore) {
	super();
	this.userid = userid;
	this.usercellphone = usercellphone;
	this.userpassword = userpassword;
	this.username = username;
	this.useremail = useremail;
	this.useravater = useravater;
	this.usercreatetime = usercreatetime;
	this.userstatus = userstatus;
	this.useridentity = useridentity;
	this.userstore = userstore;
}
public Integer getUserid() {
	return userid;
}
public void setUserid(Integer userid) {
	this.userid = userid;
}
public String getUsercellphone() {
	return usercellphone;
}
public void setUsercellphone(String usercellphone) {
	this.usercellphone = usercellphone;
}
public String getUserpassword() {
	return userpassword;
}
public void setUserpassword(String userpassword) {
	this.userpassword = userpassword;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getUseremail() {
	return useremail;
}
public void setUseremail(String useremail) {
	this.useremail = useremail;
}
public String getUseravater() {
	return useravater;
}
public void setUseravater(String useravater) {
	this.useravater = useravater;
}
public Timestamp getUsercreatetime() {
	return usercreatetime;
}
public void setUsercreatetime(Timestamp usercreatetime) {
	this.usercreatetime = usercreatetime;
}
public String getUserstatus() {
	return userstatus;
}
public void setUserstatus(String userstatus) {
	this.userstatus = userstatus;
}
public String getUseridentity() {
	return useridentity;
}
public void setUseridentity(String useridentity) {
	this.useridentity = useridentity;
}
public Integer getUserstore() {
	return userstore;
}
public void setUserstore(Integer userstore) {
	this.userstore = userstore;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}


}