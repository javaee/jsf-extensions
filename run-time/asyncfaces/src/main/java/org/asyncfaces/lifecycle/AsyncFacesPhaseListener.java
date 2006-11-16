/*
 *
 * Created on 11 novembre 2006, 21.11
 */

package org.asyncfaces.lifecycle;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import java.util.List;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.asyncfaces.AsyncFacesEncoderHandler;
import org.asyncfaces.InitializerBean;

/**
 *
 * @author agori
 */
public class AsyncFacesPhaseListener implements PhaseListener {
    

    
    public void afterPhase(PhaseEvent event) {
       
    }
    
    public void beforePhase(PhaseEvent event) {
        
        if (PhaseId.RESTORE_VIEW.equals(event.getPhaseId())) {
            FacesContext context = event.getFacesContext();
            ELContext elContext = context.getELContext();
            ValueExpression ve = context.getApplication().getExpressionFactory().
                    createValueExpression(
                    elContext, "#{asyncfacesInitializerBean}",
                    InitializerBean.class);
            ve.getValue(elContext);
            return;
        }
        
        if (AsyncResponse.isAjaxRequest()) {
            AsyncResponse async = AsyncResponse.getInstance();
            boolean leaveRenderingToDynaFaces = !async.isRenderNone() || !async.isRenderAll();
            
            if (PhaseId.APPLY_REQUEST_VALUES.equals(event.getPhaseId()) &&
                    leaveRenderingToDynaFaces) {
                
                List<String> renders = async.getRenderSubtrees();
                async.setEncoderHandlerInstance(new AsyncFacesEncoderHandler(renders));
                
            }
        }
    }
    
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
    
    
    
}
