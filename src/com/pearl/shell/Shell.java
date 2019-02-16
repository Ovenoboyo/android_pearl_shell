/*
 * Copyright (C) 2019 The Pearl Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pearl.shell;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.app.Activity;

import com.android.settings.Utils;
import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;

import com.pearl.shell.tabs.*;

public class Shell extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main, container, false);
		
		FrameLayout card1 = (FrameLayout)view.findViewById(R.id.card1);
		card1.setOnClickListener(this);
		
		FrameLayout card2 = (FrameLayout)view.findViewById(R.id.card2);
		card2.setOnClickListener(this);
		
		FrameLayout card3 = (FrameLayout) view.findViewById(R.id.card3);
		card3.setOnClickListener(this);
		
		FrameLayout card4 = (FrameLayout) view.findViewById(R.id.card4);
		card4.setOnClickListener(this);
		
		FrameLayout card5 = (FrameLayout) view.findViewById(R.id.card5);
		card5.setOnClickListener(this);
		
		FrameLayout card6 = (FrameLayout) view.findViewById(R.id.card6);
		card6.setOnClickListener(this);
		
		RelativeLayout about = (RelativeLayout) view.findViewById(R.id.about);
		about.setOnClickListener(this);

		RelativeLayout about = (RelativeLayout) view.findViewById(R.id.about);
		about.setOnClickListener(this);

        return view;
    }

	@Override
	public void onClick(View view) {
		Fragment fragment = null;
		switch (view.getId()) {
			case R.id.card1:
				fragment = new StatusbarFragment();
				replaceFragment(fragment);
				break;

			case R.id.card2:
				fragment = new LockscreenFragment();
				replaceFragment(fragment);
				break;

			case R.id.card3:
				fragment = new ButtonsFragment();
				replaceFragment(fragment);
				break;
				
			case R.id.card4:
				fragment = new InterfaceFragment();
			    replaceFragment(fragment);
				break;
			
			case R.id.card5:
				fragment = new RecentsFragment();
				replaceFragment(fragment);
				break;
				
			case R.id.card6:
				fragment = new MiscFragment();
			    replaceFragment(fragment);
				break;
				
            case R.id.about:
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.pearl.about");
                if (launchIntent != null) { 
                    startActivity(launchIntent);
                }

                         case R.id.about:
				PackageManager pm = getActivity().getPackageManager();
				Intent intent = pm.getLaunchIntentForPackage("com.pearl.about");
                                getActivity().startActivityFromFragment(this, intent, getTargetRequestCode());
			default:
				break;

		}
	}

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
