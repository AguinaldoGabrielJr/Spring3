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
import med.voll.api.medico.DadosAtualizacaoMedico;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;

@RestController
@RequestMapping("medicos")
public class MedicoController {

	@Autowired
	private MedicoRepository repository;

	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
		repository.save(new Medico(dados));
	}

	@GetMapping("/lista")
	public List<DadosListagemMedico> listar() {
		return repository.findAll().stream().map(DadosListagemMedico::new).toList();
	}
	
//	@GetMapping
//	public Page<DadosListagemMedico> listarPaginado(Pageable paginacao) {
//		return repository.findAll(paginacao).map(DadosListagemMedico::new);
//	}
	
	@GetMapping
	public Page<DadosListagemMedico> listarPaginado(Pageable paginacao) {
		return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
	}
	
    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
    	var medico = repository.getReferenceById(dados.id());
    	medico.atualizarInformacoes(dados);
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
    	var medico = repository.getReferenceById(id);
    	medico.excluir();
    }

}
