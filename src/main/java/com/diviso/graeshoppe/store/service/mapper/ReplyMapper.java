package com.diviso.graeshoppe.store.service.mapper;

import com.diviso.graeshoppe.store.domain.*;
import com.diviso.graeshoppe.store.service.dto.ReplyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reply} and its DTO {@link ReplyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReplyMapper extends EntityMapper<ReplyDTO, Reply> {



    default Reply fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reply reply = new Reply();
        reply.setId(id);
        return reply;
    }
}
