package com.springboot.klos.service.mappers;

public interface GenericMapper<Model, Req, Res> {
    Model mapToModel(Req dto);

    Res mapToDto(Model model);
}
