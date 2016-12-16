package com.ubits.chatt.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.ubits.chatt.R;
import com.ubits.chatt.model.Conversation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Abdullah
 */
// todo Image is caught and now work on adaptor to show image
public class GroupChat extends Fragment
{
  private static final int STATUS_OK =1 ;
  private static final int STATUS_ERROR =2 ;

  private ChatAdapter adp;
  private ArrayList<Conversation> convList;
  private EditText txt;
  Button sendButton;
  TextView tvcam;
  public static final int MESSAGE_SENT = 0;
  public static final int MESSAGE_RECIEVED = 1;
  int sample = R.drawable.ic_launcher;

  private Socket socket;
  private String imgDecodableString;

  {
  try {

//    socket = IO.socket("http://192.168.0.105:3000");
    socket = IO.socket("http://192.168.0.102:3000");

    //   Toast.makeText(this,"Connecting",Toast.LENGTH_SHORT).show();
  }
  catch (URISyntaxException e){

    throw new RuntimeException(e);
  }
}


  private void loadConversationList() {
    this.convList = new ArrayList();
    this.convList.add(new Conversation("hi there...",null, "12:45 AM", true, true));
    this.convList.add(new Conversation("Hello,null, How can I help you?",null, "12:47 AM", false, true));
    this.convList.add(new Conversation("What do you think?",null, "12:49 AM", true, true));
    this.convList.add(new Conversation("It's actually pretty good!",null, "12:50 AM", false, true));
    this.convList.add(new Conversation("hi there...",null, "12:45 AM", true, true));
    this.convList.add(new Conversation("Hello, How can I help you?",null, "12:47 AM", false, true));
    this.convList.add(new Conversation("What do you think about?",null, "12:49 AM", true, true));
    this.convList.add(new Conversation("It's actually pretty good!",null, "12:50 AM", false, true));


  }
//Old Send Message Way
//  private void sendMessage() {
//    if (this.txt.length() == 0) {
//      return;
//    } else {
//      InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//      imm.hideSoftInputFromWindow(this.txt.getWindowToken(), 0);
//
//    String str = this.txt.getText().toString();
//    this.convList.add(new Conversation(str,null, "12:00 AM", true, true));
//  //  this.convList.add(new Conversation("Hello, this is an auto reply!",null, "12:00 AM", false, true));
//    this.adp.notifyDataSetChanged();
//    this.txt.setText("");
//    }
//  }
  private void sendMessage1()
  {
    if (this.txt.length() == 0) {
      return;
    } else {

        String message = txt.getText().toString().trim();
        txt.setText("");
        addMessage(message,MESSAGE_SENT);
        JSONObject sendText = new JSONObject();
        try {
          sendText.put("text", message);
          socket.emit("message", sendText);
        } catch (JSONException e) {
            e.printStackTrace();
       }
     }
  }

  private void addMessage(String message, int by) {

    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(this.txt.getWindowToken(), 0);

    String str = message;
      this.txt.setText("");

      if(by ==MESSAGE_SENT){
        this.convList.add(new Conversation(str,null, "12:00 AM", true, true));
      }

     else if(by ==MESSAGE_RECIEVED){
        this.convList.add(new Conversation(str,null, "12:00 AM", false, true));
      }
    this.adp.notifyDataSetChanged();
 }


  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    View localView = paramLayoutInflater.inflate(R.layout.group_chat, null);
    loadConversationList();
    ListView localListView = (ListView)localView.findViewById(R.id.list);
    sendButton = (Button) localView.findViewById(R.id.btnSend);
    tvcam = (TextView) localView.findViewById(R.id.btnCamera);

    sendButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        sendMessage1();
      }
    });

    tvcam.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
       openGallery();
      }
    });

    this.adp = new ChatAdapter(getActivity());
    localListView.setAdapter(this.adp);
    localListView.setTranscriptMode(2);
    localListView.setStackFromBottom(true);
    this.txt = ((EditText)localView.findViewById(R.id.txt));
    this.txt.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);

    

    socket.connect();
    Toast.makeText(getActivity(),"Connecting",Toast.LENGTH_SHORT).show();
    socket.on("message",IncomingMessageListerner);
  
  return localView;
  }

  private void openGallery() {
    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(intent,STATUS_OK);

  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == STATUS_OK && resultCode == RESULT_OK
            &&  data != null) {
      Uri selectedImage = data.getData();
      String[] filePathColumn = {MediaStore.Images.Media.DATA};

      Cursor cursor = getActivity().getContentResolver().query(selectedImage,
              filePathColumn, null, null, null);
      // Move to first row
      cursor.moveToFirst();
      int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
      imgDecodableString = cursor.getString(columnIndex);
      cursor.close();
     // Toast.makeText(getActivity(),"ACTIVITY RESULT",Toast.LENGTH_SHORT).show();
      //Log.d("onActivityResult",imgDecodableString);
      sendImage(imgDecodableString);
  }
}


  public void sendImage(String path)
  {
    JSONObject sendData = new JSONObject();
    try{
      sendData.put("image", encodeImage(path));
      Bitmap bmp = decodeImage(sendData.getString("image"));
      addImage(bmp,MESSAGE_SENT);
      socket.emit("message",sendData);
    }catch(JSONException e){

    }
  }

  private void addImage(Bitmap bmp,int by){
    if(by == MESSAGE_SENT){
      this.convList.add(new Conversation(null,bmp, "12:00 AM", true, true));
    }
    else if(by == MESSAGE_RECIEVED){
      this.convList.add(new Conversation(null,bmp, "12:00 AM", false, true));
    }
    this.adp.notifyDataSetChanged();
  }

  private Bitmap decodeImage(String data)
  {
    byte[] b = Base64.decode(data,Base64.DEFAULT);
    Bitmap bmp = BitmapFactory.decodeByteArray(b,0,b.length);
    return bmp;
  }

  private String encodeImage(String path)
  {
    File imagefile = new File(path);
    FileInputStream fis = null;
    try{
      fis = new FileInputStream(imagefile);
    }catch(FileNotFoundException e){
      e.printStackTrace();
    }
    Bitmap bm = BitmapFactory.decodeStream(fis);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
    byte[] b = baos.toByteArray();
    String encImage = Base64.encodeToString(b, Base64.DEFAULT);
    //Base64.de
    return encImage;

  }


  private Emitter.Listener IncomingMessageListerner = new Emitter.Listener() {
    @Override
    public void call(final Object... args) {

      getActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
            JSONObject Data = (JSONObject) args[0];
            String message="";
            String imageText = "";
            try {
                message = Data.getString("message");
                Log.d("RECIEVED MESSAGE",message);
                String arr[] = message.split("[\":]");
//              Log.d("Data Split",arr[2]);
//              Toast.makeText(getActivity(), ""+arr.length,Toast.LENGTH_LONG).show();
//              sample Style: {"text":"data"}
                 addMessage(arr[4],MESSAGE_RECIEVED);
              //  addMessage(message,MESSAGE_RECIEVED);
                }
            catch (JSONException e){
                e.printStackTrace();
            }
            try {
              imageText = Data.getString("image");
              Log.d("DECODEIMAGE",imageText);
              addImage(decodeImage(imageText),MESSAGE_RECIEVED);
            }
            catch (JSONException e) {
            //retur
          }
        }
      });
    }
  };

  @Override
  public void onDestroy() {
    super.onDestroy();
    socket.disconnect();
  }

  private class ChatAdapter extends BaseAdapter {
    Context _c;

    private ChatAdapter(Context c) {
      this._c = c;
    }

    public int getCount()
    {
      return GroupChat.this.convList.size();
    }

    public Conversation getItem(int paramInt)
    {
      return (Conversation)GroupChat.this.convList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
      Conversation localConversation = getItem(paramInt);
      View SentView, RecievedView;

      if (localConversation.isSent()) {

        LayoutInflater inflater = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SentView = inflater.inflate(R.layout.chat_item_sent, null);

        TextView tvdate = (TextView) SentView.findViewById(R.id.lbldate);
        TextView tvmsg = (TextView) SentView.findViewById(R.id.lblmessage);
        TextView tvdelivered = (TextView) SentView.findViewById(R.id.lblstatusmsg);
        ImageView imguser = (ImageView) SentView.findViewById(R.id.imgSender);
        ImageView imgSent = (ImageView) SentView.findViewById(R.id.lblSentImg);


        tvdate.setText(localConversation.getDate());

        if(localConversation.getMsg()==null||localConversation.getMsg().equals("")) {
          tvmsg.setText("");
        }
        else{
          tvmsg.setText(localConversation.getMsg());
        }

        if(localConversation.getImage() == null){
          imgSent.setVisibility(View.INVISIBLE);
        }
        else{
          imgSent.setImageBitmap(localConversation.getImage());
        }

        if (localConversation.isSuccess()) {
          tvdelivered.setText("Delivered");
        } else {
          tvdelivered.setText("Sending");
        }
        imguser.setImageResource(R.drawable.user1);

        return SentView;
      }

      else {
        LayoutInflater inflater = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RecievedView = inflater.inflate(R.layout.chat_item_rcv, null);

        TextView tvdate = (TextView) RecievedView.findViewById(R.id.lblrcvtime);
        TextView tvmsg = (TextView) RecievedView.findViewById(R.id.lblrcvmsg);
        TextView tvdelivered = (TextView) RecievedView.findViewById(R.id.lblseen);
        ImageView imguser = (ImageView) RecievedView.findViewById(R.id.imgrcv);
        ImageView imgRcv = (ImageView) RecievedView.findViewById(R.id.lblrcvImg);

        tvdate.setText(localConversation.getDate());

        if(localConversation.getMsg()== null||localConversation.getMsg().equals("")) {

          tvmsg.setText("");
        }
        else{
          tvmsg.setText(localConversation.getMsg());
          Log.d("FINALmessage",localConversation.getMsg());
        }

        if(localConversation.getImage() == null){
          imgRcv.setVisibility(View.INVISIBLE);
          Log.d("FINALIMAGE","Image is Null");
        }
        else{
          imgRcv.setImageBitmap(localConversation.getImage());
          Log.d("FINALIMAGE",localConversation.getImage().toString());
        }

        if (localConversation.isSuccess()) {
          tvdelivered.setText("Seen");
        } else {
          tvdelivered.setText("Seen");
        }
        imguser.setImageResource(R.drawable.user2);

        return RecievedView;
      }
    }
  }
}


