package med.voll.api.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(

		@NotNull Long id, String nome, String telefone, String email, DadosEndereco endereco) {

	public DadosAtualizacaoMedico(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getTelefone(), medico.getEmail(),
				new DadosEndereco(medico.getEndereco()));
	}

}
