package com.komak.kero.keroapi.event;

import com.komak.kero.keroapi.auth.AuthService;
import com.komak.kero.keroapi.auth.Role;
import com.komak.kero.keroapi.auth.UserSession;
import com.komak.kero.keroapi.event.model.EventCreateModel;
import com.komak.kero.keroapi.event.model.EventDeleteModel;
import com.komak.kero.keroapi.event.model.EventListModel;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/event")
public class EventController {

  @Autowired
  private EventService eventService;

  @Autowired
  private AuthService authService;

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public ResponseEntity<Object> create(@RequestBody @Valid EventCreateModel event) {
    UserSession session = authService.getSession();
    if (session.getRole() == Role.ROLE_ADMIN || session.getRole() == Role.ROLE_MEMBER) {
      event.setAuthorId(session.getId());
      eventService.create(EventAdapter.toEvent(event));
      return new ResponseEntity("Done.", HttpStatus.OK);
    }
    else {
      return new ResponseEntity("Not authorised.", HttpStatus.UNAUTHORIZED);
    }
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ResponseEntity<Object> getList() {
    List<EventListModel> list = eventService.list().stream().map(EventAdapter::toListModel)
        .collect(Collectors.toList());
    return new ResponseEntity(list, HttpStatus.OK);
  }

  @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
  public ResponseEntity<Object> delete(@PathVariable String eventId) {
    UserSession session = authService.getSession();
    EventDeleteModel eventDelete = new EventDeleteModel(eventId, session);
    eventService.delete(eventDelete);
    return new ResponseEntity("Done.", HttpStatus.OK);
  }

}