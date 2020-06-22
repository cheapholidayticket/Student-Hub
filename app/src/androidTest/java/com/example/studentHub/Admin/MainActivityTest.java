package com.example.studentHub.Admin;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.studentHub.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
public class MainActivityTest {
    //launches activity  
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        // testing whether can find a view by R.id in MainActivity
        View view = mActivity.findViewById(R.id.email_Login);
        assertNotNull(view); //if view not null means successful, view is found,
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}