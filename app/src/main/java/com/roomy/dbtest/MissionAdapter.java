package com.roomy.dbtest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class MissionAdapter extends RecyclerView.Adapter<MissionAdapter.MissionViewHolder> {

    private List<Mission> missions;

    public MissionAdapter(List<Mission> missions) {
        this.missions = missions;
    }

   public interface MissionListener {
        void onMissionClicked(int position, View view);
        void onMissionLongClicked(int position, View view);
    }

    public MissionListener listener;

    public void setListener(MissionListener listener) {
        this.listener = listener;
    }

    public class MissionViewHolder extends RecyclerView.ViewHolder {

        TextView missionTv;
        CheckBox missionCb;

        public MissionViewHolder(View itemView) {
            super(itemView);

            missionTv = itemView.findViewById(R.id.mission_tv);
            missionCb = itemView.findViewById(R.id.mission_cb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onMissionClicked(getAdapterPosition(),view);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onMissionLongClicked(getAdapterPosition(),view);
                    return true;
                }
            });
        }
    }

    @NonNull
    @Override
    public MissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mission_layout,parent,false);
        MissionViewHolder missionViewHolder = new MissionViewHolder(view);
        return missionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MissionViewHolder holder, int position) {

        Mission mission = missions.get(position);
        holder.missionTv.setText(mission.getName());
        holder.missionCb.setChecked(mission.isCompleted());
    }

    @Override
    public int getItemCount() {
        return missions.size();
    }
}
