package med.voll.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
	private int Page;

	@PostMapping
	@Transactional
	public ResponseEntity<DadosCadastroMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
		Medico medico = repository.save(new Medico(dados));
		return ResponseEntity.ok(new DadosCadastroMedico(medico));

	}

	@GetMapping("/lista")
	public List<DadosListagemMedico> listar() {
		return repository.findAll().stream().map(DadosListagemMedico::new).toList();
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemMedico>> listarPaginado(
			@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
		Page<DadosListagemMedico> page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
		return ResponseEntity.ok(page);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosAtualizacaoMedico> atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		var medico = repository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosAtualizacaoMedico(medico));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		var medico = repository.getReferenceById(id);
		medico.excluir();

		return ResponseEntity.noContent().build();
	}

}
