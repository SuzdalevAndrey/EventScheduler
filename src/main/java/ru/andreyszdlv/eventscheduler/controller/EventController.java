package ru.andreyszdlv.eventscheduler.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andreyszdlv.eventscheduler.dto.event.AddEventRequestDto;
import ru.andreyszdlv.eventscheduler.dto.event.EventDto;
import ru.andreyszdlv.eventscheduler.service.EventService;
import ru.andreyszdlv.eventscheduler.validation.RequestValidator;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    private final RequestValidator requestValidator;

    @PostMapping
    public ResponseEntity<EventDto> addEvent(
            @Valid @RequestBody AddEventRequestDto addEventDto,
            BindingResult bindingResult) throws BindException {

        requestValidator.validate(bindingResult);

        EventDto responseDto = eventService.addEvent(addEventDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/generate")
    public ResponseEntity<List<EventDto>> generateEvents(@RequestParam int count){
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.generateEvents(count));
    }
}
