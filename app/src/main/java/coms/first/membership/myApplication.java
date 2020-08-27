package coms.first.membership;

import android.app.Application;

public class myApplication extends Application {
    private boolean form;

    public boolean getForm() {
        return form;
    }

    public void setForm(boolean form) {
        this.form = form;
    }
}