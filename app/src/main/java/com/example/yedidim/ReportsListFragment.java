package com.example.yedidim;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;
import java.util.LinkedList;
import java.util.List;



public class ReportsListFragment extends Fragment {
    List<Report> reports = new LinkedList<Report>();
    View view;
    MyAdapter adapter;

//    public ReportsListFragment() {
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reports_list, container, false);
        Model.getInstance().getReportsList(new Model.GetAllReportsListener() {
            @Override
            public void onComplete(List<Report> d) {
                reports = d;
                adapter.notifyDataSetChanged();
            }
        });
        RecyclerView list = view.findViewById(R.id.reportsList_recycler);
        list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        adapter = new MyAdapter();
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Report r = reports.get(position);
                ReportsListFragmentDirections.ActionReportsListFragmentToViewReportFragment action = ReportsListFragmentDirections.actionReportsListFragmentToViewReportFragment(r.getUserName(),r.getReportID());
                Navigation.findNavController(view).navigate(action);
            }
        });

        setHasOptionsMenu(true);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.base_menu, menu);
        inflater.inflate(R.menu.add_menu, menu);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private final OnItemClickListener listener;
        TextView problem;
        TextView location;
        //TODO image

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            problem = itemView.findViewById(R.id.reportListRow_problem);
            location = itemView.findViewById(R.id.reportListRow_location);
            // TODO image
            this.listener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(listener != null)
                        listener.onItemClick(pos);
                }
            });

        }

        public void bind(Report report) {
            problem.setText(report.getProblem());
            location.setText(report.getLocation());
            //TODO: IMAGE
        }
    }

    interface OnItemClickListener{
        void onItemClick(int position);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        private OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowView = getLayoutInflater().inflate(R.layout.reports_list_row,parent,false);
            MyViewHolder viewHolder = new MyViewHolder(rowView, listener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Report report = reports.get(position);
            holder.bind(report);
        }

        @Override
        public int getItemCount() {
            return reports.size();
        }
    }
}