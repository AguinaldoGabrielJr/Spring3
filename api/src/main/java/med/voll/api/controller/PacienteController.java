package med.voll.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.DadosCadastroPaciente;
import med.voll.api.paciente.DadosListagemPaciente;
import med.voll.api.paciente.Paciente;
import med.voll.api.paciente.PacienteRepository;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

	@Autowired
	private PacienteRepository repository;

	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dados) {
		repository.save(new Paciente(dados));
	}

	@GetMapping("lista")
	public List<DadosListagemPaciente> listar(){
		return repository.findAll().stream().map(DadosListagemPaciente::new).toList();
	}
	
//	@GetMapping
//	public Page<DadosListagemPaciente> listarPaginado(
//			@PageableDefault(page = 0, size = 10, sort = { "nome" }) Pageable paginacao) {
//		return repository.findAll(paginacao).map(DadosListagemPaciente::new);
//	}   
	
	
//	@GetMapping
//	public Page<DadosListagemMedico> listarPaginado(Pageable paginacao) {
//		return repository.findAll(paginacao).map(DadosListagemMedico::new);
//	}
	
	@GetMapping
	public Page<DadosListagemPaciente> listarPaginado(Pageable paginacao) {
		return repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
	}
	
    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
    	var paciente = repository.getReferenceById(dados.id());
    	paciente.atualizarInformacoes(dados);
    }
    
//    //Exclusão total
//    @DeleteMapping("/{id}")//Parametro por URL...
//    @Transactional
//    public void excluirTotal(@PathVariable Long id) {
//    	repository.deleteById(id);
//    }
	
    @DeleteMapping("/{id}")//Parametro por URL...
    @Transactional
    public void excluir(@PathVariable Long id) {
    	var paciente = repository.getReferenceById(id);
    	paciente.excluir();
    }

}