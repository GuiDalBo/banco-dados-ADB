package model.services;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.TranslatorDao;
import model.entities.Translator;

public class TranslatorService {
	
	private TranslatorDao dao = DaoFactory.createTranslatorDao();
	
	public List<Translator> findAll() {
		return dao.findAll();
	}
	
}
