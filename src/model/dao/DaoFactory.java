package model.dao;

import db.DB;
import model.dao.impl.TranslatorDaoJDBC;

public class DaoFactory {

	public static TranslatorDao createTranslatorDao() {
		return new TranslatorDaoJDBC(DB.getConnection());
	}
}
