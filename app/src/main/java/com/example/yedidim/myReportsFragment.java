package com.example.yedidim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;

import java.util.List;


public class myReportsFragment extends Fragment {
    private MyReportsViewModel viewModel;
    private View view;
    private SwipeRefreshLayout swipeRefresh;
    private myReportsFragment.MyAdapter adapter;

    public myReportsFragment() {
    }
// TODO עצרתי רגע כי אנחנו צריכות לתכנן איך רק הדיווחים של יוזר מסויים יוצגו


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(MyReportsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_reports, container, false);
        viewModel.setUsername(MyProfileFragmentArgs.fromBundle(getArguments()).getUsername());

        Model.getInstance().reloadUserReportsList(viewModel.getUsername());
        viewModel.setMyReports(Model.getInstance().getAllUserReports());

        RecyclerView list = view.findViewById(R.id.myReports_recycler);
        list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        adapter = new myReportsFragment.MyAdapter();

        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new myReportsFragment.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Report r = viewModel.getMyReports().getValue().get(position);
                myReportsFragmentDirections.ActionMyReportsFragmentToViewReportFragment action = myReportsFragmentDirections.actionMyReportsFragmentToViewReportFragment(viewModel.getUsername(), r.getReportID());
                Navigation.findNavController(v).navigate(action);
            }
        });

        adapter.setOnDeleteClickListener(new OnDeleteClickListener() {
            @Override
            public void OnDeleteClick(int position) {
                Report r = viewModel.getMyReports().getValue().get(position);
                Model.getInstance().deleteReport(r, new Model.deleteReportListener() {
                    @Override
                    public void onComplete() {
                        //TODO: refresh list
                        Model.getInstance().reloadUserReportsList(viewModel.getUsername());//for updating the list of the user reports
//                        refreshData(); // זה מה שהיה ועבד לפני שהוספתי את השורה למעלה
                    }
                });
            }
        });

        adapter.setOnEditClickListener(new OnEditClickListener() {
            @Override
            public void OnEditClick(int position) {
                Report r = viewModel.getMyReports().getValue().get(position);
                Navigation.findNavController(view).navigate(myReportsFragmentDirections.actionMyReportsFragmentToEditReportFragment(viewModel.getUsername(), r.getReportID()));
                //TODO: refresh data

            }
        });

        swipeRefresh = view.findViewById(R.id.myReports_swiprRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Model.getInstance().reloadUserReportsList(viewModel.getUsername());
            }
        });
        setHasOptionsMenu(true);

        if(viewModel.getMyReports().getValue() == null)
            refreshData();
        viewModel.getMyReports().observe(getViewLifecycleOwner(), (reportsList)-> {
            adapter.notifyDataSetChanged();

        });

        swipeRefresh.setRefreshing(Model.getInstance().getReportsListLoadingState().getValue() == Model.LoadingState.loading);
        Model.getInstance().getReportsListLoadingState().observe(getViewLifecycleOwner(), loadingState -> {
            swipeRefresh.setRefreshing(loadingState == Model.LoadingState.loading);
        });

        return view;
    }

    public void refreshData(){
//        swipeRefresh.setRefreshing(true);
//
//        Model.getInstance().getUserReportsList(viewModel.getUsername(), new Model.GetUserReportsListener() {
//            @Override
//            public void onComplete(List<Report> data) {
////                viewModel.setMyReports(data); // אני הורדתי לבדוק מול אליאב, הורדתי כי זה העלה הערה
//                adapter.notifyDataSetChanged();
//                if(swipeRefresh.isRefreshing())
//                    swipeRefresh.setRefreshing(false);
//            }
//        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.my_profile_menu, menu);
        inflater.inflate(R.menu.log_out_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //TODO: קוד כפול ???
        switch (item.getItemId()) {
            case R.id.log_out_menu_LogOut:
                Navigation.findNavController(view).navigate(myReportsFragmentDirections.actionGlobalMainScreenFragment());
                return true;
            case R.id.myProfileMenu_myProfile:
                Navigation.findNavController(view).navigate(myReportsFragmentDirections.actionGlobalMyProfileFragment(viewModel.getUsername()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private final myReportsFragment.OnItemClickListener listener;
        private final OnDeleteClickListener deleteListener;
        private final OnEditClickListener editListener;
        TextView problem;
        ImageButton deleteBtn;
        ImageButton editBtn;

        public MyViewHolder(@NonNull View itemView, myReportsFragment.OnItemClickListener listener,OnDeleteClickListener deleteListener,OnEditClickListener editListener) {
            super(itemView);
            problem = itemView.findViewById(R.id.myReports_row_tv_problem);
            deleteBtn = itemView.findViewById(R.id.myReports_row_imageBtn_delete);
            editBtn = itemView.findViewById(R.id.myReports_row_imageBtn_edit);
            this.listener = listener;
            this.deleteListener = deleteListener;
            this.editListener = editListener;


            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: to check if needed here new list
                    int pos = getAdapterPosition();
                    //need to do from model and remove delete listener
                    deleteListener.OnDeleteClick(pos);
//                    adapter.notifyDataSetChanged();
                }
            });

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    editListener.OnEditClick(pos);
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(listener != null)
                        listener.onItemClick(pos, v);
                }
            });
        }
        public void bind(Report report) {
            problem.setText(report.getProblem());
        }
    }

    interface OnItemClickListener{
        void onItemClick(int position, View v);
    }

    public interface OnDeleteClickListener{
        void OnDeleteClick(int position);
    }

    public interface OnEditClickListener{
        void OnEditClick(int position);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        private myReportsFragment.OnItemClickListener listener;
        private OnDeleteClickListener deleteListener;
        private OnEditClickListener editListener;

        public void setOnItemClickListener(myReportsFragment.OnItemClickListener listener){
            this.listener = listener;
        }

        void setOnDeleteClickListener(OnDeleteClickListener cbListener){
            this.deleteListener = cbListener;
        }

        void setOnEditClickListener(OnEditClickListener cbListener){
            this.editListener = cbListener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowView = getLayoutInflater().inflate(R.layout.my_reports_row,parent,false);
            MyViewHolder viewHolder = new MyViewHolder(rowView, listener, deleteListener, editListener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Report report = viewModel.getMyReports().getValue().get(position);
            holder.bind(report);
        }

        @Override
        public int getItemCount() {
            if(viewModel.getMyReports().getValue() == null)
                return 0;
            return viewModel.getMyReports().getValue().size();
        }
    }
}