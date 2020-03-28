package com.cmrise.ejb.backing.exams.mrqs;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.model.exams.MrqsExamenes;
import com.cmrise.ejb.services.exams.MrqsExamenesLocal;
import com.cmrise.jpa.dto.exams.MrqsExamenesV1Dto;

@ManagedBean
@ViewScoped
public class ManageMrqsExamsForm {
	 private List<MrqsExamenes> listMrqsExamenes = new ArrayList<MrqsExamenes>();
	 private MrqsExamenes ccExamenesForAction = new MrqsExamenes();
	 
	 @Inject 
	 MrqsExamenesLocal ccExamenesLocal; 
	 
	 @PostConstruct
	 public void init() {
		System.out.println("Comienza ManageTestExamsForm init()");
		refreshEntity(); 
	    System.out.println("Finaliza ManageTestExamsForm init()");
	 }
	 
	private void refreshEntity() {
		List<MrqsExamenesV1Dto> listMrqsExamenesV1Dto = ccExamenesLocal.findAllWD(); 
	    System.out.println("listMrqsExamenesDto.size():"+listMrqsExamenesV1Dto.size());
	    listMrqsExamenes = new ArrayList<MrqsExamenes>();
	    for(MrqsExamenesV1Dto ccExamenesV1Dto: listMrqsExamenesV1Dto) {
	    	MrqsExamenes ccExamenes = new MrqsExamenes();
	    	System.out.println("ccExamenesDto.getNumero():"+ccExamenesV1Dto.getNumero());
	    	ccExamenes.setNumero(ccExamenesV1Dto.getNumero());
	    	ccExamenes.setTitulo(ccExamenesV1Dto.getTitulo());
	    	ccExamenes.setTipoPreguntaDesc(ccExamenesV1Dto.getTipoPreguntaDesc());
	    	ccExamenes.setTipoExamenDesc(ccExamenesV1Dto.getTipoExamenDesc());
	    	ccExamenes.setVisibilidad(ccExamenesV1Dto.getVisibilidad());
	    	ccExamenes.setVisibilidadDesc(ccExamenesV1Dto.getVisibilidadDesc());
	    	ccExamenes.setEstatus(ccExamenesV1Dto.getEstatus());
	    	ccExamenes.setEstatusDesc(ccExamenesV1Dto.getEstatusDesc());
	    	listMrqsExamenes.add(ccExamenes); 
	    }
	} 
	 
	public String update(MrqsExamenes pMrqsExamenes) {
	 System.out.println("Entra "+this.getClass()+" update()");	
	 FacesContext context = FacesContext.getCurrentInstance(); 
	 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	 session.setAttribute("NumeroCcExamenSV", pMrqsExamenes.getNumero());
	 System.out.println("Sale "+this.getClass()+" update()");	
	 return "Exams-CoreCases-Update"; 	
	}

	public void selectForAction(MrqsExamenes pMrqsExamenes) {
		System.out.println("Entra "+this.getClass()+" selectForAction()");	
		ccExamenesForAction.setNumero(pMrqsExamenes.getNumero());
		System.out.println("Sale "+this.getClass()+" selectForAction()");
	}

	public void delete() {
		System.out.println("Entra "+this.getClass()+" delete()");
		boolean deleteIn = false; 
		ccExamenesLocal.delete(ccExamenesForAction.getNumero());
		refreshEntity(); 
		deleteIn = true; 
		PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
		System.out.println("Sale "+this.getClass()+" delete()");	
	}

	public MrqsExamenes getMrqsExamenesForAction() {
		return ccExamenesForAction;
	}

	public void setMrqsExamenesForAction(MrqsExamenes ccExamenesForAction) {
		this.ccExamenesForAction = ccExamenesForAction;
	}

	public List<MrqsExamenes> getListMrqsExamenes() {
		return listMrqsExamenes;
	}

	public void setListMrqsExamenes(List<MrqsExamenes> listMrqsExamenes) {
		this.listMrqsExamenes = listMrqsExamenes;
	}
	 
}
