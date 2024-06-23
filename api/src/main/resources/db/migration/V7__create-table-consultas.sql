CREATE TABLE consultas(

    id bigint not null auto_increment,
    paciente_id bigint not null,
    medico_id bigint not null,
    fecha datetime not null,

    primary key(id),
    constraint foreign_key_medico foreign key(medico_id) references medicos(id),
    constraint foreign_key_paciente foreign key(paciente_id) references pacientes(id)
)