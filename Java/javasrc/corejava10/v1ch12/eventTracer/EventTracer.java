import java.awt.*;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * event tracer
 *
 * @version 1.0.0 2020-03-21 12:57
 * @author bruce
 */
public class EventTracer {

    private InvocationHandler handler;

    public EventTracer() {
        // the handler for all event proxies
        handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method + ":" + args[0]);
                return null;
            }
        };
    }

    /**
     * adds event trace to which this component and its children can listen
     * @param c a component
     */
    public void add(Component c) {

        try {
            // get all events to which this component can listen
            BeanInfo info = Introspector.getBeanInfo(c.getClass());

            EventSetDescriptor[] eventSets = info.getEventSetDescriptors();
            for (EventSetDescriptor eventSet : eventSets) {
                addListener(c, eventSet);
            }

        } catch (IntrospectionException ex) {

        }
        // ok not to add listener if exception is thrown
        if (c instanceof Container) {
            // get all children and call add recursively
            for (Component comp : ((Container) c).getComponents()) {
                add(comp);
            }
        }

    }

    /**
     * add a listener to the given event set
     * @param c a component
     * @param eventSet a descriptor of a listener interface
     */
    public void addListener(Component c, EventSetDescriptor eventSet) {

        // make proxy object for this listener type
        // and route all calls to the handler
        Object proxy = Proxy.newProxyInstance(null, new Class[] { eventSet.getListenerType() }, handler);

        // add the proxy as a listener to the component
        Method addListenerMethod = eventSet.getAddListenerMethod();

        try {
            addListenerMethod.invoke(c, proxy);
        } catch (ReflectiveOperationException ex) {

        }
        // ok not to add listener if exception is thrown
    }
}
