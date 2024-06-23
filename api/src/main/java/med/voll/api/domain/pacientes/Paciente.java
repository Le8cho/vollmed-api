package med.voll.api.domain.pacientes;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Table(name="pacientes")
@Entity(name="Paciente")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")

public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    private String documento;

    private String telefono;

    @Embedded
    private Direccion direccion;

    private boolean activo;

    public Paciente(DatosRegistroPaciente datosRegistroPaciente){
        this.nombre = datosRegistroPaciente.nombre();
        this.email = datosRegistroPaciente.email();
        this.documento= datosRegistroPaciente.documento();
        this.telefono = datosRegistroPaciente.telefono();
        this.direccion = new Direccion(datosRegistroPaciente.direccion());
        this.activo = true;
    }

    public void actualizarDatos(DatosActualizarPaciente datosActualizarPaciente) {
        if(datosActualizarPaciente.direccion() != null){
            this.direccion = this.direccion.actualizarDatos(datosActualizarPaciente.direccion());
        }
        if(datosActualizarPaciente.documento() != null){
            this.documento = datosActualizarPaciente.documento();
        }
        if(datosActualizarPaciente.nombre() != null){
            this.nombre = datosActualizarPaciente.nombre();
        }
    }


    public void eliminarPaciente() {
        this.activo = false;
    }
}
