/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.rest;

import br.com.projeto.agenda.model.Classe;
import br.com.projeto.agenda.rest.util.AppException;
import br.com.projeto.agenda.rest.util.RaizJson;
import br.com.projeto.agenda.service.ClasseService;
import br.com.projeto.agenda.util.app.NegocioException;
import br.com.projeto.agenda.util.app.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Victor
 */
@Api(value = "Classes WS")
@Path("/classes")
public class ClasseWS {

    @Inject
    private ClasseService classeService;

    private Classe classe;

    private List<Classe> listaClasse;

    @ApiOperation(value = "Buscar classe por ID", response = Classe.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/buscarClasse/{id}")
    public Response buscarClasse(@PathParam("id") Integer id) throws AppException {

        try {

            classe = new Classe();

            if (id == 0 || id == null) {
                AppException appException = new AppException("Classe não encontrada", 404);
                return Response.ok().entity(new RaizJson(appException)).build();
            } else {

                this.classe = classeService.buscaPorId(id, false);

                if (classe == null) {
                    AppException appException = new AppException("Classe não encontrada", 404);
                    return Response.ok().entity(new RaizJson(appException)).build();
                }
            }

            //Retorna 200, com dto no corpo
            return Response.ok().entity(new RaizJson(classe)).build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(e.getMessage(), 501);
        }
    }

    @ApiOperation(value = "Buscar todas as classes ", response = Classe.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/buscarTodasClasses")
    public Response buscarTodasClasses() throws AppException {

        listaClasse = new ArrayList<>();

        listaClasse = classeService.buscaTodos();

        //Retorna 200, com dto no corpo
        return Response.ok().entity(new RaizJson(listaClasse)).build();
    }

    @ApiOperation(value = "Cadastra classe", response = Classe.class)
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cadastraClasse")
    public Response cadastraClasse(Classe classeParametro) throws AppException, NegocioException {

        if (Util.isNotBlank(classeParametro.getDescricao())) {

            if (!classeService.verificaUsoDescricao(classeParametro.getDescricao())) {
                AppException appException = new AppException("Já existe uma classe com essa descrição", 406);
                return Response.ok().entity(new RaizJson(appException)).build();
            } else {
                classe = new Classe();
                classe.setId(0);
                classe.setDescricao(classeParametro.getDescricao());

                classe = classeService.salvar(classe);
            }

        } else {
            AppException appException = new AppException("Descrição não pode ser nula", 406);
            return Response.ok().entity(new RaizJson(appException)).build();
        }

        //Retorna 200, com dto no corpo
        return Response.ok().entity(new RaizJson(classe)).build();

    }

    @ApiOperation(value = "Alterar classe", response = Classe.class)
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/alterarClasse")
    public Response alteraClasse(Classe classeParametro) throws AppException, NegocioException {

        if (!Util.isNotBlank(classeParametro.getDescricao())) {
            AppException appException = new AppException("Descrição não pode ser nula", 406);
            return Response.ok().entity(new RaizJson(appException)).build();
        }else if (classeParametro.getId() == 0 || classeParametro.getId() == null) {
            AppException appException = new AppException("Id não pode ser nula", 406);
            return Response.ok().entity(new RaizJson(appException)).build();
        }else{
        
        
            classe = new Classe();
            
            classe = classeService.buscaPorId(classeParametro.getId(), false);
            classe.setDescricao(classeParametro.getDescricao());
            
            classe = classeService.salvar(classe);
        
        }

        //Retorna 200, com dto no corpo
        return Response.ok().entity(new RaizJson(classe)).build();

    }
    
    
    @ApiOperation(value = "Deletar classe ", response = Classe.class)
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deletarClasse/{id}")
    public Response deleteClasse(@PathParam("id") Integer id) throws AppException {

        try {

            classe = new Classe();

            if (id == 0 || id == null) {
                AppException appException = new AppException("Classe não encontrada", 404);
                return Response.ok().entity(new RaizJson(appException)).build();
            } else {

                this.classe = classeService.buscaPorId(id, false);

                 classeService.excluir(classe);
            }

            //Retorna 200, com dto no corpo
            return Response.ok().entity(new RaizJson(classe)).build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(e.getMessage(), 501);
        }
    }
}
