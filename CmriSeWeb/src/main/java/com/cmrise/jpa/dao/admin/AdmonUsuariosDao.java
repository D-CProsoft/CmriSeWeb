package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.jpa.dto.admin.KeysDto;

public interface AdmonUsuariosDao {

	public void insert(AdmonUsuariosDto pAdmonUsuariosDto);
	public List<AdmonUsuariosDto> findTop500();
	public void delete(long pNumero);
	public void update(long pNumero,AdmonUsuariosDto pAdmonUsuariosDto);
	public List<KeysDto> findKeys();
	
}
