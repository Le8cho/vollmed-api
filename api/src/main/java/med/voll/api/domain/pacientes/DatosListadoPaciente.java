package med.voll.api.domain.pacientes;

public record DatosListadoPaciente(
        String nombre,
        String documento,
        String email
) {
    public DatosListadoPaciente(Paciente p){
        this(p.getNombre(), p.getDocumento(), p.getEmail());
    }
}
