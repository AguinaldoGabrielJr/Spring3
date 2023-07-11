package med.voll.api.paciente;

import javax.xml.stream.events.EndElement;

import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(

		@NotNull Long id, String nome, String telefone, String email, DadosEndereco endereco) {

	public DadosAtualizacaoPaciente(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getTelefone(), paciente.getEmail(),
				new DadosEndereco(paciente.getEndereco()));
	}

}
