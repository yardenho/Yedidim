package com.example.yedidim;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.User;
import com.squareup.picasso.Picasso;


public class EditReportFragment extends Fragment {
    private EditReportViewModel viewModel;
    private EditText problem;
    private EditText notes;
    private Button cancelBtn;
    private Button saveBtn;
    private ImageView photo;
    View view;


    public EditReportFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(EditReportViewModel.class);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel.setUserName(EditReportFragmentArgs.fromBundle(getArguments()).getUsername());

        String reportId = EditReportFragmentArgs.fromBundle(getArguments()).getReportID();

        view = inflater.inflate(R.layout.fragment_edit_report, container, false);
        problem = view.findViewById(R.id.editReport_et_problem);
        notes = view.findViewById(R.id.editReport_et_notes);
        photo = view.findViewById(R.id.editReport_iv_photo);
        ProgressBar pb = view.findViewById(R.id.editReport_progressBar);
        pb.setVisibility(View.GONE);

        Model.getInstance().getReportByID(reportId, (r) -> {
            viewModel.setReport(r);
            showReportDetails();
        });


        cancelBtn = view.findViewById(R.id.editReport_btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        saveBtn = view.findViewById(R.id.editReport_btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                saveBtn.setEnabled(false);
                cancelBtn.setEnabled(false);
                if(!checkCondition()) {
                    setDetails();
                    //TODO: to add here edit the report
                    Model.getInstance().editReport(viewModel.getReport(), new Model.editReportListener() {
                        @Override
                        public void onComplete() {
                            Model.getInstance().reloadUserReportsList(viewModel.getUserName());//for updating the list of the user reports
                            Navigation.findNavController(view).navigateUp();
                        }
                    });
                }
                else{
                    pb.setVisibility(View.GONE);
                    saveBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                }
            }
        });
        return view;
    }


    private void showReportDetails() {
        problem.setText(viewModel.getReport().getProblem());
        notes.setText(viewModel.getReport().getNotes());
        String url = viewModel.getReport().getReportUrl();
        if (url != null && !url.equals("")) {
            Log.d("TAG", "url = " + url);
            Picasso.get().load(url).placeholder(R.drawable.camera1).into(photo);
        } else {
            photo.setImageResource(R.drawable.camera1);
        }
    }

    private void setDetails() {
        viewModel.getReport().setProblem(problem.getText().toString());
        viewModel.getReport().setNotes(notes.getText().toString());
        //TODO do we want to add picture here?

    }

    private boolean checkCondition(){
        if(problem.getText().toString().equals("")){
            problem.setError("problem is required");
            problem.requestFocus();
            return true;
        }
        return false;
    }

}