package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.admin.AdmonUsuariosRolesDao;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class AdmonUsuariosRolesDaoImpl implements AdmonUsuariosRolesDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public void insert(AdmonUsuariosRolesDto pAdmonUsuariosRolesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.ADMON_USUARIOS_ROLES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pAdmonUsuariosRolesDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pAdmonUsuariosRolesDto.setCreadoPor((long)-1);
		pAdmonUsuariosRolesDto.setActualizadoPor((long)-1);
		pAdmonUsuariosRolesDto.setFechaCreacion(sqlsysdate);
		pAdmonUsuariosRolesDto.setFechaActualizacion(sqlsysdate);
		em.persist(pAdmonUsuariosRolesDto);
	}

	@Override
	public void delete(long pNumero) {
		AdmonUsuariosRolesDto admonUsuariosRolesDto = em.find(AdmonUsuariosRolesDto.class, pNumero);
		em.remove(admonUsuariosRolesDto);
	}

	@Override
	public void update(long pNumero, AdmonUsuariosRolesDto pAdmonUsuariosRolesDto) {
		AdmonUsuariosRolesDto admonUsuariosRolesDto = em.find(AdmonUsuariosRolesDto.class, pNumero);
		admonUsuariosRolesDto.setAdmonRole(pAdmonUsuariosRolesDto.getAdmonRole());
		admonUsuariosRolesDto.setAdmonUsuario(pAdmonUsuariosRolesDto.getAdmonUsuario());
		admonUsuariosRolesDto.setFechaEfectivaDesde(pAdmonUsuariosRolesDto.getFechaEfectivaDesde());
		admonUsuariosRolesDto.setFechaEfectivaHasta(pAdmonUsuariosRolesDto.getFechaEfectivaHasta());
	}

	@Override
	public List<AdmonUsuariosRolesV1Dto> findAll() {
		String strQuery = "SELECT a FROM AdmonUsuariosRolesV1Dto a"; 
		Query query = em.createQuery(strQuery);
		return query.getResultList();
	}

	@Override
	public int validaUsuarioRol(long pNumeroUsuario
			                   ,long pNumeroRol
			                   ) {
	    String strQuery ="SELECT COUNT(1)\r" + 
	          		     "  FROM [dbo].[ADMON_USUARIOS_ROLES]\r" + 
	    		         " WHERE [NUMERO_USUARIO] = "+pNumeroUsuario+"\r" + 
	    	 	         "  AND [NUMERO_ROL] ="+pNumeroRol ;
	    
	    Query query = em.createNativeQuery(strQuery); 
		Object object = query.getSingleResult(); 
		System.out.println(object);
		Integer integer = (Integer)query.getSingleResult(); 
		return integer.intValue();
	}

	@Override
	public int loginUsuarioRol(String pCurp
			                 , String pRol
			                 , String pContrasenia
			                 ) {
		   String strQuery ="SELECT COUNT(1)\r" + 
      		     "  FROM [dbo].[ADMON_USUARIOS_ROLES_V1]\r" + 
		         " WHERE [CURP] = '"+pCurp+"'\r" + 
	 	         "  AND [NOMBRE_ROL] ='"+pRol+"'\r" + 
	 	         "  AND [CONTRASENIA] ='"+pContrasenia+"'";

			Query query = em.createNativeQuery(strQuery); 
			Object object = query.getSingleResult(); 
			System.out.println(object);
			Integer integer = (Integer)query.getSingleResult(); 
			return integer.intValue();
	}

	@Override
	public List<Object> findWithFilterExam(long   pNumeroExamen ,String pTipoExamen ) {
		
		String strQuery =   "SELECT [AURV1].[NUMERO]\r" + 
							"      ,[AURV1].[NUMERO_USUARIO]\r" + 
							"      ,[AURV1].[MATRICULA]\r" + 
							"      ,[AURV1].[NOMBRE_USUARIO]\r" + 
							"      ,[AURV1].[NOMBRE_COMPLETO_USUARIO]\r" + 
							"      ,[AURV1].[NUMERO_ROL]\r" + 
							"      ,[AURV1].[NOMBRE_ROL]\r" + 
							"      ,[AURV1].[DESCRIPCION_ROL]\r" + 
							"      ,[AURV1].[APELLIDO_PATERNO]\r" + 
							"      ,[AURV1].[APELLIDO_MATERNO]\r" + 
							"      ,[AURV1].[CORREO_ELECTRONICO]\r" + 
							"      ,[AURV1].[CONTRASENIA]\r" + 
							"      ,[AURV1].[CURP]\r" + 
							"	   ,[ADU].ACTUALIZADO_POR\r" + 
							"      ,[ADU].FECHA_ACTUALIZACION\r" + 
							"	   ,(select au.NOMBRE + ' ' + au.APELLIDO_PATERNO + ' ' + au.APELLIDO_PATERNO from dbo.ADMON_USUARIOS au where au.numero = [ADU].[ACTUALIZADO_POR] AND au.numero <> -1) NOMBRE_ACT_POR \r" +
							"  FROM [dbo].[ADMON_USUARIOS_ROLES_V1] AURV1\r" + 
							"     , [dbo].[ADMON_USUARIOS] ADU\r"+
							" WHERE ADU.NUMERO = AURV1.NUMERO_USUARIO AND NOMBRE_ROL = '"+Utilitarios.ROL_ALUMNO+"'\r" + 
							"   AND [NUMERO_USUARIO] NOT IN ( SELECT NUMERO_USUARIO\r" + 
							"								    FROM CAND_EXAMENES\r" + 
							"								   WHERE NUMERO_EXAMEN = "+pNumeroExamen+"\r" + 
							"								     AND TIPO = '"+pTipoExamen+"' \r" + 
							"									)";
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public AdmonUsuariosRolesV1Dto findLoginUsusarioRol(String pCurp
			                                          , String pRol
			                                          , String pContrasenia
			                                          ) {
		String strQuery = "SELECT a FROM AdmonUsuariosRolesV1Dto a WHERE a.curp='"+pCurp+"' AND a.nombreRol='"+pRol+"' AND a.contrasenia='"+pContrasenia+"'"; 
		Query query = em.createQuery(strQuery); 
		return (AdmonUsuariosRolesV1Dto)query.getSingleResult();
	}
	
	public List<AdmonUsuariosRolesV1Dto> findCand() {
		String strQuery = "SELECT a FROM AdmonUsuariosRolesV1Dto a WHERE NUMERO_ROL='1'"; 
	Query query = em.createQuery(strQuery);
	return query.getResultList();
	}
	
	public List<AdmonUsuariosRolesV1Dto> findNotCand() {
		String strQuery = "SELECT a FROM AdmonUsuariosRolesV1Dto a WHERE NUMERO_ROL != '1'"; 
	Query query = em.createQuery(strQuery);
	return query.getResultList();
	}

	public List<Object> findCandidateNotExam(String cCurp, String cNombre, String c_aPaterno, String c_aMaterno, String actPor, String fechaActu, long   pNumeroExamen, String pTipoExamen){
		
		String strQuery =   "SELECT [AURV1].[NUMERO]\r" + 
				"      ,[AURV1].[NUMERO_USUARIO]\r" + 
				"      ,[AURV1].[MATRICULA]\r" + 
				"      ,[AURV1].[NOMBRE_USUARIO]\r" + 
				"      ,[AURV1].[NOMBRE_COMPLETO_USUARIO]\r" + 
				"      ,[AURV1].[NUMERO_ROL]\r" + 
				"      ,[AURV1].[NOMBRE_ROL]\r" + 
				"      ,[AURV1].[DESCRIPCION_ROL]\r" + 
				"      ,[AURV1].[APELLIDO_PATERNO]\r" + 
				"      ,[AURV1].[APELLIDO_MATERNO]\r" + 
				"      ,[AURV1].[CORREO_ELECTRONICO]\r" + 
				"      ,[AURV1].[CONTRASENIA]\r" + 
				"      ,[AURV1].[CURP]\r" + 
				"	   ,[ADU].[ACTUALIZADO_POR]\r" + 
				"      ,[ADU].[FECHA_ACTUALIZACION]\r" + 
				"	   ,(select au.NOMBRE + ' ' + au.APELLIDO_PATERNO + ' ' + au.APELLIDO_PATERNO from dbo.ADMON_USUARIOS au where au.numero = [ADU].[ACTUALIZADO_POR] AND au.numero <> -1) NOMBRE_ACT_POR \r" +
				"  FROM [dbo].[ADMON_USUARIOS_ROLES_V1] AURV1\r" + 
				"     , [dbo].[ADMON_USUARIOS] ADU\r"+
				" WHERE ADU.NUMERO = AURV1.NUMERO_USUARIO AND NOMBRE_ROL = '"+ Utilitarios.ROL_ALUMNO +"'\r" + 
				"   AND [AURV1].[CURP] LIKE '%"+ cCurp.trim() +"%' AND [AURV1].[NOMBRE_USUARIO] LIKE '%"+ cNombre.trim() +"%'" + 
				"   AND [AURV1].[APELLIDO_PATERNO] LIKE '%"+ c_aPaterno.trim() +"%' AND [AURV1].[APELLIDO_MATERNO] LIKE '%"+ c_aMaterno.trim() +"%'" ;
				
				if(actPor.length()!= 0)
					strQuery = strQuery +"   AND [ADU].[NOMBRE_ACT_POR] LIKE '%"+ actPor.trim() + "%'\r";
				
				if(fechaActu.length() != 0)
					strQuery = strQuery + " AND convert(varchar(25),[ADU].[FECHA_ACTUALIZACION],126) LIKE '%"+ fechaActu.trim() +"%'\r" ;
				
				strQuery = strQuery +"   AND [NUMERO_USUARIO] NOT IN ( SELECT NUMERO_USUARIO\r" + 
				"								    FROM CAND_EXAMENES\r" + 
				"								   WHERE NUMERO_EXAMEN = "+pNumeroExamen+"\r" + 
				"								     AND TIPO = '"+pTipoExamen+"' \r" + 
				"									)";
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}
}
