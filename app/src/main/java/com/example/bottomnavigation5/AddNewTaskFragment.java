package com.example.bottomnavigation5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AddNewTaskFragment extends Fragment {
    EditText etSend, etDescription;
    Button btnSend;
    FragmentModel fragmentmodel;
    FragmentModel model2;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    public AddNewTaskFragment() {

    }

    public static AddNewTaskFragment newInstance(String title) {
        AddNewTaskFragment fragment = new AddNewTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        args.putString(ARG_PARAM2, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task_new, container, false);
        etSend = view.findViewById(R.id.et_title);
        etDescription = view.findViewById(R.id.et_description);
        btnSend = view.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etSend.getText().toString();
                String description = etDescription.getText().toString();
                fragmentmodel = new FragmentModel(title, description);
                Bundle bundle = new Bundle();
                if (model2 == null) {
                    bundle.putSerializable("model", fragmentmodel);
                } else {
                    model2 = new FragmentModel(title, description);
                    bundle.putSerializable("model2", model2);
                }

                getActivity().getSupportFragmentManager().setFragmentResult("title", bundle);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        edit();
        return view;

    }

    private void edit() {
        getActivity().getSupportFragmentManager().setFragmentResultListener("edit", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey, Bundle result) {
                model2 = (FragmentModel) result.getSerializable("model2");
                etSend.setText(model2.getTitle());
                etDescription.setText(model2.getDescription());

            }
        });
    }


}