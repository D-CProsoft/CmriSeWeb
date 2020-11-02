package com.cmrise.ejb.model.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AdmonExamenHdr implements Serializable {

	private static final long serialVersionUID = 1L;
	private long numero;
	private long actualizadoPor;
	private long creadoPor;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private String nombre; 
	private String tipoExamen; 
	private boolean aChange;
	private int idxTemp; 
	private List<AdmonExamenLine> examenLines; 
	
	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public long getActualizadoPor() {
		return this.actualizadoPor;
	}

	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}

	public long getCreadoPor() {
		return this.creadoPor;
	}

	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaEfectivaDesde() {
		return this.fechaEfectivaDesde;
	}

	public void setFechaEfectivaDesde(Date fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}

	public Date getFechaEfectivaHasta() {
		return this.fechaEfectivaHasta;
	}

	public void setFechaEfectivaHasta(Date fechaEfectivaHasta) {
		this.fechaEfectivaHasta = fechaEfectivaHasta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String pNombre) {
		if(null!=pNombre&&null!=getNombre()) {
			if(!pNombre.equals(getNombre())) {
				  this.setaChange(true);	
		  }	
		}
		this.nombre = pNombre;
	}

	public String getTipoExamen() {
		return tipoExamen;
	}

	public void setTipoExamen(String tipoExamen) {
		this.tipoExamen = tipoExamen;
	}

	public List<AdmonExamenLine> getExamenLines() {
		return examenLines;
	}

	public void setExamenLines(List<AdmonExamenLine> examenLines) {
		this.examenLines = examenLines;
	}

	public int getIdxTemp() {
		return idxTemp;
	}

	public void setIdxTemp(int idxTemp) {
		this.idxTemp = idxTemp;
	}

	public boolean isaChange() {
		return aChange;
	}

	public void setaChange(boolean aChange) {
		this.aChange = aChange;
	}
}