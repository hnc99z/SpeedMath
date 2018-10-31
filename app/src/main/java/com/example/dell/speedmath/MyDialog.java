package com.example.dell.speedmath;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyDialog extends DialogFragment implements View.OnClickListener {
    private Fragment playFragment;
    private TextView tvYourScore,tvTitle;
    private ImageButton btRestart, btCancel;
    private View rootView;
    static MyDialog newInstance(int score) {
        MyDialog myDialog = new MyDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(PlayFragment.SCORE,score);
        myDialog.setArguments(bundle);
        return myDialog;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.over_layout, container, false);
        initView();

        return rootView;
    }

    private void initView() {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_dialog_title);
        tvYourScore = (TextView) rootView.findViewById(R.id.tv_dialog_content);
        btRestart = (ImageButton) rootView.findViewById(R.id.bt_dialog_reset);
        btCancel = (ImageButton) rootView.findViewById(R.id.bt_dialog_cancel);

        btRestart.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        playFragment = new PlayFragment();
        Bundle bundle = getArguments();
        int yourScore = bundle.getInt(PlayFragment.SCORE);
        String strYourScore = getString(R.string.your_score,yourScore);
        tvYourScore.setText(strYourScore);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_dialog_reset:
                dismiss();
                getActivity().finish();
                intent = new Intent(getActivity(),getActivity().getClass());
                startActivity(intent);

                break;
            case R.id.bt_dialog_cancel:
                dismiss();
                getActivity().finish();

                break;
            default:
                break;
        }
    }


}

