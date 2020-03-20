package com.cmrise.ejb.services.mrqs;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.mrqs.MrqsPreguntasFtaDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;

@Stateless 
public class MrqsPreguntasFtaLocalImpl implements MrqsPreguntasFtaLocal {

	@Inject
	MrqsPreguntasFtaDao mrqsPreguntasFtaDao;
	
	@Override
	public void insert(MrqsPreguntasFtaDto pMrqsPreguntasFtaDto) {
		mrqsPreguntasFtaDao.insert(pMrqsPreguntasFtaDto);
	}

	@Override
	public void delete(long pNumero) {
		mrqsPreguntasFtaDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, MrqsPreguntasFtaDto pMrqsPreguntasFtaDto) {
		mrqsPreguntasFtaDao.update(pNumero, pMrqsPreguntasFtaDto);
	}

	@Override
	public long findNumeroFtaByNumeroHdr(long pNumeroHdr) {
		return mrqsPreguntasFtaDao.findNumeroFtaByNumeroHdr(pNumeroHdr);
	}

	@Override
	public MrqsPreguntasFtaDto findDtoByNumeroFta(long pNumeroFta) {
		return mrqsPreguntasFtaDao.findDtoByNumeroFta(pNumeroFta);
	}

	@Override
	public long copyPaste(long pNumero, MrqsPreguntasHdrDto pMrqsPreguntasHdrDto) {
		return mrqsPreguntasFtaDao.copyPaste(pNumero, pMrqsPreguntasHdrDto);
	}

}
