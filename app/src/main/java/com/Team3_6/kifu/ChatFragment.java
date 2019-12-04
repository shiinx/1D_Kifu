package com.Team3_6.kifu;


import android.os.Bundle;

import androidx.annotation.Nullable;
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
    }

    /**
     * Returns a new instance of this fragment
     */
    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        setHasOptionsMenu(false); // disable fragment option menu

        // starts the chat inside a container
        ChatUI.getInstance().openConversationsListFragment(getChildFragmentManager(), R.id.container);

        return rootView;
    }

}
