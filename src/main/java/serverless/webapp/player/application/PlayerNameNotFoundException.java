package serverless.webapp.player.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PlayerNameNotFoundException extends ResponseStatusException {

    public PlayerNameNotFoundException(String name) {
        super(HttpStatus.NOT_FOUND, String.format("'%s'라는 이름을 가진 플레이어는 없습니다.", name));
    }

}
