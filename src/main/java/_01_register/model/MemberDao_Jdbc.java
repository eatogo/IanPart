package _01_register.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import _00_init.util.GlobalService;

public class MemberDao_Jdbc implements MemberDao {
	private DataSource ds=null;
	public MemberDao_Jdbc() {
		try {
			Context ctx=new InitialContext();
			ds=(DataSource)ctx.lookup(GlobalService.JNDI_DB_NAME);
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	@Override
	public boolean checkphone(String usercellphone) {
		boolean idexist=false;
		String sql= "SELECT * FROM users WHERE user_cellphone=?";
		try(
				Connection connection=ds.getConnection();
				PreparedStatement ps=connection.prepareStatement(sql);){
				ps.setString(1, usercellphone);
				try(ResultSet rs=ps.executeQuery();){
					if(rs.next()) {
						idexist=true;
					}
				}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return idexist;
	}

	@Override
	public int saveUser(MemberBean mb) {
		String sql=" insert into Users "+
	"(user_password,user_cellphone,user_name,"
			+ "user_email,user_avatar,user_create_time,"
			+ "user_status,user_identity,user_store)"
	+" values (?,?,?,?,?,?,?,?,?)";
		int n=0;
		try(
				Connection connection=ds.getConnection();
				PreparedStatement ps=connection.prepareStatement(sql);){
			String encrypedString = GlobalService.encryptString(mb.getUserpassword());
		ps.setString(1,GlobalService.getMD5Endocing(encrypedString));
		ps.setString(2,mb.getUsercellphone());
		ps.setString(3,mb.getUsername());
		ps.setString(4,mb.getUseremail());
		ps.setString(5,mb.getUseravater());
		java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
		ps.setTimestamp(6,now);
		ps.setString(7,mb.getUserstatus());
		ps.setString(8,mb.getUseridentity());
		ps.setInt(9, mb.getUserstore());
	
		
		n = ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return n;
	}

	@Override
	public MemberBean queryUser(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

}
