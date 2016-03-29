package com.kelvin.banksecurety.fragments.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.banksecurety.R;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder>
{
	private static final String LOG_TAG = "MenuListAdapter";
	public static final int P_ICON = 0;
	public static final int P_TEXT = 1;

	public static final int[][] ITEMS = new int[][] {
		{ R.drawable.ic_home, R.string.item_1 },
		{ R.drawable.ic_earth, R.string.item_2 },
		{ R.drawable.ic_android_hand, R.string.item_3 },
		{ android.R.drawable.ic_partial_secure, R.string.item_4 },
		{ R.drawable.ic_at, R.string.item_5 },
		{ R.drawable.ic_card, R.string.item_6 },
		{ R.drawable.ic_locked, R.string.item_7 },
		{ R.drawable.ic_paper_airplane, R.string.item_8 },
		{ R.drawable.ic_compose, R.string.item_9 },
		{ R.drawable.ic_password, R.string.item_10}
	};

	private final Context _context;
	private final AdapterView.OnItemClickListener _onItemClicListener;

	public MenuListAdapter(Context context, AdapterView.OnItemClickListener onClickListener)
	{
		_context = context;
		_onItemClicListener = onClickListener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View v = LayoutInflater.from(_context).inflate(R.layout.menu_item, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		holder.setView(position);
	}

	@Override
	public int getItemCount()
	{
		return ITEMS.length;
	}


	protected final class ViewHolder extends RecyclerView.ViewHolder
	{
		private final ImageView _menuItemIcon;
		private final TextView _menuItemText;

		public ViewHolder(View v)
		{
			super(v);

			if (_onItemClicListener != null) {
				v.setOnClickListener(
					new View.OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							_onItemClicListener.onItemClick(null, v, getLayoutPosition(), getItemId());
						}
					}
				);
			}

			_menuItemIcon = (ImageView) v.findViewById(R.id.menu_item_icon);
			_menuItemText = (TextView) v.findViewById(R.id.menu_item_text);
		}

		public void setView(int position)
		{
			_menuItemIcon.setImageResource(ITEMS[position][P_ICON]);
			_menuItemText.setText(_context.getString(ITEMS[position][P_TEXT]));
		}
	}
}
