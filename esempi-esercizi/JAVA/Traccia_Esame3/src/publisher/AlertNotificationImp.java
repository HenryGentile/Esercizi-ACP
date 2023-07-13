package publisher;

import Interfaces.IAlertNotification;

public class AlertNotificationImp implements IAlertNotification{

    private int ID;
    private int Criticality;
    protected static final long serialVersionUID=100000L;

    public AlertNotificationImp(int ID, int Criticality){
        this.ID = ID;
        this.Criticality = Criticality;
    }

    @Override
    public int getID() {
        // TODO Auto-generated method stub
        return ID;
    }

    @Override
    public int getCriticality() {
        // TODO Auto-generated method stub
        return Criticality;
    }
    
}
