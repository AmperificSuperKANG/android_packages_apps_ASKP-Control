package com.askp_control.Fragments;

import com.askp_control.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ControlFragment extends Fragment {

	public static String[] value;
	ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.control, container, false);

		listView = (ListView) rootView.findViewById(R.id.listView);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1, value);

		listView.setAdapter(adapter);

		return rootView;
	}
}
