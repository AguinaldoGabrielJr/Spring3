package med.voll.api.paciente;


import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(

		@NotNull Long id, String nome, String telefone,String cpf, String email, DadosEndereco endereco) {

	public DadosAtualizacaoPaciente(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getTelefone(),paciente.getCpf(), paciente.getEmail(),
				new DadosEndereco(paciente.getEndereco()));
	}


}
