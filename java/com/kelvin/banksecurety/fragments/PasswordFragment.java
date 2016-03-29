package com.kelvin.banksecurety.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.banksecurety.R;

import java.util.Random;

/**
 * Created by Николай on 27.08.2015.
 */
public class PasswordFragment extends Fragment implements View.OnClickListener
{
	private static final String TAG = "com.kelvin.banksecurety.fragments.PasswordFragment";
	private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ+_)(*&^%$#@!~[]";
	private TextView editPass;
	private ClipboardManager clipboardManager;
	private ClipData _clipData;
	private Button copy;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		final View root = inflater.inflate(R.layout.fragment_password, container, false);
		final Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
		editPass = (TextView) root.findViewById(R.id.edit_text);
		Button generate = (Button) root.findViewById(R.id.generate);
		copy = (Button) root.findViewById(R.id.copy);
		clipboardManager = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);

		generate.setOnClickListener(this);
		copy.setOnClickListener(this);

		toolbar.setNavigationOnClickListener(
			new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					getActivity().onBackPressed();

				}
			}
		);

		return root;
	}


	@Override
	public void onClick(View v)
	{
		switch (v.getId()) {
			case R.id.generate:
				editPass.setText(getRandomString());
				copy.setVisibility(View.VISIBLE);
				break;
			case R.id.copy:
				if ("generate password".compareTo(editPass.getText().toString()) == 0) {
					Toast.makeText(v.getContext(), "First generate password!", Toast.LENGTH_LONG).show();
				} else {
					_clipData = ClipData.newPlainText("password", editPass.getText());
					clipboardManager.setPrimaryClip(_clipData);
				}
				break;
		}
	}


	private String getRandomString()
	{
		final Random random = new Random();
		final int lengthRnd = random.nextInt(5);
		final StringBuilder sb = new StringBuilder(lengthRnd + 10);

		for (int i = 0; i < lengthRnd + 10; ++i) {
			sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
		}
		return sb.toString();

	}

}

