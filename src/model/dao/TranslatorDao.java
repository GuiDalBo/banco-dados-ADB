package model.dao;

import java.util.List;


import model.entities.Translator;

public interface TranslatorDao {

	void insert(Translator obj);
	void update(Translator obj);
	void deleteById(Integer id);
	Translator findById(Integer id);
	List<Translator> findAll();
}
