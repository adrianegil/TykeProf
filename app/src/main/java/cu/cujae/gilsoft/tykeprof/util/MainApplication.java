package cu.cujae.gilsoft.tykeprof.util;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(cu.cujae.gilsoft.tykeprof.util.LocaleHelper.onAttach(base, "es"));
    }
}
