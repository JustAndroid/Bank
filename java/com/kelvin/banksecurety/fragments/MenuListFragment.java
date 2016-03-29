package com.kelvin.banksecurety.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.kelvin.banksecurety.MainActivity;
import com.kelvin.banksecurety.R;
import com.kelvin.banksecurety.fragments.adapter.MenuListAdapter;

public class MenuListFragment extends Fragment implements AdapterView.OnItemClickListener
{
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		final View root = inflater.inflate(R.layout.fragment_menu_list, container, false);
		final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
		final MenuListAdapter adapter = new MenuListAdapter(getActivity(), this);
		final RecyclerView optionsList = (RecyclerView) root.findViewById(R.id.options_list);
		optionsList.setLayoutManager(layoutManager);
		optionsList.setHasFixedSize(true);
		optionsList.setAdapter(adapter);
		return root;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		final Bundle arg = new Bundle(1);
		arg.putInt(MenuItemFragment.KEY_ITEM_INDEX, position);

		switch (position) {
			case 0: ((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_MENU_ITEM, arg); break;
			case 1: ((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_MENU_ITEM, arg); break;
			case 2: ((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_MENU_ITEM, arg); break;
			case 3: ((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_MENU_ITEM, arg); break;
			case 4: ((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_MENU_ITEM, arg); break;
			case 5: ((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_MENU_ITEM, arg); break;
			case 6: ((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_MENU_ITEM, arg); break;
			case 7: ((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_MENU_ITEM, arg); break;
			case 8: ((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_QUIZ_WELCOME, arg); break;
			case 9: ((MainActivity) getActivity()).startAction(MainActivity.ACTION_OPEN_PASSWORD, arg); break;
		}
	}
}
