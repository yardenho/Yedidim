package com.example.yedidim;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.List;


public class ReportsListFragment extends Fragment {
    private ReportListViewModel viewModel;
    private View view;
    private MyAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

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
        viewModel.setUserName(ReportsListFragmentArgs.fromBundle(getArguments()).getUsername());

        RecyclerView list = view.findViewById(R.id.reportsList_recycler);
        list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);


        adapter = new ReportsListFragment.MyAdapter();
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Report r = viewModel.getReports().get(position);
                ReportsListFragmentDirections.ActionReportsListFragmentToViewReportFragment action = ReportsListFragmentDirections.actionReportsListFragmentToViewReportFragment(viewModel.getUserName(), r.getReportID());
                Navigation.findNavController(v).navigate(action);
            }
        });

        swipeRefresh = view.findViewById(R.id.reportsList_swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        setHasOptionsMenu(true);
        refreshData();
        return view;
    }

    private void refreshData() {
        swipeRefresh.setRefreshing(true);
        Model.getInstance().getReportsList(new Model.GetAllReportsListener() {
            @Override
            public void onComplete(List<Report> d) {
                viewModel.setReports(d);
                adapter.notifyDataSetChanged();
                if(swipeRefresh.isRefreshing())
                    swipeRefresh.setRefreshing(false);
            }
        });
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
                //TODO: when we after the sign up maybe not allow to return there
                ReportsListFragmentDirections.ActionReportsListFragmentToAddingReportFragment action = ReportsListFragmentDirections.actionReportsListFragmentToAddingReportFragment(viewModel.getUserName());
                Navigation.findNavController(view).navigate(action);
                return true;
            case R.id.myProfileMenu_myProfile:
                //TODO
//                ReportsListFragmentDirections.ActionGlobalMyProfileFragment action1 = ReportsListFragmentDirections.actionGlobalMyProfileFragment(viewModel.getUserName());
                Navigation.findNavController(view).navigate(ReportsListFragmentDirections.actionGlobalMyProfileFragment(viewModel.getUserName()));
                return true;
            case R.id.myReportsmenu_myReport:
                //TODO: check why the first line dos not working
//                ReportsListFragmentDirections.ActionGlobalMyReportsFragment action2 = ReportsListFragmentDirections.actionGlobalMyReportsFragment(viewModel.getUserName());
                Navigation.findNavController(view).navigate(ReportsListFragmentDirections.actionGlobalMyReportsFragment(viewModel.getUserName()));
                return true;
            case R.id.log_out_menu_LogOut:
                //TODO
                NavDirections action3 = ReportsListFragmentDirections.actionGlobalMainScreenFragment();
                Navigation.findNavController(view).navigate(action3);
                return true;
            case R.id.mapMenu_MoveToMap:
                Navigation.findNavController(view).navigate(ReportsListFragmentDirections.actionReportsListFragmentToMapFragment(viewModel.getUserName(), null));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //        return true;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final OnItemClickListener listener;
        TextView problem;
        TextView location;
        ImageView photo;
        //TODO image

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            problem = itemView.findViewById(R.id.reportListRow_problem);
            location = itemView.findViewById(R.id.reportListRow_location);
            photo = itemView.findViewById(R.id.reportListRow_iv);
            // TODO image
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
                Log.d("TAG", "url = " + url);
                Picasso.get().load(url).placeholder(R.drawable.camera1).into(photo);
            }
            else{
                photo.setImageResource(R.drawable.camera1);
            }
            //TODO: IMAGE  error(R.drawable.camera1)
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
            Report report = viewModel.getReports().get(position);
            holder.bind(report);
        }

        @Override
        public int getItemCount() {
            return viewModel.getReports().size();
        }
    }
}
