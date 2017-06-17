package com.irisdoc.dao;

import com.irisdoc.beans.Admin;

public interface AdminDAO {
	Admin findAdmin(String loginAdmin) throws DAOException;
	
	boolean checkPassword(String loginAdmin, String password) throws DAOException;
}
