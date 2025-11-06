package th.go.dxc.share.exception;

import org.springframework.http.HttpStatus;

public class CustomBusinessException extends RuntimeException {
	
	private final HttpStatus status;

	public CustomBusinessException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
