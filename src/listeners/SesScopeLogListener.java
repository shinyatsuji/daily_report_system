package listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Application Lifecycle Listener implementation class SesScopeLogListener
 *
 */
@WebListener
public class SesScopeLogListener implements HttpSessionAttributeListener {

    /**
     * Default constructor.
     */
    public SesScopeLogListener() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0) {
        System.out.println("セッションスコープに登録しました: " + arg0.getName() + "=" + arg0.getValue());

    }

    /**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
    }

}
