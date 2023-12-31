package med.voll.api.paciente;

import jakarta.validation.constraints.NotBlank;
import med.voll.api.endereco.DadosEndereco;

public record DadosCadastroPaciente(
		
		Long id,
		
		@NotBlank
        String nome,
        
        @NotBlank
        String email,
        
        @NotBlank
        String telefone,
        
        @NotBlank
        String cpf,
        
        DadosEndereco endereco) {

	public DadosCadastroPaciente(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), new DadosEndereco(paciente.getEndereco()));
	}
	
}
