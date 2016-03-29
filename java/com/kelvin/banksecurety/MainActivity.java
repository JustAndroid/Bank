package com.kelvin.banksecurety;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kelvin.banksecurety.fragments.MenuItemFragment;
import com.kelvin.banksecurety.fragments.MenuListFragment;
import com.kelvin.banksecurety.fragments.PasswordFragment;
import com.kelvin.banksecurety.fragments.QuizFragment;
import com.kelvin.banksecurety.fragments.QuizResultFragment;
import com.kelvin.banksecurety.fragments.QuizWelcomeFragment;
import com.kelvin.banksecurety.fragments.WelcomeFragment;

public class MainActivity extends AppCompatActivity
{
	private static final String LOG_TAG = ".MainActivity";
	public static final int ACTION_OPEN_WELCOME = 0;
	public static final int ACTION_OPEN_MENU_LIST = 1;
	public static final int ACTION_OPEN_MENU_ITEM = 2;
	public static final int ACTION_OPEN_QUIZ_WELCOME = 3;
	public static final int ACTION_OPEN_QUIZ = 4;
	public static final int ACTION_OPEN_QUIZ_RESULT = 5;
	public static final int ACTION_OPEN_PASSWORD = 6;

	private FragmentManager _fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_fragmentManager = getSupportFragmentManager();

		startAction(ACTION_OPEN_WELCOME, null);
	}

	@Override
	public void onBackPressed()
	{

		final int stackSize = _fragmentManager.getBackStackEntryCount();

		if (stackSize == 3) {
			_fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			startAction(ACTION_OPEN_MENU_LIST, null);
		}

		if (stackSize > 0) {
			final FragmentManager.BackStackEntry stackEntry = _fragmentManager.getBackStackEntryAt(stackSize - 1);
			_fragmentManager.popBackStack(stackEntry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

			if (
				QuizFragment.class.getName().equals(stackEntry.getName())
					|| QuizResultFragment.class.getName().equals(stackEntry.getName())
				) {
				startAction(ACTION_OPEN_MENU_LIST, null);
				return;
			}
		}

		if (stackSize == 0) {
			super.onBackPressed();
		}
	}

	public void startAction(int id, Bundle arg)
	{
		final Fragment fragment;
		final boolean hasBackStack;

		switch (id) {
			case ACTION_OPEN_WELCOME:
				fragment = new WelcomeFragment();
				hasBackStack = false;
				break;
			case ACTION_OPEN_MENU_LIST:
				fragment = new MenuListFragment();
				hasBackStack = false;
				break;
			case ACTION_OPEN_MENU_ITEM:
				fragment = new MenuItemFragment();
				hasBackStack = true;
				break;
			case ACTION_OPEN_QUIZ_WELCOME:
				fragment = new QuizWelcomeFragment();
				hasBackStack = true;
				break;
			case ACTION_OPEN_QUIZ:
				fragment = new QuizFragment();
				hasBackStack = true;
				break;
			case ACTION_OPEN_QUIZ_RESULT:
				fragment = new QuizResultFragment();
				hasBackStack = true;
				break;
			case ACTION_OPEN_PASSWORD:
				fragment = new PasswordFragment();
				hasBackStack = true;
				break;
			default:
				fragment = null;
				hasBackStack = true;
		}

		if (fragment != null && arg != null) {
			fragment.setArguments(arg);
		}

		_replaceFragmentById(id, fragment, hasBackStack);
	}

	private void _replaceFragmentById(int id, Fragment fragment, boolean backStack)
	{
		final String tag = fragment.getClass().getName();
		_fragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);

		FragmentTransaction fragmentTransaction = _fragmentManager.beginTransaction();
		Log.v(LOG_TAG, "" + id);
		fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
		if (backStack) {
			fragmentTransaction.addToBackStack(tag);
		} else {
			_fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}

		fragmentTransaction.commit();
	}
}
