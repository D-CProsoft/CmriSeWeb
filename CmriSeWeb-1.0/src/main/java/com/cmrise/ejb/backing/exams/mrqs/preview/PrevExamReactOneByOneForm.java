package com.cmrise.ejb.backing.exams.mrqs.preview;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.exams.MrqsExamenes;
import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.ejb.model.exams.MrqsGrupoLines;
import com.cmrise.ejb.model.exams.Reactivo;
import com.cmrise.ejb.model.mrqs.AnotacionesCorImg;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasFtaV1;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.model.mrqs.RespReactCorImg;
import com.cmrise.ejb.services.exams.MrqsExamenesLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoHdrLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoLinesLocal;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@ManagedBean
@ViewScoped
public class PrevExamReactOneByOneForm {
private MrqsExamenes mrqsExamen = new MrqsExamenes(); 
	
	@Inject
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	MrqsExamenesLocal mrqsExamenesLocal; 
	
	@Inject 
	MrqsGrupoHdrLocal mrqsGrupoHdrLocal; 
	
	@Inject 
	MrqsGrupoLinesLocal mrqsGrupoLinesLocal; 
	
	@Inject 
	MrqsOpcionMultipleLocal mrqsOpcionMultipleLocal; 
	
	@Inject 
	MrqsPreguntasFtaLocal mrqsPreguntasFtaLocal; 
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 
	
	private Date fechaActual  = new Date(); 
	
	/*******************************************************************/
	private List<MrqsGrupoHdr> listMrqsGrupoHdr = new ArrayList<MrqsGrupoHdr>(); 
	private MrqsGrupoHdr mrqsGrupoHdrForRead = new MrqsGrupoHdr(); 
	private boolean flag1 = true; 
	private boolean flag2 = true; 
	private boolean flag3 = true; 
	private List<MrqsGrupoLines> listMrqsGrupoLinesForRead = new ArrayList<MrqsGrupoLines>(); 
	private MrqsGrupoLines mrqsGrupoLinesForRead = new MrqsGrupoLines(); 
	private MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForRead = new MrqsPreguntasHdrV1(); 
	/*******************************************************************/

	private List<Reactivo> reactivos = new ArrayList<Reactivo>();  
	private int idxReactivos = 0; 
	private int reactivosSize = 0;
	private Reactivo reactivoForRead = new Reactivo(); 
	private Boolean indicateImage = false;
	private MrqsPreguntasFtaV1 mrqsPreguntasFtaV1 = new MrqsPreguntasFtaV1();
	private List<AnotacionesCorImg> listAnotacionesCorImg = new ArrayList<AnotacionesCorImg>();
	private List<RespReactCorImg> listRespReactCorImg = new ArrayList<RespReactCorImg>(); 
	private List<SelectItem> selectRespReactCorImg = new ArrayList<SelectItem>(); 

	
	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 Object objNumeroMrqsExamenSV = session.getAttribute("NumeroMrqsExamenSV"); 
	     long numeroMrqsExamenSV = Utilitarios.objToLong(objNumeroMrqsExamenSV); 
	     System.out.println("numeroMrqsExamenSV:"+numeroMrqsExamenSV);
	     if(null==mrqsExamen) {
	    	  mrqsExamen = mrqsExamenesLocal.findObjMod(numeroMrqsExamenSV); 
	   	 }else {
	   		 if(mrqsExamen.getNumero()==0) {
	   			mrqsExamen = mrqsExamenesLocal.findObjMod(numeroMrqsExamenSV); 
	   		 }
	   	 }
	     
	     if(null!=mrqsExamen) {
	       if(mrqsExamen.getNumero()!=0) {
	    	   
	    	   listMrqsGrupoHdr = mrqsExamen.getListMrqsGrupoHdr(); 
	    	   reactivos = new ArrayList<Reactivo>(); 
	    	   
	    	   reactivoForRead = new Reactivo(); 
	    	   reactivoForRead.setNumero(1);
	    	   reactivoForRead.setMateriaIdx(0);
	    	   reactivoForRead.setPreguntaIdx(0);
	    	   
	    	  int contador = 0; 
		      for(MrqsGrupoHdr i:listMrqsGrupoHdr) {
	    		   listMrqsGrupoLinesForRead = i.getListMrqsGrupoLines();
   				   for(MrqsGrupoLines j:listMrqsGrupoLinesForRead) {
	    			   contador = contador+1; 
	    			   Reactivo reactivo = new Reactivo(); 
	    			   reactivo.setNumero(contador);
	    			   reactivo.setMateriaIdx(listMrqsGrupoHdr.indexOf(i));
	    			   reactivo.setPreguntaIdx(listMrqsGrupoLinesForRead.indexOf(j));
	    			   reactivos.add(reactivo); 
	    			   if(0==listMrqsGrupoHdr.indexOf(i)
	    				&&0==listMrqsGrupoLinesForRead.indexOf(j)) {
	    				   mrqsGrupoHdrForRead = i; 
	    				   mrqsGrupoLinesForRead = j;  
	    				   mrqsPreguntasHdrV1ForRead = mrqsGrupoLinesForRead.getMrqsPreguntasHdrV1();
	    				   flag2 = false; 
							if (Utilitarios.IMAGEN_ANOTADA.equals(mrqsPreguntasHdrV1ForRead.getTipoPregunta())){																				
								isImage();
							}
	    				   else {
	    					   indicateImage = false;	
	    				   }
	    			   }
	    		   }
	    	   }
	    	  
		     
		      
	    	}
	     } /** END if(null!=mrqsExamen) { **/
	}

	public String backExamenesReactivos() {
		return "Exams-MRQs-Update"; 
	}
	
	private void isImage() {
		Gson gson = new Gson();
		System.out.println(mrqsPreguntasHdrV1ForRead.getNumero());
		long ftaNumero = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsPreguntasHdrV1ForRead.getNumero());
		mrqsPreguntasFtaV1 = mrqsPreguntasFtaLocal.findObjModByNumeroFta(ftaNumero,mrqsPreguntasHdrV1ForRead.getTipoPregunta());
		
		if(null!=mrqsPreguntasFtaV1.getRespuestas()) {
        	Type collectionType = new TypeToken<List<RespReactCorImg>>(){}.getType();
        	listRespReactCorImg = gson.fromJson(mrqsPreguntasFtaV1.getRespuestas(), collectionType); 
        	refreshRespuestas();
         }
         if(null!=mrqsPreguntasFtaV1.getAnotaciones()) {
        	 Type collectionType = new TypeToken<List<AnotacionesCorImg>>(){}.getType();
        	 listAnotacionesCorImg = gson.fromJson(mrqsPreguntasFtaV1.getAnotaciones(), collectionType); 
         }
		indicateImage = true;
	}
	
	private void refreshRespuestas() {
		selectRespReactCorImg = new ArrayList<SelectItem>(); 
		for(RespReactCorImg i:listRespReactCorImg) {
			SelectItem selectItem = new SelectItem(i.getNumero(),i.getRespuesta()); 
			selectRespReactCorImg.add(selectItem); 
		}
	}

	
	public void regresar() {
		idxReactivos = idxReactivos - 1;
		reactivosSize = reactivos.size() - 1;
		if (idxReactivos < 0) {
			flag1 = true;
			return;
		} else if (0 == idxReactivos) {
			flag1 = true;
		}
		flag2 = false;
		reactivoForRead = reactivos.get(idxReactivos);
		mrqsGrupoHdrForRead = listMrqsGrupoHdr.get(reactivoForRead.getMateriaIdx());
		mrqsGrupoLinesForRead = mrqsGrupoHdrForRead.getListMrqsGrupoLines().get(reactivoForRead.getPreguntaIdx());
		mrqsPreguntasHdrV1ForRead = mrqsGrupoLinesForRead.getMrqsPreguntasHdrV1();
		if (Utilitarios.IMAGEN_ANOTADA.equals(mrqsPreguntasHdrV1ForRead.getTipoPregunta())) {
			 isImage();
		} else {
			indicateImage = false;
		}

	}
	
	public String continuar() {
		idxReactivos = idxReactivos + 1;
		System.out.println("idxReactivos:" + idxReactivos);
		reactivosSize = reactivos.size() - 1;
		if (idxReactivos > reactivosSize) {
			flag2 = true;
			return "";
		} else if (idxReactivos == reactivosSize) {
			flag2 = true;
		}
		flag1 = false;
		reactivoForRead = reactivos.get(idxReactivos);
		mrqsGrupoHdrForRead = listMrqsGrupoHdr.get(reactivoForRead.getMateriaIdx());
		mrqsGrupoLinesForRead = mrqsGrupoHdrForRead.getListMrqsGrupoLines().get(reactivoForRead.getPreguntaIdx());
		mrqsPreguntasHdrV1ForRead = mrqsGrupoLinesForRead.getMrqsPreguntasHdrV1();
		if (Utilitarios.IMAGEN_ANOTADA.equals(mrqsPreguntasHdrV1ForRead.getTipoPregunta())) {
			 isImage();
		} else {
			indicateImage = false;
		}
		return "Prev-Exam-React-OneByOne";

	}
	
	public void finalizarExamen() {
		
	}
	
	public MrqsExamenes getMrqsExamen() {
		return mrqsExamen;
	}

	public void setMrqsExamen(MrqsExamenes mrqsExamen) {
		this.mrqsExamen = mrqsExamen;
	}
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public List<MrqsGrupoHdr> getListMrqsGrupoHdr() {
		return listMrqsGrupoHdr;
	}

	public void setListMrqsGrupoHdr(List<MrqsGrupoHdr> listMrqsGrupoHdr) {
		this.listMrqsGrupoHdr = listMrqsGrupoHdr;
	}

	public MrqsGrupoHdr getMrqsGrupoHdrForRead() {
		return mrqsGrupoHdrForRead;
	}

	public void setMrqsGrupoHdrForRead(MrqsGrupoHdr mrqsGrupoHdrForRead) {
		this.mrqsGrupoHdrForRead = mrqsGrupoHdrForRead;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public boolean isFlag3() {
		return flag3;
	}

	public void setFlag3(boolean flag3) {
		this.flag3 = flag3;
	}

	public List<MrqsGrupoLines> getListMrqsGrupoLinesForRead() {
		return listMrqsGrupoLinesForRead;
	}

	public void setListMrqsGrupoLinesForRead(List<MrqsGrupoLines> listMrqsGrupoLinesForRead) {
		this.listMrqsGrupoLinesForRead = listMrqsGrupoLinesForRead;
	}

	public MrqsGrupoLines getMrqsGrupoLinesForRead() {
		return mrqsGrupoLinesForRead;
	}

	public void setMrqsGrupoLinesForRead(MrqsGrupoLines mrqsGrupoLinesForRead) {
		this.mrqsGrupoLinesForRead = mrqsGrupoLinesForRead;
	}

	public MrqsPreguntasHdrV1 getMrqsPreguntasHdrV1ForRead() {
		return mrqsPreguntasHdrV1ForRead;
	}

	public void setMrqsPreguntasHdrV1ForRead(MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForRead) {
		this.mrqsPreguntasHdrV1ForRead = mrqsPreguntasHdrV1ForRead;
	}

	public Reactivo getReactivoForRead() {
		return reactivoForRead;
	}

	public void setReactivoForRead(Reactivo reactivoForRead) {
		this.reactivoForRead = reactivoForRead;
	}

	public Boolean getIndicateImage() {
		return indicateImage;
	}

	public void setIndicateImage(Boolean indicateImage) {
		this.indicateImage = indicateImage;
	}

	public MrqsPreguntasFtaV1 getMrqsPreguntasFtaV1() {
		return mrqsPreguntasFtaV1;
	}

	public void setMrqsPreguntasFtaV1(MrqsPreguntasFtaV1 mrqsPreguntasFtaV1) {
		this.mrqsPreguntasFtaV1 = mrqsPreguntasFtaV1;
	}

	public List<AnotacionesCorImg> getListAnotacionesCorImg() {
		return listAnotacionesCorImg;
	}

	public void setListAnotacionesCorImg(List<AnotacionesCorImg> listAnotacionesCorImg) {
		this.listAnotacionesCorImg = listAnotacionesCorImg;
	}

	public List<RespReactCorImg> getListRespReactCorImg() {
		return listRespReactCorImg;
	}

	public void setListRespReactCorImg(List<RespReactCorImg> listRespReactCorImg) {
		this.listRespReactCorImg = listRespReactCorImg;
	}

	public List<SelectItem> getSelectRespReactCorImg() {
		return selectRespReactCorImg;
	}

	public void setSelectRespReactCorImg(List<SelectItem> selectRespReactCorImg) {
		this.selectRespReactCorImg = selectRespReactCorImg;
	}

	

}
