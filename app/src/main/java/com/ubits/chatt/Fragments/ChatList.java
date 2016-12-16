package com.ubits.chatt.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ubits.chatt.NewChat;
import com.ubits.chatt.R;
import com.ubits.chatt.model.ChatItem;

import java.util.ArrayList;

/**
 * Created by Abdullah
 */

public class ChatList extends Fragment
{
  private ArrayList<ChatItem> chatList;
  Context context;
  Activity activity;
  
  private void loadChatList() {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new ChatItem("Jonathan L@L", "My special project", "Yes, it's very special", "12:10PM", R.drawable.user1, true, false));
    localArrayList.add(new ChatItem("Jonathan L, Matt G, Neha D.", "Friends", "I agree with you and with your thoughts, you are right! We need some changes in life", "08:20AM", R.drawable.group1, true, true));
    localArrayList.add(new ChatItem("Victor Holdings", "Urgent!", "Hey man, long time. Hows your work goin on and hows your life?", "01/20/2014", R.drawable.user2, false, false));
    localArrayList.add(new ChatItem("Linda Hines", "Vacations...", "How are you?", "01/20/2014", R.drawable.user3, false, false));
    localArrayList.add(new ChatItem("Nelsy thomas", "Life!", "Hope you and your team is doing great. I know you are very busy with work", "01/22/2014", R.drawable.user4, true, false));
    localArrayList.add(new ChatItem("John E, Matt G., Victor D", "Happy hours", "Yes, it's very good", "01/22/2014", R.drawable.group2, true, true));
    localArrayList.add(new ChatItem("Martin K", "Last party", "This is last party...", "01/22/2014", R.drawable.user5, false, false));
    this.chatList = new ArrayList(localArrayList);
    this.chatList.addAll(localArrayList);

  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {

    View localView = paramLayoutInflater.inflate(R.layout.chat_list, null);
    loadChatList();
    ListView localListView = (ListView)localView.findViewById(R.id.list);
    localListView.setAdapter(new ChatAdapter(getContext()));

    FloatingActionButton fab = (FloatingActionButton) localView.findViewById(R.id.btnNewChat);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //Launch Chat Ativity
        getActivity().startActivity(new Intent(getContext(), NewChat.class));
     //   Toast.makeText(getActivity(), "Showng from Inside Fragment", Toast.LENGTH_LONG).show();
      }
    });
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        //Toast.makeText(getActivity(),"I'm going In Group chat", Toast.LENGTH_LONG).show();
       ChatList.this.getFragmentManager().beginTransaction()
               .replace(R.id.content_frame, new GroupChat()).addToBackStack("Group Chat").commit();

      }

    });

    return localView;
  }

  void getContext(Context c)
  {
    this.context = c;
  }

  void getParentActivity(Activity activity)
  {
    this.activity = activity;
  }

  private class ChatAdapter
    extends BaseAdapter
  {
    Context _c;
    private ChatAdapter(Context c) {
      this._c = c;
    }
    
    public int getCount()
    {
      return ChatList.this.chatList.size();
    }
    
    public ChatItem getItem(int paramInt)
    {
      return (ChatItem)ChatList.this.chatList.get(paramInt);
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View v = paramView;

      if (paramView == null) {

        LayoutInflater inflater = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.chat_item, null);
      }
      ChatItem localChatItem = getItem(paramInt);
      TextView tv1 = (TextView) v.findViewById(R.id.lbl1);
      TextView tv2 = (TextView) v.findViewById(R.id.lbl2);
      TextView tv3 = (TextView) v.findViewById(R.id.lbl3);
      TextView tv4 = (TextView) v.findViewById(R.id.lbl4);
      ImageView userImage = (ImageView)v.findViewById(R.id.img1);
      ImageView imggrplock = (ImageView)v.findViewById(R.id.img2);
      ImageView isOnline = (ImageView)v.findViewById(R.id.online);
      ImageView arrow = (ImageView)v.findViewById(R.id.imageView1);


      tv1.setText(localChatItem.getName());
      tv2.setText(localChatItem.getDate());
      tv3.setText(localChatItem.getTitle());
      tv4.setText(localChatItem.getMsg());
      userImage.setImageResource(localChatItem.getIcon());
      arrow.setImageResource(R.drawable.arrow);

      if(localChatItem.isOnline()){ isOnline.setImageResource(R.drawable.ic_online);}
      else{isOnline.setVisibility(View.INVISIBLE);}

      if(localChatItem.isGroup()){ imggrplock.setImageResource(R.drawable.ic_group);}
      else{imggrplock.setImageResource(R.drawable.ic_lock);}

      return v;
    }
  }
}


