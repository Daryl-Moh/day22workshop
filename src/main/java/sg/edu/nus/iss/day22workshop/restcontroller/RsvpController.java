package sg.edu.nus.iss.day22workshop.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.day22workshop.model.RSVP;
import sg.edu.nus.iss.day22workshop.repo.RsvpRepoImpl;

@RequestMapping("/api")
@RestController
public class RsvpController {

    @Autowired
    RsvpRepoImpl rsvpRepo;

    @GetMapping("/rsvps")
    public ResponseEntity<List<RSVP>> getAllRsvps() {
        List<RSVP> rsvps = new ArrayList<RSVP>();
        rsvps = rsvpRepo.findAll();
        if (rsvps.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rsvps, HttpStatus.OK);
    }

    @GetMapping("/rsvp")
    public ResponseEntity<List<RSVP>> getRsvpByName(@RequestParam("q") String name) {
        List<RSVP> listRsvps = new ArrayList<RSVP>();
        listRsvps = rsvpRepo.findByName(name);
        if (listRsvps.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listRsvps, HttpStatus.OK);
    }

    @PostMapping("/rsvp")
    public ResponseEntity<String> saveRsvp(@RequestBody RSVP rsvp) {
        try {
            Boolean saved = rsvpRepo.save(rsvp);
            if (saved) {
                return new ResponseEntity<>("RSVP record created successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("RSVP record failed to create.", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception ex) {

            return new ResponseEntity<>("Server failed to process saveRsvp.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/rsvp")
    public ResponseEntity<String> updateRsvp(@RequestBody RSVP rsvp) {

        Boolean result = rsvpRepo.update(rsvp);
        try {
            if (result) {
                return new ResponseEntity<>("RSVP record updated successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("RSVP record failed to update.", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception ex) {

            return new ResponseEntity<>("Server failed to process saveRsvp.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("rsvps/count")
    public ResponseEntity<Integer> getRsvpCount() {
        Integer rsvpCount = rsvpRepo.countAll();
        return new ResponseEntity<>(rsvpCount, HttpStatus.OK);
    }

    

}
