package med.voll.api.domain.medicos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Table(name = "medicos") //Indicamos que hay una tabla asociada de nombre medicos
@Entity(name = "Medico") //Indicamos que esta entidad tiene de nombre Medico
//Anotaciones Lombok
@Getter //Se incluyen todos los getters
@NoArgsConstructor //se incluye un constructor sin ningun parametro
@AllArgsConstructor //incluye un constructor con todos los parametros
@EqualsAndHashCode(of = "id") //se implementa un hashcode usando de base el ID

public class Medico {
    @Id //indicamos que este campo es el Id de nuestra entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) //indicamos que el ID se autoincrementa
    private Long id;

    private String nombre;

    private String email;

    private String telefono;

    private String documento;

    @Enumerated(EnumType.STRING) //indicamos que es un enum de tipo STRING
    private Especialidad especialidad;

    @Embedded //Indicamos que tiene de atributo una clase
    private Direccion direccion;

    private boolean activo;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.nombre = datosRegistroMedico.nombre();
        this.email  = datosRegistroMedico.email();
        this.telefono = datosRegistroMedico.telefono();
        this.documento = datosRegistroMedico.documento();
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion = new Direccion(datosRegistroMedico.direccion());
        this.activo = true;
    }

    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {
        if(datosActualizarMedico.nombre() != null){
            this.nombre = datosActualizarMedico.nombre();
        }
        if(datosActualizarMedico.documento() != null){
            this.documento = datosActualizarMedico.documento();
        }
        if(datosActualizarMedico.direccion() != null){
            this.direccion = this.direccion.actualizarDatos(datosActualizarMedico.direccion());
        }
    }

    public void desactivarMedico() {
        this.activo = false;
    }
}
