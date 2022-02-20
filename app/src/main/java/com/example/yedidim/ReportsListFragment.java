package com.example.yedidim;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;
import com.squareup.picasso.Picasso;

public class ReportsListFragment extends Fragment {
    private ReportListViewModel viewModel;
    private View view;
    private MyAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private TextView noReportsMessage;
    private ProgressBar pb;

    public ReportsListFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(ReportListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reports_list, container, false);
        pb = view.findViewById(R.id.reportList_progressBar);
        pb.setVisibility(View.VISIBLE);
        RecyclerView list = view.findViewById(R.id.reportsList_recycler);
        list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);

        noReportsMessage = view.findViewById(R.id.reportsList_tv_noReportsMessage);
        noReportsMessage.setVisibility(View.GONE);

        adapter = new ReportsListFragment.MyAdapter();
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Report r = viewModel.getReports().getValue().get(position);
                ReportsListFragmentDirections.ActionReportsListFragmentToViewReportFragment action = ReportsListFragmentDirections.actionReportsListFragmentToViewReportFragment(r.getReportID());
                Navigation.findNavController(v).navigate(action);
            }
        });

        swipeRefresh = view.findViewById(R.id.reportsList_swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Model.getInstance().reloadReportsList();
            }
        });

        setHasOptionsMenu(true);

        viewModel.getReports().observe(getViewLifecycleOwner(), (reportsList)-> {
            adapter.notifyDataSetChanged();
            noReportMessage();
        });

        swipeRefresh.setRefreshing(Model.getInstance().getReportsListLoadingState().getValue() == Model.LoadingState.loading);
        Model.getInstance().getReportsListLoadingState().observe(getViewLifecycleOwner(), loadingState -> {
            swipeRefresh.setRefreshing(loadingState == Model.LoadingState.loading);
        });

        pb.setVisibility(View.GONE);
        return view;
    }

    private void noReportMessage(){
        if(viewModel.getReports().getValue()!=null){
            if(viewModel.getReports().getValue().size() == 0)
                noReportsMessage.setVisibility(View.VISIBLE);
            else
                noReportsMessage.setVisibility(View.GONE);
        }
        else
            noReportsMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_menu, menu);
        inflater.inflate(R.menu.map_menu, menu);
        inflater.inflate(R.menu.my_profile_menu, menu);
        inflater.inflate(R.menu.my_reports_menu, menu);
        inflater.inflate(R.menu.log_out_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMenu_addReport:
                @NonNull NavDirections action = ReportsListFragmentDirections.actionReportsListFragmentToAddingReportFragment();
                Navigation.findNavController(view).navigate(action);
                return true;
            case R.id.mapMenu_MoveToMap:
                Navigation.findNavController(view).navigate(ReportsListFragmentDirections.actionReportsListFragmentToMapFragment(null));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final OnItemClickListener listener;
        TextView problem;
        TextView location;
        ImageView photo;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            problem = itemView.findViewById(R.id.reportListRow_problem);
            location = itemView.findViewById(R.id.reportListRow_location);
            photo = itemView.findViewById(R.id.reportListRow_iv);
            this.listener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null)
                        listener.onItemClick(pos, v);
                }
            });

        }

        public void bind(Report report) {
            problem.setText(report.getProblem());
            location.setText(report.getLocation());
            String url = report.getReportUrl();
            if(url != null && !url.equals("")){
                Picasso.get().load(url).placeholder(R.drawable.car).into(photo);
            }
            else{
                photo.setImageResource(R.drawable.car);
            }
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //יוצר אובייקט חדש מהשורה
            LayoutInflater inflater = getLayoutInflater();
            View rowView = inflater.inflate(R.layout.reports_list_row, parent, false);
            //יצירת הולדר שיעטוף אותו
            MyViewHolder viewHolder = new MyViewHolder(rowView, listener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            //תחבר לי את הview עם הdata של אותה שורה
            Report report = viewModel.getReports().getValue().get(position);
            holder.bind(report);
        }

        @Override
        public int getItemCount() {
            if(viewModel.getReports().getValue() == null)
                return 0;
            return viewModel.getReports().getValue().size();
        }
    }
}
