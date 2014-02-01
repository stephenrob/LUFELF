package uk.co.stephen_robinson.uni.lufelf;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.Color;
/**
 * Created by James on 30/01/2014.
 */
public class BaseFragment  extends Fragment{
    protected FragmentManager fragmentManager;
    protected Context context;

    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager=fragmentManager;
    }
    public void setContext(Context context){
        this.context=context;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
