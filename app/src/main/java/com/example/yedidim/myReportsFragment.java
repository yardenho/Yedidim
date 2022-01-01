package com.example.yedidim;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;

import java.util.LinkedList;
import java.util.List;


public class myReportsFragment extends Fragment {
    List<Report> myReports = new LinkedList<Report>();
    View view;
//    ReportsListFragment.MyAdapter adapter;

//    public myReportsFragment() {
//    }
// TODO עצרתי רגע כי אנחנו צריכות לתכנן איך רק הדיווחים של יוזר מסויים יוצגו

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_my_reports, container, false);
//        Model.getInstance().getReportsList(new Model.GetAllReportsListener() {
//            @Override
//            public void onComplete(List<Report> d) {
//                myReports = d;
//                adapter.notifyDataSetChanged();
//            }
//        });
//        RecyclerView list = view.findViewById(R.id.reportsList_recycler);
//        list.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        list.setLayoutManager(layoutManager);
//        adapter = new MyAdapter();
//        list.setAdapter(adapter);
//        adapter.setOnItemClickListener(new ReportsListFragment.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Report r = myReports.get(position);
//                ReportsListFragmentDirections.ActionReportsListFragmentToViewReportFragment action = ReportsListFragmentDirections.actionReportsListFragmentToViewReportFragment(r.getUserName(),r.getReportID());
//                Navigation.findNavController(view).navigate(action);
//            }
//        });
//        return view;
//    }
//    class MyViewHolder extends RecyclerView.ViewHolder{
//        private final ReportsListFragment.OnItemClickListener listener;
//        TextView problem;
//        Button deleteBtn;
//        Button editBtn;
//
//        public MyViewHolder(@NonNull View itemView, ReportsListFragment.OnItemClickListener listener) {
//            super(itemView);
//            problem = itemView.findViewById(R.id.myReports_row_tv_problem);
//            this.listener = listener;
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    if(listener != null)
//                        listener.onItemClick(pos);
//                }
//            });
//        }
//        public void bind(Report report) {
//            problem.setText(report.getProblem());
//        }
//    }
//
//    interface OnItemClickListener{
//        void onItemClick(int position);
//    }
//
//    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
//        private ReportsListFragment.OnItemClickListener listener;
//
//        public void setOnItemClickListener(ReportsListFragment.OnItemClickListener listener){
//            this.listener = listener;
//        }
//
//        @NonNull
//        @Override
//        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View rowView = getLayoutInflater().inflate(R.layout.my_reports_row,parent,false);
//            MyViewHolder viewHolder = new MyViewHolder(rowView, listener);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//            Report report = myReports.get(position);
//            holder.bind(report);
//        }
//
//        @Override
//        public int getItemCount() {
//            return myReports.size();
//        }
//    }
}