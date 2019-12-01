package com.Team3_6.kifu;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.chat21.android.ui.ChatUI;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {


    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        setHasOptionsMenu(false); // disable fragment option menu

        // starts the chat inside a container
        ChatUI.getInstance().openConversationsListFragment(getChildFragmentManager(), R.id.container);

        return rootView;
    }

}
