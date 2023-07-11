package med.voll.api.controller;

import java.net.URI;
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
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosDetalhamentoMedico;
import med.voll.api.paciente.DadosAtualizacaoPaciente;
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
	public ResponseEntity<DadosAtualizacaoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
		Paciente paciente = new Paciente(dados);
		repository.save(paciente);
		URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosAtualizacaoPaciente(paciente));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosAtualizacaoPaciente> detalhar(@PathVariable Long id) {
		Paciente paciente = repository.getReferenceById(id);

		return ResponseEntity.ok(new DadosAtualizacaoPaciente(paciente));
	}
	
	@GetMapping
	public ResponseEntity<Page<DadosCadastroPaciente>> listar(Pageable paginacao) {
		Page<DadosCadastroPaciente> page = repository.findAllByAtivoTrue(paginacao).map(DadosCadastroPaciente::new);
		return ResponseEntity.ok(page);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosAtualizacaoPaciente> atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
		Paciente paciente = repository.getReferenceById(dados.id());
		paciente.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosAtualizacaoPaciente(paciente));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		var paciente = repository.getReferenceById(id);
		paciente.excluir();
		
		return ResponseEntity.noContent().build();
	}

}