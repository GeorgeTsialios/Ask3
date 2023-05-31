package gr.upatras.rest.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.*;

@RestController
public class UserTextController {
	private static final Logger log = LoggerFactory.getLogger(UserTextController.class);
	@ApiOperation(value = "Retrieves all Texts", notes = "This operation retrieves all UserText entities.", response = UserText.class)

	@ApiResponses(value = {
		 @ApiResponse(code = 200, message = "Success", response = UserText.class),
		 @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
		 @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
		 @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
		 @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
		 @ApiResponse(code = 409, message = "Conflict", response = Error.class),
		 @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	
	
	@RequestMapping(value = "/usertext", produces = {"application/json;charset=utf-8" }, consumes = { "application/json" }, method = RequestMethod.POST)
 
	public ResponseEntity<UserText> publishText(@ApiParam(value = "The text that will be sent", required = true) @RequestBody String message) {
		 log.info("Will post a new text from user");
		 UserText text = new UserText(message);
		 text.publishText();
		 return new ResponseEntity<UserText>(text, HttpStatus.OK);
	}
}
