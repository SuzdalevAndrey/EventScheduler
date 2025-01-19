package ru.andreyszdlv.eventscheduler.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.andreyszdlv.eventscheduler.dto.event.AddEventRequestDto;
import ru.andreyszdlv.eventscheduler.dto.event.EventDto;
import ru.andreyszdlv.eventscheduler.model.Event;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {

    Event toEntity(AddEventRequestDto eventDto);

    EventDto toDto(Event event);

}
