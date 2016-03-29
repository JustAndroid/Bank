package com.kelvin.banksecurety.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.banksecurety.R;
import com.kelvin.banksecurety.fragments.adapter.MenuListAdapter;


import java.util.HashMap;
import java.util.Locale;

public class MenuItemFragment extends Fragment {
    private static final String TAG = "com.kelvin.banksecurety.fragments.BaseMenuItemFragment";
    public static final String KEY_ITEM_INDEX = TAG + ".KEY_ITEM_INDEX";
    TextToSpeech textToSpeech;
    String speechText;
    ImageView playButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_menu_item, container, false);
        final Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        playButton = (ImageView) root.findViewById(R.id.play_button);
        final Handler handler = new Handler();

        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();
                        textToSpeech.stop();
                    }
                }
        );


        textToSpeech = new TextToSpeech(inflater.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {


                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.US);

                }
            }
        }
        );


                playButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (textToSpeech.isSpeaking()) {
                            textToSpeech.stop();
                        } else {
                            HashMap<String, String> params = new HashMap<String, String>();

                            params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "stringId");

                            textToSpeech.speak(speechText, TextToSpeech.QUEUE_FLUSH, params);
                            playButton.setImageResource(R.drawable.ic_stop_white_48dp);
                        }

                    }
                }
        );

        textToSpeech.setOnUtteranceCompletedListener(
                new TextToSpeech.OnUtteranceCompletedListener() {
                    @Override
                    public void onUtteranceCompleted(String utteranceId) {
                        handler.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        playButton.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                                    }
                                }
                        );
                    }


                }
        );

        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    textToSpeech.stop();
                    textToSpeech.shutdown();
                    getActivity().onBackPressed();
                    return true;
                } else {
                    return false;
                }
            }
        });

        final Bundle arg = getArguments();
        final int index;

        if (arg != null && arg.containsKey(KEY_ITEM_INDEX)) {
            index = arg.getInt(KEY_ITEM_INDEX);
        } else {
            return null;
        }


        final TextView titleMenuItem = (TextView) root.findViewById(R.id.title_menu_item);
        titleMenuItem.setText(MenuListAdapter.ITEMS[index][MenuListAdapter.P_TEXT]);
String html = " <p>\n" +
        "     Вчера во время проведения разведоперации наша группа подверглась нападению неизвестного \n" +
        "     противника в камуфляжной форме Алиенов." +
        "<img src=\"logo\" alt=\"Лейтенант Бокатуев\" width=\"30%\"class=\"leftimg\"> В результате эффективной обороны и стремительной \n" +
        "     контратаки многочисленная группа боевиков была смята и отброшена. Среди личного состава \n" +
        "     потерь нет. Бойцы разведгруппы проявили недюжие навыки владения оружием. Особо отличился \n" +
        "     в бою взводный Кудряшев&nbsp;М.А., грамотно использовавший человеческие ресурсы \n" +
        "     своего взвода. В результате операции были захвачены элементы внеземной культуры, которые \n" +
        "     переданы аналитической группе.</p>\n" +
        "     <h2>Пресс-релиз аналитической группы</h2>\n";


//        final String[] texts_1 = getResources().getStringArray(R.array.text_1);
//        final String[] texts_2 = getResources().getStringArray(R.array.text_2);
//        final String[] texts_3 = getResources().getStringArray(R.array.text_3);
//
//        final TextView textView1 = (TextView) root.findViewById(R.id.text_1);
//        textView1.setText(texts_1[index]);
//
//        final TextView textView2 = (TextView) root.findViewById(R.id.text_2);
//        textView2.setText(texts_2[index]);
//
//
//        final TextView textView3 = (TextView) root.findViewById(R.id.text_3);
//        textView3.setText(texts_3[index]);
      //  speechText = texts_1[index] + texts_2[index] + texts_3[index];



        // Получаем иконку и ее ширину




        TextView messageView = (TextView) root.findViewById(R.id.text);


        messageView.setText(Html.fromHtml(html,new ImageGetter(), null));


        return root;
    }

    private class ImageGetter implements Html.ImageGetter {

        public Drawable getDrawable(String source) {
            int id;
           switch (source) {
               case "logo" : id = R.drawable.logo;
                   Drawable d = getResources().getDrawable(id);
                   d.setBounds(0,0,100, 100);
                   return d;
            }
            return null;

        }
    };


}

