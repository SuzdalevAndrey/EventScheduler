package ru.andreyszdlv.eventscheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreyszdlv.eventscheduler.dto.event.AddEventRequestDto;
import ru.andreyszdlv.eventscheduler.dto.event.EventDto;
import ru.andreyszdlv.eventscheduler.mapper.EventMapper;
import ru.andreyszdlv.eventscheduler.model.Event;
import ru.andreyszdlv.eventscheduler.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    private final UserService userService;

    public EventDto addEvent(AddEventRequestDto addEventDto) {
        Event event = eventMapper.toEntity(addEventDto);

        event.setAuthorId(userService.getCurrentUserId());

        return eventMapper.toDto(eventRepository.save(event));
    }

    @Transactional
    public List<EventDto> generateEvents(int count) {
        List<EventDto> result = new ArrayList<>();

        List<String> titles = List.of("Meeting", "Make an appointment with a doctor", "Exam", "Call", "Walk");


        for (int i = 0; i < count; i++) {
            String title = titles.get(new Random().nextInt(titles.size()));
            result.add(
                    addEvent(new AddEventRequestDto(title, null, 1, LocalDateTime.now()))
            );
        }

        return result;
    }
}
