package com.example.bottomnavigation5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ListFragment extends Fragment {


    FloatingActionButton btnFloat;
    RecyclerView rvList;
    FragmentAdapter adapter;
    int pos;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ListFragment() {

    }

    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new FragmentAdapter(requireContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam1 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        rvList = view.findViewById(R.id.rv_list);
        btnFloat = view.findViewById(R.id.button_float);

        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(requireContext()));
        getActivity().getSupportFragmentManager().setFragmentResultListener("title", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey, Bundle result) {
                FragmentModel taskModel = (FragmentModel) result.getSerializable("model");
                FragmentModel taskModel2 = (FragmentModel) result.getSerializable("model2");

                if (taskModel != null) {
                    adapter.addTask(taskModel);
                    Toast.makeText(requireContext(), taskModel.getTitle(), Toast.LENGTH_SHORT).show();
                }
                else {
                    adapter.dataTry(pos,taskModel2);
                }
            }
        });
        btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("AddNewTaskFragment").replace(R.id.fragment_container, new AddNewTaskFragment()).commit();
            }


        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(int position, FragmentModel model) {
                pos = position;
                Bundle bundle = new Bundle();
                FragmentModel model1 = new FragmentModel(adapter.list.get(position).getTitle(),adapter.list.get(position).getDescription());
                bundle.putSerializable("model2",model1);
                getActivity().getSupportFragmentManager().setFragmentResult("edit",bundle);
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("AddNewTaskFragment").replace(R.id.fragment_container,new AddNewTaskFragment()).commit();

            }
        });

        return view;
    }

};