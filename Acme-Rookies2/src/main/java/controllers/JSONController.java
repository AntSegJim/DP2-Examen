
package controllers;

import java.util.Collection;
import java.util.HashSet;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import security.LoginService;
import services.ActorService;
import services.AuditorService;
import services.HackerService;
import domain.Actor;
import domain.Auditor;
import domain.Position;
import domain.Rookie;

@Controller
@RequestMapping("/export")
public class JSONController extends AbstractController {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private HackerService	hackerService;

	@Autowired
	private AuditorService	auditorService;


	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public ResponseEntity<String> getMyJSONProfile() {
		ResponseEntity<String> res;
		try {
			final int id = LoginService.getPrincipal().getId();
			final Actor a = this.actorService.getActorByUserAccount(id);
			final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			final Rookie h = this.hackerService.hackerUserAccount(id);
			final Auditor auditor = this.auditorService.auditorUserAccount(id);
			String json;
			if (this.hackerService.hackerUserAccount(id) != null) {
				final Collection<Position> positions = h.getFinder().getPositions();
				h.getFinder().setPositions(new HashSet<Position>());
				json = ow.writeValueAsString(h);
				h.getFinder().setPositions(positions);
			} else if (this.auditorService.auditorUserAccount(id) != null) {
				final Collection<Position> positions = auditor.getPositions();
				auditor.setPositions(new HashSet<Position>());
				json = ow.writeValueAsString(auditor);
				auditor.setPositions(positions);
			} else
				json = ow.writeValueAsString(a);
			res = new ResponseEntity<String>(json, HttpStatus.OK);
		} catch (final Exception e) {
			res = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return res;
	}

	public String JSON() {
		String res;
		try {
			final int id = LoginService.getPrincipal().getId();
			final Actor a = this.actorService.getActorByUserAccount(id);
			final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			final String json = ow.writeValueAsString(a);
			res = json;
		} catch (final Exception e) {
			res = "Error";
		}
		return res;
	}
}
