package med.voll.api.domain.medicos;

public record DatosListadoMedico(
        Long id,
        String nombre,
        String especialidad,
        String documento,
        String email
) {
    public DatosListadoMedico(Medico m) {
        this(m.getId(),m.getNombre(), m.getEspecialidad().toString(), m.getDocumento(), m.getEmail());
    }
}
