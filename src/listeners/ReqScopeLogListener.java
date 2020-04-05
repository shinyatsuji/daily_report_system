package listeners;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ReqScopeLogListener
 *
 */
@WebListener
public class ReqScopeLogListener implements ServletRequestAttributeListener {

    /**
     * Default constructor.
     */
    public ReqScopeLogListener() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see ServletRequestAttributeListener#attributeRemoved(ServletRequestAttributeEvent)
     */
    public void attributeRemoved(ServletRequestAttributeEvent arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * @see ServletRequestAttributeListener#attributeAdded(ServletRequestAttributeEvent)
     */
    public void attributeAdded(ServletRequestAttributeEvent arg0) {
        System.out.println("リクエストスコープに登録しました: " + arg0.getName() + "=" + arg0.getValue());

    }

    /**
     * @see ServletRequestAttributeListener#attributeReplaced(ServletRequestAttributeEvent)
     */
    public void attributeReplaced(ServletRequestAttributeEvent arg0) {
        // TODO Auto-generated method stub
    }

}
