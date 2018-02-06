package _02_login.model;

import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;
import _00_init.util.GlobalService;
import _01_register.model.MemberBean;

public class LoginServiceImp implements LoginService {
	private DataSource ds = null;
	public LoginServiceImp() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(GlobalService.JNDI_DB_NAME);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	public MemberBean checkIDPassword(String usercellphone, String userpassword) {
		MemberBean mb = null;
		String sql = "SELECT * FROM Users m WHERE m.user_cellphone = ? and m.user_password = ?";
		try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setString(1, usercellphone);
			ps.setString(2, userpassword);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					mb = new MemberBean();
					mb.setUserid(rs.getInt("user_id"));
					mb.setUsercellphone(rs.getString("user_cellphone"));
					mb.setUserpassword(rs.getString("user_password"));
					mb.setUsername(rs.getString("user_name"));
					mb.setUseremail(rs.getString("user_email"));
					mb.setUseravater(rs.getString("user_avatar"));
					mb.setUsercreatetime(rs.getTimestamp("user_create_time"));
					mb.setUserstatus(rs.getString("user_status"));
					mb.setUseridentity(rs.getString("user_identity"));
					mb.setUserstore(rs.getInt("user_store"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mb;
	}
}
