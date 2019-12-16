/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.rest;

import br.com.projeto.agenda.model.Classe;
import br.com.projeto.agenda.model.Especialidade;
import br.com.projeto.agenda.rest.util.AppException;
import br.com.projeto.agenda.rest.util.RaizJson;
import br.com.projeto.agenda.service.EspecialidadeService;
import br.com.projeto.agenda.util.app.NegocioException;
import br.com.projeto.agenda.util.app.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Usuario
 */
@Api(value = "Especialidades WS")
@Path("/especialidades")
public class EspecialidadeWS {

    @Inject
    private EspecialidadeService service;

    private Especialidade especialidade;

    private List<Especialidade> listaEspecialidade;

    @ApiOperation(value = "Buscar especialidade por ID", response = Especialidade.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/buscarEspecialidade/{id}")
    public Response buscarEspecialidade(@PathParam("id") Integer id) throws AppException {

        try {

            especialidade = new Especialidade();

            if (id == 0 || id == null) {
                AppException appException = new AppException("Especialidade não encontrada", 404);
                return Response.ok().entity(new RaizJson(appException)).build();
            } else {

                this.especialidade= service.buscaPorId(id, false);

                if (especialidade == null) {
                    AppException appException = new AppException("Especialidade não encontrada", 404);
                    return Response.ok().entity(new RaizJson(appException)).build();
                }
            }

            //Retorna 200, com dto no corpo
            return Response.ok().entity(new RaizJson(especialidade)).build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(e.getMessage(), 501);
        }
    }

    @ApiOperation(value = "Buscar todas as especialidades ", response = Especialidade.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/buscarTodasEspecialidades")
    public Response buscarTodasEspecialidades() throws AppException {

        listaEspecialidade = new ArrayList<>();

        listaEspecialidade = service.buscaTodos();

        //Retorna 200, com dto no corpo
        return Response.ok().entity(new RaizJson(listaEspecialidade)).build();
    }

    @ApiOperation(value = "Cadastra especialidade", response = Especialidade.class)
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cadastraEspecialidade")
    public Response cadastraEspecialidade(Especialidade parametro) throws AppException, NegocioException {

        if (Util.isNotBlank(parametro.getDescricao())) {

            if (!service.verificaUsoDescricao(parametro.getDescricao())) {
                AppException appException = new AppException("Já existe uma especialidade com essa descrição", 406);
                return Response.ok().entity(new RaizJson(appException)).build();
            } else {
                especialidade = new Especialidade();
                especialidade.setId(0);
                especialidade.setDescricao(parametro.getDescricao());

                especialidade = service.salvar(especialidade);
            }

        } else {
            AppException appException = new AppException("Descrição não pode ser nula", 406);
            return Response.ok().entity(new RaizJson(appException)).build();
        }

        //Retorna 200, com dto no corpo
        return Response.ok().entity(new RaizJson(especialidade)).build();

    }

    @ApiOperation(value = "Alterar especialidade", response = Especialidade.class)
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/alterarEspecialidade")
    public Response alteraEspecialidade(Especialidade parametro) throws AppException, NegocioException {

        if (!Util.isNotBlank(parametro.getDescricao())) {
            AppException appException = new AppException("Descrição não pode ser nula", 406);
            return Response.ok().entity(new RaizJson(appException)).build();
        } else if (parametro.getId() == 0 || parametro.getId() == null) {
            AppException appException = new AppException("Id não pode ser nula", 406);
            return Response.ok().entity(new RaizJson(appException)).build();
        } else {

            especialidade = new Especialidade();

            especialidade = service.buscaPorId(parametro.getId(), false);
            especialidade.setDescricao(parametro.getDescricao());

            especialidade = service.salvar(especialidade);

        }

        //Retorna 200, com dto no corpo
        return Response.ok().entity(new RaizJson(especialidade)).build();

    }

    @ApiOperation(value = "Deletar especialidade ", response = Especialidade.class)
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deletarEspecialidade/{id}")
    public Response deleteEspecialidade(@PathParam("id") Integer id) throws AppException {

        try {

            especialidade = new Especialidade();

            if (id == 0 || id == null) {
                AppException appException = new AppException("Especialidade não encontrada", 404);
                return Response.ok().entity(new RaizJson(appException)).build();
            } else {

                this.especialidade = service.buscaPorId(id, false);

                service.excluir(especialidade);
            }

            //Retorna 200, com dto no corpo
            return Response.ok().entity(new RaizJson(especialidade)).build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(e.getMessage(), 501);
        }
    }
}
