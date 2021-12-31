package com.springboot.klos.service.mappers;

import com.springboot.klos.dto.request.ContactRequestDto;
import com.springboot.klos.dto.response.ContactResponseDto;
import com.springboot.klos.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {
    public ContactResponseDto mapToDto(Contact contact) {
        ContactResponseDto dto = new ContactResponseDto();
        dto.setPhoneNumber(contact.getPhoneNumber());
        dto.setEmail(dto.getEmail());
        return dto;
    }

    public Contact mapToModel(ContactRequestDto dto) {
        Contact contact = new Contact();
        contact.setPhoneNumber(dto.getPhoneNumber());
        contact.setEmail(dto.getEmail());
        return contact;
    }
}
