package th.go.dxc.share.exception;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CustomBusinessException.class)
	public ResponseEntity<Object> handleCustomBusinessException(CustomBusinessException ex, HttpServletRequest request) {
		
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", OffsetDateTime.now(ZoneOffset.UTC));
		body.put("status", ex.getStatus().value());
		body.put("error", ex.getStatus().getReasonPhrase());
		body.put("message", String.format(ex.getMessage()));
		body.put("path", request.getRequestURI());

		return ResponseEntity.status(ex.getStatus()).body(body);
	}
}
