import net.kencochrane.raven.Dsn;
import net.kencochrane.raven.Raven;
import net.kencochrane.raven.RavenFactory;
import net.kencochrane.raven.event.Event;
import net.kencochrane.raven.event.EventBuilder;
import net.kencochrane.raven.event.interfaces.ExceptionInterface;

/**
 * Created by kelong on 8/15/14.
 */
public class Example {
    public static void main(String[] args) {
        String rawDsn = "https://6cfbb7dfbace43af891a53d5b69c53c2:7a00f0c0567643b0841db8b94b83a428@app.getsentry.com/28894?raven.async=false&raven.async.gracefulshutdown=false";
        Raven raven = RavenFactory.ravenInstance(new Dsn(rawDsn));

        try{
            String s=null;
            s.split(",");
        }catch (Exception e){
            EventBuilder eventBuilder = new EventBuilder()
                    .setMessage("Hello from Raven!!!!!!!!test111111111111!!!!!!!")
                    .setLevel(Event.Level.ERROR)
                    .setLogger(Example.class.getName())
                    .addSentryInterface(new ExceptionInterface(e));

            raven.runBuilderHelpers(eventBuilder); // Optional
            raven.sendEvent(eventBuilder.build());
        }
        // record a simple message

    }
}
