/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.agenda.rest;

import br.com.projeto.agenda.model.Classe;
import br.com.projeto.agenda.model.Especialidade;
import br.com.projeto.agenda.model.Personagem;
import br.com.projeto.agenda.model.PersonagemEspecialidade;
import br.com.projeto.agenda.model.to.ClasseTO;
import br.com.projeto.agenda.model.to.EspecialidadeTO;
import br.com.projeto.agenda.model.to.PersonagemTO;
import br.com.projeto.agenda.rest.util.AppException;
import br.com.projeto.agenda.rest.util.RaizJson;
import br.com.projeto.agenda.service.ClasseService;
import br.com.projeto.agenda.service.EspecialidadeService;
import br.com.projeto.agenda.service.PersonagemService;
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
@Api(value = "Personagens WS")
@Path("/personagens")
public class PersonagemWS {

    @Inject
    private PersonagemService service;

    @Inject
    private ClasseService classeService;

    @Inject
    private EspecialidadeService especialidadeService;

    private Personagem personagem;

    private List<Personagem> listaPersonagens;

    private List<PersonagemTO> listaPersonagemTO;

    @ApiOperation(value = "Buscar personagem por ID", response = PersonagemTO.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/buscarPersonagem/{id}")
    public Response buscarPersonagem(@PathParam("id") Integer id) throws AppException {

        PersonagemTO personagemTO = new PersonagemTO();
        List<EspecialidadeTO> listaEspecialidadeTO = new ArrayList<>();

        try {

            personagem = new Personagem();

            if (id == 0 || id == null) {
                AppException appException = new AppException("Personagem não encontrada", 404);
                return Response.ok().entity(new RaizJson(appException)).build();
            } else {

                this.personagem = service.buscaPorId(id, false);

                if (personagem == null) {
                    AppException appException = new AppException("Personagem não encontrada", 404);
                    return Response.ok().entity(new RaizJson(appException)).build();
                }
            }

            if (personagem != null) {
                personagemTO.setId(personagem.getId());
                personagemTO.setImagem(personagem.getImagem());
                personagemTO.setDano(personagem.getDano());
                personagemTO.setDefesa(personagem.getDefesa());
                personagemTO.setNome(personagem.getNome());
                personagemTO.setObservacao(personagem.getNome());
                personagemTO.setVelocidadeAtaque(personagem.getVelocidadeAtaque());
                personagemTO.setVelocidadeMovimento(personagem.getVelocidadeMovimento());
                personagemTO.setVida(personagem.getVida());

                ClasseTO classeTO = new ClasseTO();
                classeTO.setId(personagem.getClasse().getId());
                classeTO.setDescricao(personagem.getClasse().getDescricao());

                personagemTO.setClasse(classeTO);

                for (PersonagemEspecialidade objeto : personagem.getListaEspecialidade()) {
                    EspecialidadeTO especialidadeTO = new EspecialidadeTO();

                    especialidadeTO.setId(objeto.getEspecialidade().getId());
                    especialidadeTO.setDescricao(objeto.getEspecialidade().getDescricao());

                    listaEspecialidadeTO.add(especialidadeTO);
                }

                personagemTO.setListaEspecialidadeTO(listaEspecialidadeTO);

            }

            //Retorna 200, com dto no corpo
            return Response.ok().entity(new RaizJson(personagemTO)).build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(e.getMessage(), 501);
        }
    }

    @ApiOperation(value = "Buscar todos os personagens ", response = PersonagemTO.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/buscarTodosPersonagens")
    public Response buscarTodosPersonagens() throws AppException {

        listaPersonagens = new ArrayList<>();

        listaPersonagens = service.buscaTodos();

        listaPersonagemTO = new ArrayList<>();

        try {

            for (Personagem personagemBanco : listaPersonagens) {
                if (personagemBanco != null) {

                    PersonagemTO personagemTO = new PersonagemTO();
                    List<EspecialidadeTO> listaEspecialidadeTO = new ArrayList<>();

                    personagemTO.setId(personagemBanco.getId());
                    personagemTO.setImagem(personagemBanco.getImagem());
                    personagemTO.setDano(personagemBanco.getDano());
                    personagemTO.setDefesa(personagemBanco.getDefesa());
                    personagemTO.setNome(personagemBanco.getNome());
                    personagemTO.setObservacao(personagemBanco.getNome());
                    personagemTO.setVelocidadeAtaque(personagemBanco.getVelocidadeAtaque());
                    personagemTO.setVelocidadeMovimento(personagemBanco.getVelocidadeMovimento());
                    personagemTO.setVida(personagemBanco.getVida());

                    ClasseTO classeTO = new ClasseTO();
                    classeTO.setId(personagemBanco.getClasse().getId());
                    classeTO.setDescricao(personagemBanco.getClasse().getDescricao());

                    personagemTO.setClasse(classeTO);

                    for (PersonagemEspecialidade objeto : personagemBanco.getListaEspecialidade()) {
                        EspecialidadeTO especialidadeTO = new EspecialidadeTO();

                        especialidadeTO.setId(objeto.getEspecialidade().getId());
                        especialidadeTO.setDescricao(objeto.getEspecialidade().getDescricao());

                        listaEspecialidadeTO.add(especialidadeTO);
                    }

                    personagemTO.setListaEspecialidadeTO(listaEspecialidadeTO);

                    listaPersonagemTO.add(personagemTO);

                }

            }

            //Retorna 200, com dto no corpo
            return Response.ok().entity(new RaizJson(listaPersonagemTO)).build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(e.getMessage(), 501);
        }
    }
    
    
    @ApiOperation(value = "Buscar por nome e classe", response = PersonagemTO.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/buscarPorNomeClasse/{nome}/{classe}")
    public Response buscarTodosPersonagens(@PathParam("nome") String nome, @PathParam("classe") String classe) throws AppException {

        
       
     
        listaPersonagens = new ArrayList<>();

        listaPersonagens = service.buscaComFiltros(nome, null, classe);

        listaPersonagemTO = new ArrayList<>();

        try {

            for (Personagem personagemBanco : listaPersonagens) {
                if (personagemBanco != null) {

                    PersonagemTO personagemTO = new PersonagemTO();
                    List<EspecialidadeTO> listaEspecialidadeTO = new ArrayList<>();

                    personagemTO.setId(personagemBanco.getId());
                    personagemTO.setImagem(personagemBanco.getImagem());
                    personagemTO.setDano(personagemBanco.getDano());
                    personagemTO.setDefesa(personagemBanco.getDefesa());
                    personagemTO.setNome(personagemBanco.getNome());
                    personagemTO.setObservacao(personagemBanco.getNome());
                    personagemTO.setVelocidadeAtaque(personagemBanco.getVelocidadeAtaque());
                    personagemTO.setVelocidadeMovimento(personagemBanco.getVelocidadeMovimento());
                    personagemTO.setVida(personagemBanco.getVida());

                    ClasseTO classeTO = new ClasseTO();
                    classeTO.setId(personagemBanco.getClasse().getId());
                    classeTO.setDescricao(personagemBanco.getClasse().getDescricao());

                    personagemTO.setClasse(classeTO);

                    for (PersonagemEspecialidade objeto : personagemBanco.getListaEspecialidade()) {
                        EspecialidadeTO especialidadeTO = new EspecialidadeTO();

                        especialidadeTO.setId(objeto.getEspecialidade().getId());
                        especialidadeTO.setDescricao(objeto.getEspecialidade().getDescricao());

                        listaEspecialidadeTO.add(especialidadeTO);
                    }

                    personagemTO.setListaEspecialidadeTO(listaEspecialidadeTO);

                    listaPersonagemTO.add(personagemTO);

                }

            }

            //Retorna 200, com dto no corpo
            return Response.ok().entity(new RaizJson(listaPersonagemTO)).build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(e.getMessage(), 501);
        }
    }
    

    @ApiOperation(value = "Cadastra personagem", response = PersonagemTO.class)
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cadastraPersonagem")
    public Response cadastraPersonagem(PersonagemTO parametro) throws AppException, NegocioException {

        Classe classeBanco = new Classe();
        List<PersonagemEspecialidade> listaEspecialidade = new ArrayList<>();

        if (parametro != null) {

            if (Util.isBlank(parametro.getNome())) {
                AppException appException = new AppException("Nome não pode ser nulo", 406);
                return Response.ok().entity(new RaizJson(appException)).build();
            } else {

                if (!service.verificaUsoNome(parametro.getNome())) {
                    AppException appException = new AppException("Já existe um personagem com este nome", 406);
                    return Response.ok().entity(new RaizJson(appException)).build();
                }
            }

            if (parametro.getClasse().getId() == null || parametro.getClasse().getId() == 0 || Util.isBlank(parametro.getClasse().getDescricao())) {
                AppException appException = new AppException("Selecione uma classe para o personagem", 406);
                return Response.ok().entity(new RaizJson(appException)).build();
            } else {

                classeBanco = classeService.buscaPorId(parametro.getClasse().getId(), false);

                if (classeBanco == null) {
                    AppException appException = new AppException("Classe não encontrada", 406);
                    return Response.ok().entity(new RaizJson(appException)).build();

                }

            }

            if (parametro.getVida() == null) {
                AppException appException = new AppException("Valor da vida do personagem não pode ser nulo", 406);
                return Response.ok().entity(new RaizJson(appException)).build();
            }

            Personagem personagemBanco = new Personagem();
            personagemBanco.setId(0);
            personagemBanco.setDano(parametro.getDano());
            personagemBanco.setDefesa(parametro.getDefesa());
            personagemBanco.setImagem(parametro.getImagem());
            personagemBanco.setNome(parametro.getNome());
            personagemBanco.setObservacao(parametro.getObservacao());
            personagemBanco.setVelocidadeAtaque(parametro.getVelocidadeAtaque());
            personagemBanco.setVelocidadeMovimento(parametro.getVelocidadeMovimento());
            personagemBanco.setVida(parametro.getVida());
            personagemBanco.setClasse(classeBanco);

            personagemBanco = service.salvar(personagemBanco);

            for (EspecialidadeTO especialidadeTO : parametro.getListaEspecialidadeTO()) {

                if (especialidadeTO.getId() != null && especialidadeTO.getId() != 0 && Util.isNotBlank(especialidadeTO.getDescricao())) {

                    Especialidade especialidade = new Especialidade();
                    
                    especialidade = especialidadeService.buscaPorId(especialidadeTO.getId(), false);
                    
                    if(especialidade != null){
                    
                        PersonagemEspecialidade persoangemEspecialidadeBanco = new PersonagemEspecialidade();
                        persoangemEspecialidadeBanco.setId(0);
                        persoangemEspecialidadeBanco.setPersonagem(personagemBanco);
                        persoangemEspecialidadeBanco.setEspecialidade(especialidade);
                        
                        listaEspecialidade.add(persoangemEspecialidadeBanco);
                        
                    }
                    
                    
                }

            }
            
            personagemBanco.setListaEspecialidade(listaEspecialidade);
            
            
            personagemBanco = service.salvar(personagemBanco);

        } else {
            AppException appException = new AppException("Objeto nulo", 406);
            return Response.ok().entity(new RaizJson(appException)).build();
        }

        //Retorna 200, com dto no corpo
        return Response.ok().entity(new RaizJson(parametro)).build();

    }
    
    
    @ApiOperation(value = "Deletar personagem ", response = Response.class)
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deletarPersoangem/{id}")
    public Response deleteEspecialidade(@PathParam("id") Integer id) throws AppException {

        try {

            personagem = new Personagem();

            if (id == 0 || id == null) {
                AppException appException = new AppException("Personagem não encontrada", 404);
                return Response.ok().entity(new RaizJson(appException)).build();
            } else {

                this.personagem = service.buscaPorId(id, false);

                service.excluir(personagem);
            }

            //Retorna 200, com dto no corpo
            return Response.ok().entity(new RaizJson()).build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(e.getMessage(), 501);
        }
    }

}
