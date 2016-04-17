package com.raido.android.receiver;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DayStatsAdapter extends CursorAdapter{
    private final LayoutInflater layoutInflater;
    private UOW uow;
    private ViewGroup parentViewGroup;

    public DayStatsAdapter(Context context, Cursor c, UOW uow) {
        super(context, c, 0);
        layoutInflater = LayoutInflater.from(context);
        this.uow = uow;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view=layoutInflater.inflate(R.layout.operation_stats, parent, false);
        parentViewGroup = parent;
        return view;
    }


    // this can be called several times by the system!!!
    // first pass - initial draw, get measurements
    // second pass - final draw
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewName =(TextView) view.findViewById(R.id.name);

        DayStat dayStat = uow.dayStatRepo.cursorToEntity(cursor);
        textViewName.setText(dayStat.toString());
        //displayOperations(view, context, dayStat);
    }


    private void displayOperations(View view, Context context, DayStat operand) {
        ListView listView = (ListView) view.findViewById(R.id.operationsListView);
        OperationsAdapter adapter = new OperationsAdapter(context, uow.operationRepo.getForOperarandAndDatestamp(operand.getOperandId()), uow);
        listView.setAdapter(adapter);

    }
}
