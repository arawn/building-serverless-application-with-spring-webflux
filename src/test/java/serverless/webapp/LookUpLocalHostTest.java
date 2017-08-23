package serverless.webapp;

import org.junit.Test;
import org.springframework.util.StopWatch;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class LookUpLocalHostTest {

    @Test
    public void lookUpLocalHost() throws UnknownHostException, URISyntaxException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String hostName = InetAddress.getLocalHost().getHostName();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());;

        System.out.println(new URI("/test"));
    }

}
