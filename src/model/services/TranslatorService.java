package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.TranslatorDao;
import model.entities.Translator;

public class TranslatorService {
	
	private TranslatorDao dao = DaoFactory.createTranslatorDao();
	
	public List<Translator> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Translator obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Translator obj) {
		dao.deleteById(obj.getId());
	}
	
}
